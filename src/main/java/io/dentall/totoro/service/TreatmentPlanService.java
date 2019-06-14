package io.dentall.totoro.service;

import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.repository.TreatmentPlanRepository;
import io.dentall.totoro.repository.TreatmentRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.service.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing TreatmentPlan.
 */
@Service
@Transactional
public class TreatmentPlanService {

    private final Logger log = LoggerFactory.getLogger(TreatmentPlanService.class);

    private final TreatmentPlanRepository treatmentPlanRepository;

    private final RelationshipService relationshipService;

    private final TreatmentRepository treatmentRepository;

    public TreatmentPlanService(
        TreatmentPlanRepository treatmentPlanRepository,
        RelationshipService relationshipService,
        TreatmentRepository treatmentRepository
    ) {
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.relationshipService = relationshipService;
        this.treatmentRepository = treatmentRepository;
    }

    /**
     * Save a treatmentPlan.
     *
     * @param treatmentPlan the entity to save
     * @return the persisted entity
     */
    public TreatmentPlan save(TreatmentPlan treatmentPlan) {
        log.debug("Request to save TreatmentPlan : {}", treatmentPlan);

        Set<TreatmentTask> treatmentTasks = treatmentPlan.getTreatmentTasks();
        treatmentPlan = treatmentPlanRepository.save(treatmentPlan.treatmentTasks(null));
        relationshipService.addRelationshipWithTreatmentTasks(treatmentPlan.treatmentTasks(treatmentTasks));

        return treatmentPlan;
    }

    /**
     * Get all the treatmentPlans.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TreatmentPlan> findAll() {
        log.debug("Request to get all TreatmentPlans");
        return treatmentPlanRepository.findAll();
    }


    /**
     * Get one treatmentPlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentPlan> findOne(Long id) {
        log.debug("Request to get TreatmentPlan : {}", id);
        return treatmentPlanRepository.findById(id);
    }

    /**
     * Delete the treatmentPlan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentPlan : {}", id);

        treatmentPlanRepository.findById(id).ifPresent(treatmentPlan -> {
            if (treatmentPlan.isActivated()) {
                throw new ProblemUtil("An activated treatmentPlan cannot delete", Status.BAD_REQUEST);
            }

            StreamUtil.asStream(treatmentPlan.getTreatmentTasks()).forEach(treatmentTask -> treatmentTask.setTreatmentPlan(null));
            relationshipService.deleteTreatmentTasks(treatmentPlan.getTreatmentTasks());

            if (treatmentPlan.getTreatment() != null) {
                Treatment treatment = treatmentPlan.getTreatment();
                treatment.getTreatmentPlans().remove(treatmentPlan);
            }

            treatmentPlanRepository.deleteById(id);
        });
    }

    /**
     * Update the treatmentPlan.
     *
     * @param updateTreatmentPlan the update entity
     */
    public TreatmentPlan update(TreatmentPlan updateTreatmentPlan) {
        log.debug("Request to update TreatmentPlan : {}", updateTreatmentPlan);

        return treatmentPlanRepository
            .findById(updateTreatmentPlan.getId())
            .map(treatmentPlan -> {
                if (updateTreatmentPlan.isActivated() != null) {
                    treatmentPlan.setActivated((updateTreatmentPlan.isActivated()));
                }

                if (updateTreatmentPlan.getName() != null) {
                    treatmentPlan.setName((updateTreatmentPlan.getName()));
                }

                if (updateTreatmentPlan.getTreatment() != null && updateTreatmentPlan.getTreatment().getId() != null) {
                    treatmentRepository.findById(updateTreatmentPlan.getTreatment().getId()).ifPresent(treatmentPlan::setTreatment);
                }

                if (updateTreatmentPlan.getTreatmentTasks() != null) {
                    relationshipService.addRelationshipWithTreatmentTasks(treatmentPlan.treatmentTasks(updateTreatmentPlan.getTreatmentTasks()));
                }

                return treatmentPlan;
            })
            .get();
    }
}
