package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;


/**
 * Spring Data  repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByRegistrationIsNullAndExpectedArrivalTimeBetween(Instant start, Instant end, Pageable pageable);
}
