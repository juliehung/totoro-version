package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiIcCardOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Calendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiIcCardOperationRecordRepository extends JpaRepository<NhiIcCardOperationRecord, Long> {
    List<NhiIcCardOperationRecord> findByEjectIcCardRecordFalseAndDisposalIdAndNhiResponseCodeIs(Long disposalId, String responseCode);
}
