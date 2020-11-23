package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.service.dto.table.RegistrationTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;


/**
 * Spring Data  repository for the Registration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Page<Registration> findByAccounting_TransactionTimeBetweenOrderByAccounting_TransactionTime(Instant start, Instant end, Pageable page);

    Page<Registration> findByArrivalTimeBetweenOrderByArrivalTimeAsc(Instant start, Instant end, Pageable page);

    Optional<RegistrationTable> findRegistrationByDisposal_Id(Long id);

    <T> Optional<T> findById(Long id, Class<T> type);
}
