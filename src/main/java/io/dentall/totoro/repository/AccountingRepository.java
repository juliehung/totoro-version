package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.service.dto.table.AccountingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Accounting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountingRepository extends JpaRepository<Accounting, Long> {

    Optional<AccountingTable> findAccountingById(Long id);

}
