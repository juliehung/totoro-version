package io.dentall.totoro.service;

import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.repository.TreatmentPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing TreatmentPlan.
 */
@Service
@Transactional
public class TreatmentPlanService {

    private final Logger log = LoggerFactory.getLogger(TreatmentPlanService.class);

    private final TreatmentPlanRepository treatmentPlanRepository;

    public TreatmentPlanService(TreatmentPlanRepository treatmentPlanRepository) {
        this.treatmentPlanRepository = treatmentPlanRepository;
    }

    /**
     * Save a treatmentPlan.
     *
     * @param treatmentPlan the entity to save
     * @return the persisted entity
     */
    public TreatmentPlan save(TreatmentPlan treatmentPlan) {
        log.debug("Request to save TreatmentPlan : {}", treatmentPlan);
        return treatmentPlanRepository.save(treatmentPlan);
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
        treatmentPlanRepository.deleteById(id);
    }

    /**
     * Update the treatmentPlan.
     *
     * @param updateTreatmentPlan the update entity
     */
    public void update(TreatmentPlan updateTreatmentPlan) {
        log.debug("Request to update TreatmentPlan : {}", updateTreatmentPlan);
        treatmentPlanRepository.findById(updateTreatmentPlan.getId()).ifPresent(treatmentPlan -> {
            if (updateTreatmentPlan.isActivated() != null) {
                treatmentPlan.setActivated((updateTreatmentPlan.isActivated()));
            }
        });
    }
}
