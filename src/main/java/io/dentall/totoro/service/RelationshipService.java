package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.service.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
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

    private final NhiExtendDisposalService nhiExtendDisposalService;

    private final NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService;

    private final NhiExtendTreatmentDrugService nhiExtendTreatmentDrugService;

    private final NhiDayUploadDetailsService nhiDayUploadDetailsService;

    private final NhiMedicalRecordService nhiMedicalRecordService;

    private final TreatmentPlanService treatmentPlanService;

    public RelationshipService(
        @Lazy TreatmentProcedureService treatmentProcedureService,
        @Lazy TreatmentTaskService treatmentTaskService,
        ToothService toothService,
        TreatmentDrugService treatmentDrugService,
        @Lazy NhiExtendDisposalService nhiExtendDisposalService,
        NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService,
        NhiExtendTreatmentDrugService nhiExtendTreatmentDrugService,
        @Lazy NhiDayUploadDetailsService nhiDayUploadDetailsService,
        NhiMedicalRecordService nhiMedicalRecordService,
        @Lazy TreatmentPlanService treatmentPlanService
    ) {
        this.treatmentProcedureService = treatmentProcedureService;
        this.treatmentTaskService = treatmentTaskService;
        this.toothService = toothService;
        this.treatmentDrugService = treatmentDrugService;
        this.nhiExtendDisposalService = nhiExtendDisposalService;
        this.nhiExtendTreatmentProcedureService = nhiExtendTreatmentProcedureService;
        this.nhiExtendTreatmentDrugService = nhiExtendTreatmentDrugService;
        this.nhiDayUploadDetailsService = nhiDayUploadDetailsService;
        this.nhiMedicalRecordService = nhiMedicalRecordService;
        this.treatmentPlanService = treatmentPlanService;
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

    void addRelationshipWithNhiExtendDisposals(Disposal disposal) {
        Set<NhiExtendDisposal> nhiExtendDisposals = disposal.getNhiExtendDisposals();
        if (nhiExtendDisposals != null) {
            nhiExtendDisposals = getRelationshipWithOwners(
                nhiExtendDisposals.stream().map(this::getNhiExtendDisposal),
                nhiExtendDisposal -> nhiExtendDisposal.disposal(disposal)
            );
        }

        disposal.setNhiExtendDisposals(nhiExtendDisposals);
    }

    void addRelationshipWithNhiExtendTreatmentProcedures(NhiExtendDisposal nhiExtendDisposal, Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        if (nhiExtendTreatmentProcedures != null) {
            nhiExtendTreatmentProcedures = getRelationshipWithOwners(
                nhiExtendTreatmentProcedures.stream().map(this::getNhiExtendTreatmentProcedure),
                nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.nhiExtendDisposal(nhiExtendDisposal)
            );

            nhiExtendDisposal.setNhiExtendTreatmentProcedures(nhiExtendTreatmentProcedures);
        }
    }

    void addRelationshipWithNhiExtendTreatmentDrugs(NhiExtendDisposal nhiExtendDisposal, Set<NhiExtendTreatmentDrug> nhiExtendTreatmentDrugs) {
        if (nhiExtendTreatmentDrugs != null) {
            nhiExtendTreatmentDrugs = getRelationshipWithOwners(
                nhiExtendTreatmentDrugs.stream().map(this::getNhiExtendTreatmentDrug),
                nhiExtendTreatmentDrug -> nhiExtendTreatmentDrug.nhiExtendDisposal(nhiExtendDisposal)
            );

            nhiExtendDisposal.setNhiExtendTreatmentDrugs(nhiExtendTreatmentDrugs);
        }
    }

    void addRelationshipWithNhiDayUploadDetails(NhiDayUpload nhiDayUpload, Set<NhiDayUploadDetails> nhiDayUploadDetails) {
        if (nhiDayUploadDetails != null) {
            nhiDayUploadDetails = getRelationshipWithOwners(
                nhiDayUploadDetails.stream().map(this::getNhiDayUploadDetails),
                nhiDayUploadDetail -> nhiDayUploadDetail.nhiDayUpload(nhiDayUpload)
            );

            nhiDayUpload.setNhiDayUploadDetails(nhiDayUploadDetails);
        }
    }

    void addRelationshipWithNhiExtendDisposals(NhiDayUploadDetails nhiDayUploadDetail, Set<NhiExtendDisposal> nhiExtendDisposals) {
        if (nhiExtendDisposals != null) {
            nhiExtendDisposals = nhiExtendDisposals
                .stream()
                .map(this::getNhiExtendDisposal)
                .collect(Collectors.toSet());

            nhiDayUploadDetail.setNhiExtendDisposals(nhiExtendDisposals);
        }
    }

    void addRelationshipWithNhiMedicalRecords(NhiExtendPatient nhiExtendPatient, Set<NhiMedicalRecord> nhiMedicalRecords) {
        if (nhiMedicalRecords != null) {
            nhiMedicalRecords = getRelationshipWithOwners(
                nhiMedicalRecords.stream().map(this::getNhiMedicalRecord),
                nhiMedicalRecord -> nhiMedicalRecord.nhiExtendPatient(nhiExtendPatient)
            );

            nhiExtendPatient.setNhiMedicalRecords(
                // https://www.techempower.com/blog/2016/10/19/efficient-multiple-stream-concatenation-in-java/
                Stream
                    .concat(StreamUtil.asStream(nhiExtendPatient.getNhiMedicalRecords()), StreamUtil.asStream(nhiMedicalRecords))
                    .collect(Collectors.toSet())
            );
        }
    }

    void deleteTeeth(Set<Tooth> teeth) {
        StreamUtil.asStream(teeth)
            .map(Tooth::getId)
            .forEach(toothService::delete);
    }

    void deleteTreatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        StreamUtil.asStream(treatmentProcedures)
            .map(TreatmentProcedure::getId)
            .forEach(treatmentProcedureService::delete);
    }

    void deleteTreatmentPlans(Set<TreatmentPlan> treatmentPlans) {
        StreamUtil.asStream(treatmentPlans)
            .map(TreatmentPlan::getId)
            .forEach(treatmentPlanService::delete);
    }

    void deleteTreatmentTasks(Set<TreatmentTask> treatmentTasks) {
        StreamUtil.asStream(treatmentTasks)
            .map(TreatmentTask::getId)
            .forEach(treatmentTaskService::delete);
    }

    void deleteTreatmentDrugs(Set<TreatmentDrug> treatmentDrugs) {
        StreamUtil.asStream(treatmentDrugs)
            .map(TreatmentDrug::getId)
            .forEach(treatmentDrugService::delete);
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

    private NhiExtendDisposal getNhiExtendDisposal(NhiExtendDisposal nhiExtendDisposal) {
        return nhiExtendDisposal.getId() == null ? nhiExtendDisposalService.save(nhiExtendDisposal) : nhiExtendDisposalService.update(nhiExtendDisposal);
    }

    private NhiExtendTreatmentProcedure getNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        return nhiExtendTreatmentProcedure.getId() == null ? nhiExtendTreatmentProcedureService.save(nhiExtendTreatmentProcedure) : nhiExtendTreatmentProcedureService.update(nhiExtendTreatmentProcedure);
    }

    private NhiExtendTreatmentDrug getNhiExtendTreatmentDrug(NhiExtendTreatmentDrug nhiExtendTreatmentDrug) {
        return nhiExtendTreatmentDrug.getId() == null ? nhiExtendTreatmentDrugService.save(nhiExtendTreatmentDrug) : nhiExtendTreatmentDrugService.update(nhiExtendTreatmentDrug);
    }

    private NhiDayUploadDetails getNhiDayUploadDetails(NhiDayUploadDetails nhiDayUploadDetails) {
        return nhiDayUploadDetails.getId() == null ? nhiDayUploadDetailsService.save(nhiDayUploadDetails) : nhiDayUploadDetailsService.update(nhiDayUploadDetails);
    }

    private NhiMedicalRecord getNhiMedicalRecord(NhiMedicalRecord nhiMedicalRecord) {
        return nhiMedicalRecord.getId() == null ? nhiMedicalRecordService.save(nhiMedicalRecord) : nhiMedicalRecordService.update(nhiMedicalRecord);
    }
}
