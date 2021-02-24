package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.dto.HistoricalNhiTxDispInfoDTO;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the NhiExtendTreatmentProcedureRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendTreatmentProcedureRepository extends JpaRepository<NhiExtendTreatmentProcedure, Long> {

    List<NhiExtendTreatmentProcedureTable> findByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73AndTreatmentProcedure_Disposal_IdNotAndTreatmentProcedure_IdNotInOrderByA71Desc(
        Long patientId,
        String a73,
        Long disposalId,
        List<Long> excludeTreatmentProcedureIds);

    List<NhiExtendTreatmentProcedureTable> findByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73AndTreatmentProcedure_IdNotInOrderByA71Desc(
        Long patientId,
        String a73,
        List<Long> excludeTreatmentProcedureIds);

    // 查詢這個 `病患(?1)` ，除了當下這個 `健保治療(?2)` 外，這項治療的 `發生時間(?3)` 之前的所有健保治療項目
    @Query(
        nativeQuery = true,
        value =
            "select netp.*, " +
            "tp.id as TreatmentProcedure_Id, " +
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

    <T> Optional<T> findByIdAndA73AndTreatmentProcedure_Disposal_Registration_Appointment_Patient_Id(Long treatmentProcedureId, String a73, Long patientId, Class<T> type);

    <T> Optional<T> findById(Long id, Class<T> type);

    Optional<NhiExtendTreatmentProcedureTable> findTop1ByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndTreatmentProcedure_Disposal_DateTimeBetweenAndTreatmentProcedure_Disposal_IdNotInOrderByTreatmentProcedure_Disposal_DateTimeDesc(
        Long patientId,
        Instant begin,
        Instant end,
        Long excludeDisposalId
    );

    // 查詢所有 nhi extend procedure，且包含輸入之健保代碼，且在指定病患下
    List<NhiExtendTreatmentProcedureTable> findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73InOrderByA71Desc(Long patientId, List<String> a73s);

}
