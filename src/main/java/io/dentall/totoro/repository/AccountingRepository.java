package io.dentall.totoro.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.service.dto.table.AccountingTable;


/**
 * Spring Data  repository for the Accounting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountingRepository extends JpaRepository<Accounting, Long> {

    Optional<AccountingTable> findAccountingById(Long id);

    List<AccountingTable> findByRegistration_ArrivalTimeBetweenAndRegistration_Status(Instant start, Instant end, RegistrationStatus status);

    List<AccountingTable> findByRegistration_ArrivalTimeBetween(Instant start, Instant end);
}
