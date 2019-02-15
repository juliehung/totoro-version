package io.dentall.totoro.service;

import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.repository.TreatmentPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing TreatmentPlan.
 */
@Service
@Transactional
public class TreatmentPlanService {

    private final Logger log = LoggerFactory.getLogger(TreatmentPlanService.class);

    private final TreatmentPlanRepository treatmentPlanRepository;

    private final TreatmentTaskService treatmentTaskService;

    private final TreatmentProcedureService treatmentProcedureService;

    private final ToothService toothService;

    public TreatmentPlanService(
        TreatmentPlanRepository treatmentPlanRepository,
        TreatmentTaskService treatmentTaskService,
        TreatmentProcedureService treatmentProcedureService,
        ToothService toothService
    ) {
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.treatmentTaskService = treatmentTaskService;
        this.treatmentProcedureService = treatmentProcedureService;
        this.toothService = toothService;
    }

    /**
     * Save a treatmentPlan.
     *
     * @param treatmentPlan the entity to save
     * @return the persisted entity
     */
    public TreatmentPlan save(TreatmentPlan treatmentPlan) {
        log.debug("Request to save TreatmentPlan : {}", treatmentPlan);

        TreatmentPlan result = treatmentPlanRepository.save(treatmentPlan);
        addRelationshipWithTreatmentTasks(result);
        result.setTreatmentTasks(result.getTreatmentTasks());

        return result;
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

    private void addRelationshipWithTreatmentTasks(TreatmentPlan treatmentPlan) {
        Set<TreatmentTask> treatmentTasks = treatmentPlan.getTreatmentTasks();
        if (treatmentTasks != null) {
            treatmentTasks = addRelationshipWithOwners(
                treatmentTasks.stream().map(TreatmentTask::getId).map(treatmentTaskService::findOne),
                treatmentTask -> {
                    treatmentTask.setTreatmentPlan(treatmentPlan);
                    addRelationshipWithTreatmentProcedures(treatmentTask);

                    return treatmentTask;
                }
            );
        }

        treatmentPlan.setTreatmentTasks(treatmentTasks);
    }

    private void addRelationshipWithTreatmentProcedures(TreatmentTask treatmentTask) {
        Set<TreatmentProcedure> treatmentProcedures = treatmentTask.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = addRelationshipWithOwners(
                treatmentProcedures.stream().map(TreatmentProcedure::getId).map(treatmentProcedureService::findOne),
                treatmentProcedure -> {
                    treatmentProcedure.setTreatmentTask(treatmentTask);
                    addRelationshipWithTeeth(treatmentProcedure);

                    return treatmentProcedure;
                }
            );
        }

        treatmentTask.setTreatmentProcedures(treatmentProcedures);
    }

    private void addRelationshipWithTeeth(TreatmentProcedure treatmentProcedure) {
        Set<Tooth> teeth = treatmentProcedure.getTeeth();
        if (teeth != null) {
            teeth = addRelationshipWithOwners(
                teeth.stream().map(Tooth::getId).map(toothService::findOne),
                tooth -> {
                    tooth.setTreatmentProcedure(treatmentProcedure);

                    return tooth;
                }
            );
        }

        treatmentProcedure.setTeeth(teeth);
    }

    private <Owner> Set<Owner> addRelationshipWithOwners(Stream<Optional<Owner>> owners, Function<Owner, Owner> mapper) {
        return owners
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(mapper)
            .collect(Collectors.toSet());
    }
}
