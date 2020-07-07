package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.service.dto.table.AppointmentTable;
import io.dentall.totoro.service.util.MapperUtil;

public class AppointmentMapper {

    public static Appointment appointmentTableToAppointment(AppointmentTable appointmentTable) {
        Appointment appointment = new Appointment();

        appointment.setId(appointmentTable.getId());
        appointment.setStatus(appointmentTable.getStatus());
        appointment.setSubject(appointmentTable.getSubject());
        appointment.setNote(appointmentTable.getNote());
        appointment.setExpectedArrivalTime(appointmentTable.getExpectedArrivalTime());
        appointment.setRequiredTreatmentTime(appointmentTable.getRequiredTreatmentTime());
        appointment.setMicroscope(appointmentTable.getMicroscope());
        appointment.setBaseFloor(appointmentTable.getBaseFloor());
        appointment.setColorId(appointmentTable.getColorId());
        appointment.setArchived(appointmentTable.getArchived());
        appointment.setContacted(appointmentTable.getContacted());

        // Relationship
        if (appointmentTable.getPatient_Id() != null) {
            Patient patient = new Patient();
            patient.setId(appointmentTable.getPatient_Id());
            MapperUtil.setNullAuditing(patient);

            appointment.setPatient(patient);
        }

        // 依照我們使用 mapper for table and domain 這應該要被實作，但由於前端已無 appointment.registration 這種架構，到時候整理 api 介面實在統一整理
        if (appointmentTable.getRegistration_Id() != null) {
            Registration registration = new Registration();
            registration.setId(appointmentTable.getRegistration_Id());
            MapperUtil.setNullAuditing(registration);

            appointment.setRegistration(registration);
        }

        if (appointmentTable.getDoctorUser_Id() != null) {
            ExtendUser extendUser = new ExtendUser();
            extendUser.setId(appointmentTable.getDoctorUser_Id());

            appointment.setDoctor(extendUser);
        }

        MapperUtil.setAuditing(appointment, appointmentTable);

        return appointment;
    }
}
