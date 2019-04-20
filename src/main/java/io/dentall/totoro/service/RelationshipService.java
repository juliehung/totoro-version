package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
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

    public RelationshipService(
        @Lazy TreatmentProcedureService treatmentProcedureService,
        @Lazy TreatmentTaskService treatmentTaskService,
        ToothService toothService,
        TreatmentDrugService treatmentDrugService
    ) {
        this.treatmentProcedureService = treatmentProcedureService;
        this.treatmentTaskService = treatmentTaskService;
        this.toothService = toothService;
        this.treatmentDrugService = treatmentDrugService;
    }

    void addRelationshipWithTreatmentTasks(TreatmentPlan treatmentPlan) {
        Set<TreatmentTask> treatmentTasks = treatmentPlan.getTreatmentTasks();
        if (treatmentTasks != null) {
            treatmentTasks = getRelationshipWithOwners(
                treatmentTasks
                    .stream()
                    .map(this::getTreatmentTask),
                treatmentTask -> {
                    treatmentTask.setTreatmentPlan(treatmentPlan);
                    addRelationshipWithTreatmentProcedures(treatmentTask);

                    return treatmentTask;
                }
            );
        }

        treatmentPlan.setTreatmentTasks(treatmentTasks);
    }

    void addRelationshipWithTreatmentProcedures(Disposal disposal) {
        Set<TreatmentProcedure> treatmentProcedures = disposal.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures
                    .stream()
                    .map(this::getTreatmentProcedure),
                treatmentProcedure -> {
                    treatmentProcedure.setDisposal(disposal);
                    addRelationshipWithTeeth(treatmentProcedure);

                    return treatmentProcedure;
                }
            );
        }

        disposal.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentProcedures(Todo todo) {
        Set<TreatmentProcedure> treatmentProcedures = todo.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures
                    .stream()
                    .map(this::getTreatmentProcedure),
                treatmentProcedure -> {
                    treatmentProcedure.setTodo(todo);
                    addRelationshipWithTeeth(treatmentProcedure);

                    return treatmentProcedure;
                }
            );
        }

        todo.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentProcedures(TreatmentTask treatmentTask) {
        Set<TreatmentProcedure> treatmentProcedures = treatmentTask.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures
                    .stream()
                    .map(this::getTreatmentProcedure),
                treatmentProcedure -> {
                    treatmentProcedure.setTreatmentTask(treatmentTask);
                    addRelationshipWithTeeth(treatmentProcedure);

                    return treatmentProcedure;
                }
            );
        }

        treatmentTask.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentProcedures(Appointment appointment) {
        Set<TreatmentProcedure> treatmentProcedures = appointment.getTreatmentProcedures();
        if (treatmentProcedures != null) {
            treatmentProcedures = getRelationshipWithOwners(
                treatmentProcedures
                    .stream()
                    .map(this::getTreatmentProcedure),
                treatmentProcedure -> {
                    treatmentProcedure.setAppointment(appointment);
                    addRelationshipWithTeeth(treatmentProcedure);

                    return treatmentProcedure;
                }
            );
        }

        appointment.setTreatmentProcedures(treatmentProcedures);
    }

    void addRelationshipWithTreatmentDrugs(Prescription prescription) {
        Set<TreatmentDrug> treatmentDrugs = prescription.getTreatmentDrugs();
        if (treatmentDrugs != null) {
            treatmentDrugs = getRelationshipWithOwners(
                treatmentDrugs
                    .stream()
                    .map(this::getTreatmentDrug),
                treatmentDrug -> {
                    treatmentDrug.setPrescription(prescription);
                    treatmentDrug.setDrug(treatmentDrug.getDrug());

                    return treatmentDrug;
                }
            );
        }

        prescription.setTreatmentDrugs(treatmentDrugs);
    }

    void addRelationshipWithTeeth(TreatmentProcedure treatmentProcedure) {
        Set<Tooth> teeth = treatmentProcedure.getTeeth();
        if (teeth != null) {
            teeth = getRelationshipWithOwners(
                teeth
                    .stream()
                    .map(this::getTooth),
                tooth -> {
                    tooth.setTreatmentProcedure(treatmentProcedure);

                    return tooth;
                }
            );
        }

        treatmentProcedure.setTeeth(teeth);
    }

    void addRelationshipWithTeeth(Disposal disposal) {
        Set<Tooth> teeth = disposal.getTeeth();
        if (teeth != null) {
            teeth = getRelationshipWithOwners(
                teeth
                    .stream()
                    .map(this::getTooth),
                tooth -> {
                    tooth.setDisposal(disposal);

                    return tooth;
                }
            );
        }

        disposal.setTeeth(teeth);
    }

    void addRelationshipWithTeeth(Patient patient) {
        Set<Tooth> teeth = patient.getTeeth();
        if (teeth != null) {
            teeth = getRelationshipWithOwners(
                teeth
                    .stream()
                    .map(this::getTooth),
                tooth -> {
                    tooth.setPatient(patient);

                    return tooth;
                }
            );
        }

        patient.setTeeth(teeth);
    }

    void deleteRelationshipWithTreatmentProcedures(Appointment appointment, Appointment updateAppointment) {
        Set<Long> updateIds = updateAppointment.getTreatmentProcedures().stream().map(TreatmentProcedure::getId).collect(Collectors.toSet());
        appointment
            .getTreatmentProcedures()
            .stream()
            .map(TreatmentProcedure::getId)
            .filter(id -> !updateIds.contains(id))
            .forEach(treatmentProcedureService::delete);
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
