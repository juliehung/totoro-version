package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.service.dto.ToothCriteria;
import io.dentall.totoro.service.dto.TreatmentProcedureCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class RelationshipService {

    private final Logger log = LoggerFactory.getLogger(RelationshipService.class);

    private final TreatmentProcedureService treatmentProcedureService;

    private final TreatmentTaskService treatmentTaskService;

    private final ToothService toothService;

    private final TreatmentDrugService treatmentDrugService;

    private final ToothQueryService toothQueryService;

    private final TreatmentProcedureQueryService treatmentProcedureQueryService;

    public RelationshipService(
        @Lazy TreatmentProcedureService treatmentProcedureService,
        @Lazy TreatmentTaskService treatmentTaskService,
        ToothService toothService,
        TreatmentDrugService treatmentDrugService,
        ToothQueryService toothQueryService,
        TreatmentProcedureQueryService treatmentProcedureQueryService
    ) {
        this.treatmentProcedureService = treatmentProcedureService;
        this.treatmentTaskService = treatmentTaskService;
        this.toothService = toothService;
        this.treatmentDrugService = treatmentDrugService;
        this.toothQueryService = toothQueryService;
        this.treatmentProcedureQueryService = treatmentProcedureQueryService;
    }

    void addRelationshipWithTreatmentTasks(TreatmentPlan treatmentPlan) {
        Set<TreatmentTask> treatmentTasks = treatmentPlan.getTreatmentTasks();
        if (treatmentTasks != null) {
            treatmentTasks = getRelationshipWithOwners(
                treatmentTasks.stream().map(this::getTreatmentTask),
                treatmentTask -> treatmentTask.treatmentPlan(treatmentPlan)
            );
        }

        treatmentPlan.setTreatmentTasks(treatmentTasks);
    }

    void addRelationshipWithTreatmentProcedures(Disposal disposal) {
        Set<TreatmentProcedure> treatmentProcedures = disposal.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures.stream().map(this::getTreatmentProcedure),
                treatmentProcedure -> treatmentProcedure.disposal(disposal)
            );
        }

        disposal.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentProcedures(Todo todo) {
        Set<TreatmentProcedure> treatmentProcedures = todo.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures.stream().map(this::getTreatmentProcedure),
                treatmentProcedure -> treatmentProcedure.todo(todo)
            );
        }

        todo.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentProcedures(TreatmentTask treatmentTask) {
        Set<TreatmentProcedure> treatmentProcedures = treatmentTask.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures.stream().map(this::getTreatmentProcedure),
                treatmentProcedure -> treatmentProcedure.treatmentTask(treatmentTask)
            );
        }

        treatmentTask.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentProcedures(Appointment appointment) {
        Set<TreatmentProcedure> treatmentProcedures = appointment.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures.stream().map(this::getTreatmentProcedure),
                treatmentProcedure -> treatmentProcedure.appointment(appointment)
            );
        }

        appointment.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentDrugs(Prescription prescription) {
        Set<TreatmentDrug> treatmentDrugs = prescription.getTreatmentDrugs();
        if (treatmentDrugs != null) {
            treatmentDrugs = getRelationshipWithOwners(
                treatmentDrugs.stream().map(this::getTreatmentDrug),
                treatmentDrug -> treatmentDrug.prescription(prescription).drug(treatmentDrug.getDrug())
            );
        }

        prescription.setTreatmentDrugs(treatmentDrugs);
    }

    void addRelationshipWithTeeth(TreatmentProcedure treatmentProcedure) {
        Set<Tooth> teeth = treatmentProcedure.getTeeth();
        if (teeth != null) {
            teeth = getRelationshipWithOwners(
                teeth.stream().map(this::getTooth),
                tooth -> tooth.treatmentProcedure(treatmentProcedure)
            );
        }

        treatmentProcedure.setTeeth(teeth);
    }

    void addRelationshipWithTeeth(Disposal disposal) {
        Set<Tooth> teeth = disposal.getTeeth();
        if (teeth != null) {
            teeth = getRelationshipWithOwners(
                teeth.stream().map(this::getTooth),
                tooth -> tooth.disposal(disposal)
            );
        }

        disposal.setTeeth(teeth);
    }

    void addRelationshipWithTeeth(Patient patient) {
        Set<Tooth> teeth = patient.getTeeth();
        if (teeth != null) {
            teeth = getRelationshipWithOwners(
                teeth.stream().map(this::getTooth),
                tooth -> tooth.patient(patient)
            );
        }

        patient.setTeeth(teeth);
    }

    void deleteTreatmentProcedures(Appointment appointment, Set<Long> updateIds) {
        appointment
            .getTreatmentProcedures()
            .stream()
            .map(TreatmentProcedure::getId)
            .filter(id -> !updateIds.contains(id))
            .forEach(treatmentProcedureService::delete);
    }

    void deleteTeeth(Set<Tooth> teeth, Set<Long> updateIds) {
        teeth
            .stream()
            .map(Tooth::getId)
            .filter(id -> !updateIds.contains(id))
            .forEach(toothService::delete);
    }

    void deleteTeethByTreatmentProcedureId(Long id) {
        LongFilter filter = new LongFilter();
        filter.setEquals(id);
        ToothCriteria criteria = new ToothCriteria();
        criteria.setTreatmentProcedureId(filter);
        toothQueryService
            .findByCriteria(criteria)
            .stream()
            .map(Tooth::getId)
            .forEach(toothService::delete);
    }

    void deleteRelationshipWithTreatmentProceduresByTreatmentTaskId(Long id) {
        LongFilter filter = new LongFilter();
        filter.setEquals(id);
        TreatmentProcedureCriteria criteria = new TreatmentProcedureCriteria();
        criteria.setTreatmentTaskId(filter);
        treatmentProcedureQueryService
            .findByCriteria(criteria)
            .stream()
            .map(TreatmentProcedure::getId)
            .forEach(treatmentProcedureService::delete);
    }

    void deleteRelationshipWithTreatmentProcedures(Disposal disposal, Set<Long> updateIds) {
        disposal
            .getTreatmentProcedures()
            .forEach(treatmentProcedure -> {
                if (!updateIds.contains(treatmentProcedure.getId())) {
                    treatmentProcedure.setDisposal(null);
                }
            });
    }

    private <Owner> Set<Owner> getRelationshipWithOwners(Stream<Owner> owners, Function<Owner, Owner> mapper) {
        return owners
            .map(mapper)
            .collect(Collectors.toSet());
    }

    private TreatmentProcedure getTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        return treatmentProcedure.getId() == null ? treatmentProcedureService.save(treatmentProcedure) : treatmentProcedureService.update(treatmentProcedure);
    }

    private TreatmentDrug getTreatmentDrug(TreatmentDrug treatmentDrug) {
        return treatmentDrug.getId() == null ? treatmentDrugService.save(treatmentDrug) : treatmentDrugService.update(treatmentDrug);
    }

    private Tooth getTooth(Tooth tooth) {
        return tooth.getId() == null ? toothService.save(tooth) : toothService.update(tooth);
    }

    private TreatmentTask getTreatmentTask(TreatmentTask treatmentTask) {
        return treatmentTask.getId() == null ? treatmentTaskService.save(treatmentTask) : treatmentTaskService.update(treatmentTask);
    }
}
