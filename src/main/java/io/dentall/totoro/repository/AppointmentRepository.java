package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Appointment;
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
            "appointment.expectedArrivalTime, " +
            "appointment.requiredTreatmentTime, " +
            "appointment.patient.id, " +
            "appointment.patient.name, " +
            "appointment.doctor " +
            ") " +
        "from Appointment as appointment " +
        "where appointment.expectedArrivalTime between :beginDate and :endDate ")
    List<AppointmentDTO> findMonthAppointment(@Param("beginDate")Instant beginDate, @Param("endDate")Instant endDate);
}
