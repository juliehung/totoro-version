package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.repository.dao.AppointmentDAO;
import io.dentall.totoro.service.dto.AppointmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data  repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {

    List<Appointment> findByRegistrationIsNullAndExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(Instant start, Instant end);

    List<Appointment> findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(Instant start, Instant end);

    @Query(value =
        "select new io.dentall.totoro.service.dto.AppointmentDTO( " +
            "appointment.patient.id, " +
            "appointment.patient.name, " +
            "appointment.patient.birth, " +
            "appointment.patient.nationalId, " +
            "appointment.patient.gender, " +
            "appointment.patient.phone, " +
            "appointment.expectedArrivalTime, " +
            "appointment.doctor, " +
            "appointment.requiredTreatmentTime, " +
            "appointment.note, " +
            "appointment.microscope, " +
            "appointment.baseFloor, " +
            "appointment.status, " +
            "registration.arrivalTime, " +
            "appointment.id, " +
            "appointment.patient.newPatient, " +
            "registration.status, " +
            "appointment.patient.lastModifiedDate, " +
            "appointment.patient.lastModifiedBy " +
            ") " +
            "from Appointment as appointment left outer join appointment.registration as registration " +
            "where appointment.expectedArrivalTime between :beginDate and :endDate ")
    List<AppointmentDTO> findMonthAppointment(@Param("beginDate") Instant beginDate, @Param("endDate") Instant endDate);

    @Query(value =
        "select new io.dentall.totoro.repository.dao.AppointmentDAO( " +
            "appointment.id, " +
            "appointment.status, " +
            "appointment.subject, " +
            "appointment.note, " +
            "appointment.expectedArrivalTime, " +
            "appointment.requiredTreatmentTime, " +
            "appointment.microscope, " +
            "appointment.baseFloor, " +
            "appointment.colorId, " +
            "appointment.archived, " +
            "appointment.contacted, " +
            "patient.id, " +
            "patient.name, " +
            "patient.phone, " +
            "patient.gender, " +
            "patient.birth, " +
            "patient.nationalId, " +
            "patient.medicalId, " +
            "patient.address, " +
            "patient.email, " +
            "patient.blood, " +
            "patient.cardId, " +
            "patient.vip, " +
            "patient.emergencyName, " +
            "patient.emergencyPhone, " +
            "patient.emergencyAddress, " +
            "patient.emergencyRelationship, " +
            "patient.deleteDate, " +
            "patient.scaling, " +
            "patient.lineId, " +
            "patient.fbId, " +
            "patient.note, " +
            "patient.clinicNote, " +
            "patient.writeIcTime, " +
            "patient.mainNoticeChannel, " +
            "patient.career, " +
            "patient.marriage, " +
            "patient.newPatient, " +
            "registration.id, " +
            "registration.status, " +
            "registration.arrivalTime, " +
            "registration.type, " +
            "registration.onSite, " +
            "registration.noCard, " +
            "doctor" +
            ") " +
            "from Appointment as appointment " +
                "left join appointment.patient as patient " +
                "left join appointment.registration as registration " +
                "left join appointment.doctor as doctor " +
            "where appointment.expectedArrivalTime between :beginDate and :endDate ")
    List<AppointmentDAO> findAppointmentWithRelationshipBetween(@Param("beginDate") Instant beginDate, @Param("endDate") Instant endDate);
}
