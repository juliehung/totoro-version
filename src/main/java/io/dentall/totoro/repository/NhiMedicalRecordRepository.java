package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.service.dto.NhiMedicalRecordDTO;
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

    List<NhiMedicalRecord> findByNhiExtendPatient_Patient_IdAndNhiCodeOrderByDateDesc(Long id, String code);

    List<NhiMedicalRecord> findByNhiExtendPatient_Patient_IdAndNhiCodeInOrderByDateDesc(Long id, List<String> codes);

    List<NhiMedicalRecordDTO> findByNhiExtendPatient_Patient_IdAndDateLikeOrNhiExtendPatient_Patient_IdAndDateLikeOrNhiExtendPatient_Patient_IdAndDateLikeOrderByDateDesc(
        Long _1stPatientId,
        String _1stDateString,
        Long _2ndPatientId,
        String _2ndDateString,
        Long _3rdPatientId,
        String _3rdDateString
    );
}
