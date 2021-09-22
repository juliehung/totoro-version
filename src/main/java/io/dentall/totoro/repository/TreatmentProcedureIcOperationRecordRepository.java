package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentProcedureIcOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Calendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentProcedureIcOperationRecordRepository extends JpaRepository<TreatmentProcedureIcOperationRecord, Long> {

}
