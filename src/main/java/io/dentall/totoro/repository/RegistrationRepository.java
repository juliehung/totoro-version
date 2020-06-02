package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.service.dto.table.RegistrationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Registration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByArrivalTimeBetweenOrderByArrivalTimeAsc(Instant start, Instant end);

    Optional<RegistrationTable> findRegistrationByDisposal_Id(Long id);
}
