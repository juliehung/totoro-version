package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiAccumulatedMedicalRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiAccumulatedMedicalRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiAccumulatedMedicalRecordRepository extends JpaRepository<NhiAccumulatedMedicalRecord, Long>, JpaSpecificationExecutor<NhiAccumulatedMedicalRecord> {

}
