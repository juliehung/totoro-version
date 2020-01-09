package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.dao.AppointmentDAO;

public class AppointmentSplitRelationshipDTO {

    private final Appointment appointment;

    public AppointmentSplitRelationshipDTO(AppointmentDAO appointmentDAO) {

        Registration registration = null;
        if (appointmentDAO.getRegistrationId() != null) {
            registration = new Registration()
                .status(appointmentDAO.getRegistrationStatus())
                .arrivalTime(appointmentDAO.getArrivalTime())
                .type(appointmentDAO.getType())
                .onSite(appointmentDAO.getOnSite())
                .noCard(appointmentDAO.getNoCard());
            registration.setId(appointmentDAO.getRegistrationId());
            registration.setLastModifiedBy(appointmentDAO.getRegistrationLastModifiedBy());
            registration.setLastModifiedDate(appointmentDAO.getRegistrationLastModifiedDate());

            Disposal disposal = null;
            if (appointmentDAO.getDisposalId() != null) {
                disposal = new Disposal();
                disposal.setId(appointmentDAO.getDisposalId());
                disposal.setCreatedDate(appointmentDAO.getDisposalCreatedDate());
                disposal.setLastModifiedDate(appointmentDAO.getDisposalLastModifiedDate());
            }
            registration.setDisposal(disposal);

            if (appointmentDAO.getAccounting() != null) {
                registration.setAccounting(appointmentDAO.getAccounting());
            }

        }
        NhiExtendPatient nhiExtendPatient = new NhiExtendPatient()
            .cardNumber(appointmentDAO.getNhiPatientCardNumber())
            .cardAnnotation(appointmentDAO.getNhiPatientCardAnnotation())
            .cardValidDate(appointmentDAO.getNhiPatientCardValidDate())
            .cardIssueDate(appointmentDAO.getNhiPatientCardIssueDate())
            .nhiIdentity(appointmentDAO.getNhiPatientNhiIdentity())
            .availableTimes(appointmentDAO.getNhiPatientAvailableTimes())
            .scaling(appointmentDAO.getNhiPatientScaling())
            .fluoride(appointmentDAO.getNhiPatientFluoride())
            .perio(appointmentDAO.getNhiPatientPerio());
        nhiExtendPatient.setCreatedBy(appointmentDAO.getNhiPatientCreatedBy());
        nhiExtendPatient.setCreatedDate(appointmentDAO.getNhiPatientCreatedDate());
        nhiExtendPatient.setLastModifiedBy(appointmentDAO.getNhiPatientLastModifiedBy());
        nhiExtendPatient.setLastModifiedDate(appointmentDAO.getNhiPatientLastModifiedDate());

        Patient patient = new Patient()
            .name(appointmentDAO.getName())
            .phone(appointmentDAO.getPhone())
            .gender(appointmentDAO.getGender())
            .birth(appointmentDAO.getBirth())
            .nationalId(appointmentDAO.getNationalId())
            .medicalId(appointmentDAO.getMedicalId())
            .address(appointmentDAO.getAddress())
            .email(appointmentDAO.getEmail())
            .blood(appointmentDAO.getBlood())
            .cardId(appointmentDAO.getCardId())
            .vip(appointmentDAO.getVip())
            .emergencyName(appointmentDAO.getEmergencyName())
            .emergencyPhone(appointmentDAO.getEmergencyPhone())
            .emergencyAddress(appointmentDAO.getEmergencyAddress())
            .emergencyRelationship(appointmentDAO.getEmergencyRelationship())
            .deleteDate(appointmentDAO.getDeleteDate())
            .scaling(appointmentDAO.getScaling())
            .lineId(appointmentDAO.getLineId())
            .fbId(appointmentDAO.getFbId())
            .note(appointmentDAO.getPatientNote())
            .clinicNote(appointmentDAO.getClinicNote())
            .writeIcTime(appointmentDAO.getWriteIcTime())
            .mainNoticeChannel(appointmentDAO.getMainNoticeChannel())
            .career(appointmentDAO.getCareer())
            .marriage(appointmentDAO.getMarriage())
            .newPatient(appointmentDAO.getNewPatient());
        patient.setId(appointmentDAO.getPatientId());
        patient.setLastModifiedBy(appointmentDAO.getPatientLastModifiedBy());
        patient.setLastModifiedDate(appointmentDAO.getPatientLastModifiedDate());
        patient.setNhiExtendPatient(nhiExtendPatient);

        Appointment appointment = new Appointment()
            .status(appointmentDAO.getStatus())
            .subject(appointmentDAO.getSubject())
            .note(appointmentDAO.getNote())
            .expectedArrivalTime(appointmentDAO.getExpectedArrivalTime())
            .requiredTreatmentTime(appointmentDAO.getRequiredTreatmentTime())
            .microscope(appointmentDAO.getMicroscope())
            .baseFloor(appointmentDAO.getBaseFloor())
            .colorId(appointmentDAO.getColorId())
            .archived(appointmentDAO.getArchived())
            .contacted(appointmentDAO.getContacted());
        appointment.setCreatedBy(appointmentDAO.getCreatedBy());
        appointment.setCreatedDate(appointmentDAO.getCreatedDate());
        appointment.setId(appointmentDAO.getId());

        appointment.setPatient(patient);
        appointment.setRegistration(registration);

        ExtendUser doctor = appointmentDAO.getDoctor();
        if (doctor != null) {
            doctor.setAvatar(null);
        }
        appointment.setDoctor(doctor);

        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

}
