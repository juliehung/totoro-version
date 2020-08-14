package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.dto.HistoricalNhiTxDispInfoDTO;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the NhiExtendTreatmentProcedureRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendTreatmentProcedureRepository extends JpaRepository<NhiExtendTreatmentProcedure, Long> {

    // 查詢這個 `病患(?1)` ，除了當下這個 `健保治療(?2)` 外，這項治療的 `發生時間(?3)` 之前的所有健保治療項目
    @Query(
        nativeQuery = true,
        value =
            "select netp.*, " +
            "ned.a17, " +
            "ned.a54 " +
            "from disposal d " +
            "left join nhi_extend_disposal ned on ned.disposal_id = d.id " +
            "left join registration r on d.registration_id = r.id " +
            "left join appointment a on r.id = a.registration_id " +
            "left join treatment_procedure tp on d.id = tp.disposal_id " +
            "left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "where a.patient_id = ?1 and ned.a19 <> '2' and ned.jhi_date <= ?2 " +
            "or a.patient_id = ?1 and ned.a19 = '2' and ned.replenishment_date <= ?2 "
    )
    List<HistoricalNhiTxDispInfoDTO> findHistoricalNhiTxByPatientIdAndExcludeTargetNhiTxId(Long patientId, Instant date);

    Set<NhiExtendTreatmentProcedure> findNhiExtendTreatmentProcedureByTreatmentProcedure_Disposal_Id(@Param(value = "disposalId") Long disposalId);

    Optional<NhiExtendTreatmentProcedureTable> findNhiExtendTreatmentProcedureByTreatmentProcedure_Id(Long id);

    <T> Optional<T> findById(Long id, Class<T> type);
}
