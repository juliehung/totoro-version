package io.dentall.totoro.service;

import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final RelationshipService relationshipService;

    private final TreatmentTaskRepository treatmentTaskRepository;

    private final TodoRepository todoRepository;

    private final DisposalRepository disposalRepository;

    private final NhiProcedureRepository nhiProcedureRepository;

    public TreatmentProcedureService(
        TreatmentProcedureRepository treatmentProcedureRepository,
        ProcedureRepository procedureRepository,
        AppointmentRepository appointmentRepository,
        RelationshipService relationshipService,
        TreatmentTaskRepository treatmentTaskRepository,
        TodoRepository todoRepository,
        DisposalRepository disposalRepository,
        NhiProcedureRepository nhiProcedureRepository
    ) {
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.procedureRepository = procedureRepository;
        this.appointmentRepository = appointmentRepository;
        this.relationshipService = relationshipService;
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.todoRepository = todoRepository;
        this.disposalRepository = disposalRepository;
        this.nhiProcedureRepository = nhiProcedureRepository;
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

        relationshipService.deleteTeethByTreatmentProcedureId(id);
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

                if (updateTreatmentProcedure.getNhiCategory() != null) {
                    treatmentProcedure.setNhiCategory(updateTreatmentProcedure.getNhiCategory());
                }

                if (updateTreatmentProcedure.getNhiDescription() != null) {
                    treatmentProcedure.setNhiDescription(updateTreatmentProcedure.getNhiDescription());
                }

                if (updateTreatmentProcedure.getProcedure() != null && updateTreatmentProcedure.getProcedure().getId() != null) {
                    procedureRepository.findById(updateTreatmentProcedure.getProcedure().getId()).ifPresent(treatmentProcedure::setProcedure);
                }

                if (updateTreatmentProcedure.getNhiProcedure() != null && updateTreatmentProcedure.getNhiProcedure().getId() != null) {
                    nhiProcedureRepository.findById(updateTreatmentProcedure.getNhiProcedure().getId()).ifPresent(treatmentProcedure::setNhiProcedure);
                }

                if (updateTreatmentProcedure.getAppointment() != null && updateTreatmentProcedure.getAppointment().getId() != null) {
                    appointmentRepository.findById(updateTreatmentProcedure.getAppointment().getId()).ifPresent(treatmentProcedure::setAppointment);
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
                    log.debug("Update teeth({}) of TreatmentProcedure(id: {})", updateTreatmentProcedure.getTeeth(), updateTreatmentProcedure.getId());
                    relationshipService.deleteTeeth(
                        treatmentProcedure.getTeeth(),
                        updateTreatmentProcedure.getTeeth().stream().map(Tooth::getId).collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTeeth(treatmentProcedure.teeth(updateTreatmentProcedure.getTeeth()));
                }

                return treatmentProcedure;
            })
            .get();
    }
}
