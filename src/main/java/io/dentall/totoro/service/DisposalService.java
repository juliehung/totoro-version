package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.repository.RegistrationRepository;
import io.dentall.totoro.service.dto.TreatmentDrugCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Disposal.
 */
@Service
@Transactional
public class DisposalService {

    private final Logger log = LoggerFactory.getLogger(DisposalService.class);

    private final DisposalRepository disposalRepository;

    private final PrescriptionService prescriptionService;

    private final TodoService todoService;

    private final TreatmentDrugService treatmentDrugService;

    private final TreatmentDrugQueryService treatmentDrugQueryService;

    private final RelationshipService relationshipService;

    private final RegistrationRepository registrationRepository;

    public DisposalService(
        DisposalRepository disposalRepository,
        PrescriptionService prescriptionService,
        TodoService todoService,
        TreatmentDrugService treatmentDrugService,
        TreatmentDrugQueryService treatmentDrugQueryService,
        RelationshipService relationshipService,
        RegistrationRepository registrationRepository
    ) {
        this.disposalRepository = disposalRepository;
        this.prescriptionService = prescriptionService;
        this.todoService = todoService;
        this.treatmentDrugService = treatmentDrugService;
        this.treatmentDrugQueryService = treatmentDrugQueryService;
        this.relationshipService = relationshipService;
        this.registrationRepository = registrationRepository;
    }

    /**
     * Save a disposal.
     *
     * @param disposal the entity to save
     * @return the persisted entity
     */
    public Disposal save(Disposal disposal) {
        log.debug("Request to save Disposal : {}", disposal);

        Prescription prescription = getPrescription(disposal);
        Todo todo = getTodo(disposal);

        Set<TreatmentProcedure> treatmentProcedures = disposal.getTreatmentProcedures();
        disposal =  disposalRepository.save(disposal.treatmentProcedures(null));
        relationshipService.addRelationshipWithTreatmentProcedures(disposal.treatmentProcedures(treatmentProcedures));
        disposal.setPrescription(prescription);
        disposal.setTodo(todo);

        return disposal;
    }

    /**
     * Get all the disposals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Disposal> findAll(Pageable pageable) {
        log.debug("Request to get all Disposals");
        return disposalRepository.findAll(pageable);
    }



    /**
     *  get all the disposals where Prescription is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Disposal> findAllWherePrescriptionIsNull() {
        log.debug("Request to get all disposals where Prescription is null");
        return StreamSupport
            .stream(disposalRepository.findAll().spliterator(), false)
            .filter(disposal -> disposal.getPrescription() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the disposals where Todo is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Disposal> findAllWhereTodoIsNull() {
        log.debug("Request to get all disposals where Todo is null");
        return StreamSupport
            .stream(disposalRepository.findAll().spliterator(), false)
            .filter(disposal -> disposal.getTodo() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one disposal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Disposal> findOne(Long id) {
        log.debug("Request to get Disposal : {}", id);
        return disposalRepository.findById(id);
    }

    /**
     * Delete the disposal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Disposal : {}", id);
        disposalRepository.deleteById(id);
    }

    /**
     * Get one disposal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Disposal> findOneWithEagerRelationships(Long id) {
        log.debug("Request to get Disposal : {}", id);
        return disposalRepository.findWithEagerRelationshipsById(id);
    }

    /**
     * Update the disposal.
     *
     * @param updateDisposal the update entity
     */
    public Disposal update(Disposal updateDisposal) {
        log.debug("Request to update Disposal : {}", updateDisposal);

        return disposalRepository
            .findById(updateDisposal.getId())
            .map(disposal -> {
                if (updateDisposal.getStatus() != null) {
                    disposal.setStatus((updateDisposal.getStatus()));
                }

                if (updateDisposal.getTotal() != null) {
                    disposal.setTotal((updateDisposal.getTotal()));
                }

                if (updateDisposal.getPrescription() != null) {
                    disposal.setPrescription(getPrescription(updateDisposal));
                }

                if (updateDisposal.getTodo() != null) {
                    disposal.setTodo(getTodo(updateDisposal));
                }

                if (updateDisposal.getTreatmentProcedures() != null) {
                    relationshipService.addRelationshipWithTreatmentProcedures(disposal.treatmentProcedures(updateDisposal.getTreatmentProcedures()));
                }

                if (updateDisposal.getRegistration() != null && updateDisposal.getRegistration().getId() != null) {
                    registrationRepository.findById(updateDisposal.getRegistration().getId()).ifPresent(disposal::setRegistration);
                }

                return disposal;
            })
            .get();
    }

    private Prescription getPrescription(Disposal disposal) {
        Prescription prescription = disposal.getPrescription();
        if (prescription != null) {
            // delete treatmentDrug when updating
            if (prescription.getId() != null && prescription.getTreatmentDrugs() != null) {
                LongFilter filter = new LongFilter();
                filter.setEquals(prescription.getId());
                TreatmentDrugCriteria criteria = new TreatmentDrugCriteria();
                criteria.setPrescriptionId(filter);
                List<TreatmentDrug> list = treatmentDrugQueryService.findByCriteria(criteria);
                list.removeAll(prescription.getTreatmentDrugs());
                list.forEach(treatmentDrug -> treatmentDrugService.delete(treatmentDrug.getId()));
            }

            prescription = prescription.getId() == null ? prescriptionService.save(prescription) : prescriptionService.update(prescription);
        }

        return prescription;
    }

    private Todo getTodo(Disposal disposal) {
        Todo todo = disposal.getTodo();
        if (todo != null) {
            todo = todo.getId() == null ? todoService.save(todo) : todoService.update(todo);
        }

        return todo;
    }
}
