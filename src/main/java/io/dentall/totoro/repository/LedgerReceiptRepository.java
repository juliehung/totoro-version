package io.dentall.totoro.repository;

import io.dentall.totoro.domain.LedgerReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Ledger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LedgerReceiptRepository extends JpaRepository<LedgerReceipt, Long> {
}
