package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.dao.AppointmentDAO;

public class AppointmentSplitRelationshipDTO {

    private final Appointment appointment;

    public AppointmentSplitRelationshipDTO(AppointmentDAO appointmentDAO) {
        Disposal disposal = new Disposal();
        disposal.setId(appointmentDAO.getDisposalId());

        Registration registration = new Registration()
            .status(appointmentDAO.getRegistrationStatus())
            .arrivalTime(appointmentDAO.getArrivalTime())
            .type(appointmentDAO.getType())
            .onSite(appointmentDAO.getOnSite())
            .noCard(appointmentDAO.getNoCard());
        registration.setId(appointmentDAO.getRegistrationId());
        registration.setDisposal(disposal);

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
        appointment.setId(appointmentDAO.getId());
        appointment.setPatient(patient);
        appointment.setRegistration(registration);
        appointment.setDoctor(appointmentDAO.getDoctor());

        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

}
