package io.dentall.totoro.service;

import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.ToothCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing TreatmentProcedure.
 */
@Service
@Transactional
public class TreatmentProcedureService {

    private final Logger log = LoggerFactory.getLogger(TreatmentProcedureService.class);

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    private final ProcedureRepository procedureRepository;

    private final AppointmentRepository appointmentRepository;

    private final RegistrationRepository registrationRepository;

    private final RelationshipService relationshipService;

    private final TreatmentTaskRepository treatmentTaskRepository;

    private final TodoRepository todoRepository;

    private final DisposalRepository disposalRepository;

    private final ToothQueryService toothQueryService;

    private final ToothRepository toothRepository;

    public TreatmentProcedureService(
        TreatmentProcedureRepository treatmentProcedureRepository,
        ProcedureRepository procedureRepository,
        AppointmentRepository appointmentRepository,
        RegistrationRepository registrationRepository,
        RelationshipService relationshipService,
        TreatmentTaskRepository treatmentTaskRepository,
        TodoRepository todoRepository,
        DisposalRepository disposalRepository,
        ToothQueryService toothQueryService,
        ToothRepository toothRepository
    ) {
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.procedureRepository = procedureRepository;
        this.appointmentRepository = appointmentRepository;
        this.registrationRepository = registrationRepository;
        this.relationshipService = relationshipService;
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.todoRepository = todoRepository;
        this.disposalRepository = disposalRepository;
        this.toothQueryService = toothQueryService;
        this.toothRepository = toothRepository;
    }

    /**
     * Save a treatmentProcedure.
     *
     * @param treatmentProcedure the entity to save
     * @return the persisted entity
     */
    public TreatmentProcedure save(TreatmentProcedure treatmentProcedure) {
        log.debug("Request to save TreatmentProcedure : {}", treatmentProcedure);

        Set<Tooth> teeth = treatmentProcedure.getTeeth();
        treatmentProcedure = treatmentProcedureRepository.save(treatmentProcedure.teeth(null));
        relationshipService.addRelationshipWithTeeth(treatmentProcedure.teeth(teeth));

        return treatmentProcedure;
    }

    /**
     * Get all the treatmentProcedures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TreatmentProcedure> findAll(Pageable pageable) {
        log.debug("Request to get all TreatmentProcedures");
        return treatmentProcedureRepository.findAll(pageable);
    }


    /**
     * Get one treatmentProcedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentProcedure> findOne(Long id) {
        log.debug("Request to get TreatmentProcedure : {}", id);
        return treatmentProcedureRepository.findById(id);
    }

    /**
     * Delete the treatmentProcedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentProcedure : {}", id);

        deleteTeethByTreatmentProcedureId(id);
        treatmentProcedureRepository.deleteById(id);
    }

    /**
     * Get one treatmentProcedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentProcedure> findOneWithEagerRelationships(Long id) {
        log.debug("Request to get TreatmentProcedure : {}", id);
        return treatmentProcedureRepository.findById(id);
    }

    /**
     * Update the treatmentProcedure.
     *
     * @param updateTreatmentProcedure the update entity
     * @return the entity
     */
    public TreatmentProcedure update(TreatmentProcedure updateTreatmentProcedure) {
        log.debug("Request to update TreatmentProcedure : {}", updateTreatmentProcedure);

        return treatmentProcedureRepository
            .findById(updateTreatmentProcedure.getId())
            .map(treatmentProcedure -> {
                if (updateTreatmentProcedure.getStatus() != null) {
                    treatmentProcedure.setStatus((updateTreatmentProcedure.getStatus()));
                }

                if (updateTreatmentProcedure.getQuantity() != null) {
                    treatmentProcedure.setQuantity((updateTreatmentProcedure.getQuantity()));
                }

                if (updateTreatmentProcedure.getTotal() != null) {
                    treatmentProcedure.setTotal((updateTreatmentProcedure.getTotal()));
                }

                if (updateTreatmentProcedure.getNote() != null) {
                    treatmentProcedure.setNote((updateTreatmentProcedure.getNote()));
                }

                if (updateTreatmentProcedure.getCompletedDate() != null) {
                    treatmentProcedure.setCompletedDate((updateTreatmentProcedure.getCompletedDate()));
                }

                if (updateTreatmentProcedure.getPrice() != null) {
                    treatmentProcedure.setPrice((updateTreatmentProcedure.getPrice()));
                }

                if (updateTreatmentProcedure.getProcedure() != null && updateTreatmentProcedure.getProcedure().getId() != null) {
                    procedureRepository.findById(updateTreatmentProcedure.getProcedure().getId()).ifPresent(treatmentProcedure::setProcedure);
                }

                if (updateTreatmentProcedure.getAppointment() != null && updateTreatmentProcedure.getAppointment().getId() != null) {
                    appointmentRepository.findById(updateTreatmentProcedure.getAppointment().getId()).ifPresent(treatmentProcedure::setAppointment);
                }

                if (updateTreatmentProcedure.getRegistration() != null && updateTreatmentProcedure.getRegistration().getId() != null) {
                    registrationRepository.findById(updateTreatmentProcedure.getRegistration().getId()).ifPresent(treatmentProcedure::setRegistration);
                }

                if (updateTreatmentProcedure.getTreatmentTask() != null && updateTreatmentProcedure.getTreatmentTask().getId() != null) {
                    treatmentTaskRepository.findById(updateTreatmentProcedure.getTreatmentTask().getId()).ifPresent(treatmentProcedure::setTreatmentTask);
                }

                if (updateTreatmentProcedure.getTodo() != null && updateTreatmentProcedure.getTodo().getId() != null) {
                    todoRepository.findById(updateTreatmentProcedure.getTodo().getId()).ifPresent(treatmentProcedure::setTodo);
                }

                if (updateTreatmentProcedure.getDisposal() != null && updateTreatmentProcedure.getDisposal().getId() != null) {
                    disposalRepository.findById(updateTreatmentProcedure.getDisposal().getId()).ifPresent(treatmentProcedure::setDisposal);
                }

                if (updateTreatmentProcedure.getTeeth() != null) {
                    deleteTeethByTreatmentProcedureId(treatmentProcedure.getId());
                    relationshipService.addRelationshipWithTeeth(treatmentProcedure.teeth(updateTreatmentProcedure.getTeeth()));
                }

                return treatmentProcedure;
            })
            .get();
    }

    private void deleteTeethByTreatmentProcedureId(Long id) {
        LongFilter filter = new LongFilter();
        filter.setEquals(id);
        ToothCriteria criteria = new ToothCriteria();
        criteria.setTreatmentProcedureId(filter);
        toothQueryService
            .findByCriteria(criteria)
            .stream()
            .map(Tooth::getId)
            .forEach(toothRepository::deleteById);
    }
}
