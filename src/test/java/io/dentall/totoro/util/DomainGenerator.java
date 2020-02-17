package io.dentall.totoro.util;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.TreatmentQueryService;
import io.dentall.totoro.service.dto.TreatmentCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = TotoroApp.class)
@Transactional
@Service
public class DomainGenerator {

    @Autowired
    private PatientService patientService;

    @Autowired
    private TreatmentQueryService treatmentQueryService;

    @Autowired
    private TreatmentProcedureRepository treatmentProcedureRepository;

    @Autowired
    private NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    @Autowired
    private DisposalRepository disposalRepository;

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    /**
     * Generate a new patient and treatment, treatment plan, treatment task
     * @param name
     * @param phone
     * @return
     */
    public Patient generatePatientAndTxFamily(String name, String phone) {
        Patient p = new Patient().name(name).phone(phone);
        return patientService.save(p);
    }

    /**
     * Before using this function make sure you already have treatment family. If not, then call `generatePatientAndTxFamily`
     *
     * @param patient
     * @param a13
     * @param a17
     * @param a73
     * @param a74
     * @param patientIdentity
     * @return
     */
    public TreatmentProcedure generateTreatmentProcedureToDisposal(
        Patient patient,
        String a13,
        String a17,
        String a73,
        String a74,
        String patientIdentity
    ) {
        // Query patient's all nhi treatment procedures
        TreatmentCriteria treatmentCriteria = new TreatmentCriteria();
        LongFilter lf = new LongFilter();
        lf.setEquals(patient.getId());
        treatmentCriteria.setPatientId(lf);
        TreatmentTask treatmentTask = treatmentQueryService.findByCriteria(treatmentCriteria).stream()
            .findFirst()
            .get()
            .getTreatmentPlans().iterator().next()
            .getTreatmentTasks().iterator().next();

        Disposal disposal = new Disposal().status(DisposalStatus.PERMANENT);
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure().status(TreatmentProcedureStatus.COMPLETED);
        NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal().a17(a17).uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure().a73(a73).a74(a74).check("");

        // Save disposal
        disposal = disposalRepository.saveAndFlush(disposal);
        // Save nhi disposal with relationship
        Set<NhiExtendDisposal> nhiExtendDisposals = new HashSet<>();
        nhiExtendDisposals.add(nhiExtendDisposal);
        disposal.nhiExtendDisposals(nhiExtendDisposals);
        nhiExtendDisposal.setA13(a13);
        nhiExtendDisposal.disposal(disposal);
        nhiExtendDisposal.setPatientIdentity(patientIdentity);
        nhiExtendDisposalRepository.saveAndFlush(nhiExtendDisposal);
        // Save treatment with relationship
        treatmentTask.getTreatmentProcedures().add(treatmentProcedure);
        treatmentProcedure.disposal(disposal);
        treatmentProcedure.treatmentTask(treatmentTask);
        treatmentProcedure = treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        // Save nhi treatment with relationship
        treatmentProcedure.nhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
        nhiExtendTreatmentProcedure.treatmentProcedure(treatmentProcedure);
        nhiExtendTreatmentProcedureRepository.saveAndFlush(nhiExtendTreatmentProcedure);

        return treatmentProcedure;
    }

    public TreatmentProcedure generateTreatmentProcedureToDisposal(
        Patient patient,
        String a17,
        String a73,
        String a74
    ) {
        // Query patient's all nhi treatment procedures
        TreatmentCriteria treatmentCriteria = new TreatmentCriteria();
        LongFilter lf = new LongFilter();
        lf.setEquals(patient.getId());
        treatmentCriteria.setPatientId(lf);
        TreatmentTask treatmentTask = treatmentQueryService.findByCriteria(treatmentCriteria).stream()
            .findFirst()
            .get()
            .getTreatmentPlans().iterator().next()
            .getTreatmentTasks().iterator().next();

        Disposal disposal = new Disposal().status(DisposalStatus.PERMANENT);
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure().status(TreatmentProcedureStatus.COMPLETED);
        NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal().a17(a17).uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure().a73(a73).a74(a74).check("");

        // Save disposal
        disposal = disposalRepository.saveAndFlush(disposal);
        // Save nhi disposal with relationship
        Set<NhiExtendDisposal> nhiExtendDisposals = new HashSet<>();
        nhiExtendDisposals.add(nhiExtendDisposal);
        disposal.nhiExtendDisposals(nhiExtendDisposals);
        nhiExtendDisposal.disposal(disposal);
        nhiExtendDisposalRepository.saveAndFlush(nhiExtendDisposal);
        // Save treatment with relationship
        treatmentTask.getTreatmentProcedures().add(treatmentProcedure);
        treatmentProcedure.disposal(disposal);
        treatmentProcedure.treatmentTask(treatmentTask);
        treatmentProcedure = treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        // Save nhi treatment with relationship
        treatmentProcedure.nhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
        nhiExtendTreatmentProcedure.treatmentProcedure(treatmentProcedure);
        nhiExtendTreatmentProcedureRepository.saveAndFlush(nhiExtendTreatmentProcedure);

        return treatmentProcedure;
    }

    public TreatmentProcedure addTreatmentProcedureToDisposalAndTreatmentFamily(
        Disposal disposal,
        TreatmentTask treatmentTask,
        String a73,
        String a74
    ) {
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure().status(TreatmentProcedureStatus.COMPLETED);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure().a73(a73).a74(a74).check("");

        // Save treatment with relationship
        treatmentTask.getTreatmentProcedures().add(treatmentProcedure);
        treatmentProcedure.disposal(disposal);
        treatmentProcedure.treatmentTask(treatmentTask);
        treatmentProcedure = treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        // Save nhi treatment with relationship
        treatmentProcedure.nhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
        nhiExtendTreatmentProcedure.treatmentProcedure(treatmentProcedure);
        nhiExtendTreatmentProcedureRepository.saveAndFlush(nhiExtendTreatmentProcedure);

        return treatmentProcedure;
    }

}
