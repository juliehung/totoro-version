package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMedicalRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiMedicalRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiMedicalRecordRepository extends JpaRepository<NhiMedicalRecord, Long>, JpaSpecificationExecutor<NhiMedicalRecord> {

}
