package io.dentall.totoro.aop;

import io.dentall.totoro.business.service.UpdatePatientNhiStatus;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.service.PatientService;
import org.apache.poi.poifs.filesystem.POIFSStream;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.Period;
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
        if (appointment != null &&
            appointment.getPatient() != null &&
            appointment.getPatient().getId() != null
        ) {
            updatePatientNhiStatus(
                Trigger.APPOINTMENT_POST,
                appointment.getPatient().getId()
            );
        }
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(putMapping) && args(nhiExtendPatients)")
    public void updatePatientNhiStatusAtNhiExtendPatient(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        PutMapping putMapping,
        NhiExtendPatient nhiExtendPatients
    ) {
        if (nhiExtendPatients != null &&
            nhiExtendPatients.getPatient() != null &&
            nhiExtendPatients.getPatient().getId() != null
        ) {
            updatePatientNhiStatus(
                Trigger.NHI_EXTEND_PATIENT_PUT,
                nhiExtendPatients.getPatient().getId()
            );
        }
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(postMapping) && args(disposal)")
    public void updatePatientNhiStatusAtDisposal(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        PostMapping postMapping,
        Disposal disposal
    ) {
        if (disposal != null &&
            disposal.getRegistration() != null &&
            disposal.getRegistration().getAppointment() != null &&
            disposal.getRegistration().getAppointment().getPatient() != null &&
            disposal.getRegistration().getAppointment().getPatient().getId() != null
        ) {
            updatePatientNhiStatus(
                Trigger.DISPOSAL_POST,
                disposal.getRegistration().getAppointment().getPatient().getId()
            );
        }
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(putMapping) && args(disposal)")
    public void updatePatientNhiStatusAtDisposal(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        PutMapping putMapping,
        Disposal disposal
    ) {
        if (disposal != null &&
            disposal.getRegistration() != null &&
            disposal.getRegistration().getAppointment() != null &&
            disposal.getRegistration().getAppointment().getPatient() != null &&
            disposal.getRegistration().getAppointment().getPatient().getId() != null
        ) {
            updatePatientNhiStatus(
                Trigger.DISPOSAL_PUT,
                disposal.getRegistration().getAppointment().getPatient().getId()
            );
        }
    }

    private void updatePatientNhiStatus(Trigger trigger, Long pid) {
        try {
            Optional<Patient> patientOpt = patientService.findPatientById(pid);
            if (!patientOpt.isPresent()) {
                return;
            }

            Patient p = patientOpt.get();

            Period age = Period.between(
                p.getBirth(),
                LocalDate.now()
            );


            if (age.getYears() <= 6) {
                patientService.updatePatientNhiStatus(
                    p,
                    patientService.calculateNhiStatus81(patientService.createNhiRuleCheckDto(pid, PatientService.NHI_STATUS_81)),
                    PatientService.NHI_STATUS_81
                );
            } else {
                p.setNhiStatus81("");
            }

            if (age.getYears() >= 12) {
                patientService.updatePatientNhiStatus(
                    p,
                    patientService.calculateNhiStatus91004C(patientService.createNhiRuleCheckDto(pid, PatientService.NHI_STATUS_91004C)),
                    PatientService.NHI_STATUS_91004C
                );
            } else {
                p.setNhiStatus91004C("");
            }

            patientService.update(p);
        } catch (Exception e) {
            log.error("[PatientNhiStatusAspect] fail to update patient nhi status with pid: " + pid + " at " + trigger.toString());
        }
    }
}
