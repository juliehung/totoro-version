package io.dentall.totoro.service;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.repository.TreatmentPlanRepository;
import io.dentall.totoro.repository.TreatmentTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing TreatmentTask.
 */
@Service
@Transactional
public class TreatmentTaskService {

    private final Logger log = LoggerFactory.getLogger(TreatmentTaskService.class);

    private final TreatmentTaskRepository treatmentTaskRepository;

    private final RelationshipService relationshipService;

    private final TreatmentPlanRepository treatmentPlanRepository;

    public TreatmentTaskService(
        TreatmentTaskRepository treatmentTaskRepository,
        RelationshipService relationshipService,
        TreatmentPlanRepository treatmentPlanRepository
    ) {
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.relationshipService = relationshipService;
        this.treatmentPlanRepository = treatmentPlanRepository;
    }

    /**
     * Save a treatmentTask.
     *
     * @param treatmentTask the entity to save
     * @return the persisted entity
     */
    public TreatmentTask save(TreatmentTask treatmentTask) {
        log.debug("Request to save TreatmentTask : {}", treatmentTask);

        Set<TreatmentProcedure> treatmentProcedures = treatmentTask.getTreatmentProcedures();
        treatmentTask = treatmentTaskRepository.save(treatmentTask.treatmentProcedures(null));
        relationshipService.addRelationshipWithTreatmentProcedures(treatmentTask.treatmentProcedures(treatmentProcedures));

        return treatmentTask;
    }

    /**
     * Get all the treatmentTasks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TreatmentTask> findAll(Pageable pageable) {
        log.debug("Request to get all TreatmentTasks");
        return treatmentTaskRepository.findAll(pageable);
    }


    /**
     * Get one treatmentTask by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentTask> findOne(Long id) {
        log.debug("Request to get TreatmentTask : {}", id);
        return treatmentTaskRepository.findById(id);
    }

    /**
     * Delete the treatmentTask by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentTask : {}", id);
        treatmentTaskRepository.deleteById(id);
    }

    /**
     * Update the treatmentTask.
     *
     * @param updateTreatmentTask the update entity
     */
    public TreatmentTask update(TreatmentTask updateTreatmentTask) {
        log.debug("Request to update TreatmentTask : {}", updateTreatmentTask);

        return treatmentTaskRepository
            .findById(updateTreatmentTask.getId())
            .map(treatmentTask -> {
                if (updateTreatmentTask.getName() != null) {
                    treatmentTask.setName((updateTreatmentTask.getName()));
                }

                if (updateTreatmentTask.getNote() != null) {
                    treatmentTask.setNote((updateTreatmentTask.getNote()));
                }

                if (updateTreatmentTask.getTreatmentPlan() != null && updateTreatmentTask.getTreatmentPlan().getId() != null) {
                    treatmentPlanRepository.findById(updateTreatmentTask.getTreatmentPlan().getId()).ifPresent(treatmentTask::setTreatmentPlan);
                }

                if (updateTreatmentTask.getTreatmentProcedures() != null) {
                    relationshipService.addRelationshipWithTreatmentProcedures(treatmentTask.treatmentProcedures(updateTreatmentTask.getTreatmentProcedures()));
                }

                return treatmentTask;
            })
            .get();
    }
}
