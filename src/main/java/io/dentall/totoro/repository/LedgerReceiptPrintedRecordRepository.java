package io.dentall.totoro.repository;

import io.dentall.totoro.domain.LedgerReceipt;
import io.dentall.totoro.domain.LedgerReceiptPrintedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ledger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LedgerReceiptPrintedRecordRepository extends JpaRepository<LedgerReceiptPrintedRecord, Long> {

}
