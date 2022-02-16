package io.dentall.totoro.aop;

import io.dentall.totoro.business.service.UpdatePatientNhiStatus;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.service.PatientService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @After("@annotation(updatePatientNhiStatus) && @annotation(requestMapping) && args(appointment)")
    public void updatePatientNhiStatusAtAppointment(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        RequestMapping requestMapping,
        Appointment appointment
    ) {
        if (requestMapping.method().length > 0 &&
            RequestMethod.POST.equals(requestMapping.method()[0])
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
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(requestMapping) && args(nhiExtendPatients)")
    public void updatePatientNhiStatusAtNhiExtendPatient(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        RequestMapping requestMapping,
        NhiExtendPatient nhiExtendPatients
    ) {
        if (requestMapping.method().length > 0 &&
            RequestMethod.PUT.equals(requestMapping.method()[0])
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
    }

    @After("@annotation(updatePatientNhiStatus) && @annotation(requestMapping) && args(disposal)")
    public void updatePatientNhiStatusAtDisposal(
        UpdatePatientNhiStatus updatePatientNhiStatus,
        RequestMapping requestMapping,
        Disposal disposal
    ) {
        if (requestMapping.method().length > 0 &&
            RequestMethod.POST.equals(requestMapping.method()[0]) ||
            RequestMethod.PUT.equals(requestMapping.method()[0])
        ) {
            if (disposal != null &&
                disposal.getRegistration() != null &&
                disposal.getRegistration().getAppointment() != null &&
                disposal.getRegistration().getAppointment().getPatient() != null &&
                disposal.getRegistration().getAppointment().getPatient().getId() != null
            ) {
                updatePatientNhiStatus(
                    RequestMethod.POST.equals(requestMapping.method()[0])
                        ? Trigger.DISPOSAL_POST
                        : Trigger.DISPOSAL_PUT,
                    disposal.getRegistration().getAppointment().getPatient().getId()
                );
            }
        }
    }

    private void updatePatientNhiStatus(Trigger trigger, Long pid) {
        try {
            Optional<Patient> patientOpt = patientService.findPatientById(pid);
            if (!patientOpt.isPresent()) {
                return;
            }

            Patient p = patientOpt.get();

            patientService.updatePatientNhiStatus(
                p,
                patientService.calculateNhiStatus81(patientService.createNhiRuleCheckDto(pid, PatientService.NHI_STATUS_81)),
                PatientService.NHI_STATUS_81
            );
            patientService.updatePatientNhiStatus(
                p,
                patientService.calculateNhiStatus91004C(patientService.createNhiRuleCheckDto(pid, PatientService.NHI_STATUS_91004C)),
                PatientService.NHI_STATUS_91004C
            );

            patientService.update(p);
        } catch (Exception e) {
            log.error("[PatientNhiStatusAspect] fail to update patient nhi status with pid: " + pid + " at " + trigger.toString());
        }
    }
}
