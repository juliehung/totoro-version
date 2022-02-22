package io.dentall.totoro.aop;

import io.dentall.totoro.business.service.UpdatePatientNhiStatus;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.service.PatientService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@Aspect
public class PatientNhiStatusAspect {

    private static final Logger log = LoggerFactory.getLogger(PatientNhiStatusAspect.class);

    private final PatientService patientService;

    enum Trigger{
        APPOINTMENT_POST,
        NHI_EXTEND_PATIENT_PUT,
        DISPOSAL_POST,
        DISPOSAL_PUT,
    }

    public PatientNhiStatusAspect(
        PatientService patientService
    ) {
        this.patientService = patientService;
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(postMapping) && args(appointment)")
    public void updatePatientNhiStatusAtAppointment(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        PostMapping postMapping,
        Appointment appointment
    ) {
        if (appointment == null ||
            appointment.getPatient() != null ||
            appointment.getPatient().getId() != null
        ) {
            log.error("[PatientNhiStatusAspect] Empty appointment, patient or patient id at " + Trigger.APPOINTMENT_POST.toString());
            return ;
        }

        updatePatientNhiStatus(
            Trigger.APPOINTMENT_POST,
            appointment.getPatient().getId()
        );
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(putMapping) && args(nhiExtendPatients)")
    public void updatePatientNhiStatusAtNhiExtendPatient(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        PutMapping putMapping,
        NhiExtendPatient nhiExtendPatients
    ) {
        if (nhiExtendPatients != null ||
            nhiExtendPatients.getPatient() != null ||
            nhiExtendPatients.getPatient().getId() != null
        ) {
            log.error("[PatientNhiStatusAspect] Empty nhi extend patient, patient or patient id at " + Trigger.NHI_EXTEND_PATIENT_PUT.toString());
            return;
        }

        updatePatientNhiStatus(
            Trigger.NHI_EXTEND_PATIENT_PUT,
            nhiExtendPatients.getPatient().getId()
        );
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(postMapping) && args(disposal)")
    public void updatePatientNhiStatusAtDisposal(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        PostMapping postMapping,
        Disposal disposal
    ) {
        if (disposal == null ||
            disposal.getId() == null
        ) {
            log.error("[PatientNhiStatusAspect] Empty disposal or disposal id at " + Trigger.DISPOSAL_POST.toString());
            return;
        }

        Optional<Patient> patientOpt = patientService.findPatientByDisposalId(disposal.getId());

        if (!patientOpt.isPresent()) {
            log.error("[PatientNhiStatusAspect] Can not found patient by disposal id " + disposal.getId() + " at " + Trigger.DISPOSAL_POST.toString());
            return;
        }

        updatePatientNhiStatus(
            Trigger.DISPOSAL_POST,
            patientOpt.get().getId()
        );
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(putMapping) && args(disposal)")
    public void updatePatientNhiStatusAtDisposal(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        PutMapping putMapping,
        Disposal disposal
    ) {
        if (disposal == null ||
            disposal.getId() == null
        ) {
            log.error("[PatientNhiStatusAspect] Empty disposal or disposal id at " + Trigger.DISPOSAL_PUT.toString());
            return;
        }

        Optional<Patient> patientOpt = patientService.findPatientByDisposalId(disposal.getId());

        if (!patientOpt.isPresent()) {
            log.error("[PatientNhiStatusAspect] Can not found patient by disposal id " + disposal.getId() + " at " + Trigger.DISPOSAL_PUT.toString());
            return;
        }

        updatePatientNhiStatus(
            Trigger.DISPOSAL_PUT,
            patientOpt.get().getId()
        );
    }

    private void updatePatientNhiStatus(Trigger trigger, Long pid) {
        try {
            Optional<Patient> patientOpt = patientService.findPatientById(pid);
            if (!patientOpt.isPresent()) {
                return;
            }

            Patient p = patientOpt.get();

            patientService.updatePatientNhiStatus(p);
            patientService.update(p);
        } catch (Exception e) {
            log.error("[PatientNhiStatusAspect] fail to update patient nhi status with pid: " + pid + " at " + trigger.toString());
        }
    }
}
