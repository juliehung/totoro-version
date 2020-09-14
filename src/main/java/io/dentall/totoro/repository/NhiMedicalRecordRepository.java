package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the NhiMedicalRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiMedicalRecordRepository extends JpaRepository<NhiMedicalRecord, Long>, JpaSpecificationExecutor<NhiMedicalRecord> {

    List<NhiMedicalRecord> findByNhiExtendPatient_Patient_IdAndNhiCodeIn(Long id, List<String> codes);
}
