package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;


/**
 * Spring Data  repository for the Registration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Page<Registration> findByArrivalTimeBetween(ZonedDateTime start, ZonedDateTime end, Pageable pageable);
}
