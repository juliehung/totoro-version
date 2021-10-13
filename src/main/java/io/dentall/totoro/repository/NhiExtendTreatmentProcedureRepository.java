package io.dentall.totoro.repository;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckMonthDeclarationTxDTO;
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

    @Query(
        nativeQuery = true,
        value = "select d.id as disposalId, " +
            "       a.patient_id as patientId, " +
            "       p.name as patientName, " +
            "       a.doctor_user_id as doctorId, " +
            "       ju.first_name as doctorName, " +
            "       case when ned.a19 = '1' " +
            "           then ned.a17 " +
            "           else ned.a54 " +
            "       end as disposalTime, " +
            "       d.date_time as displayDisposalTime, " +
            "       ned.a23 as nhiCategory, " +
            "       tp.id as treatmentProcedureId, " +
            "       netp.a73 as nhiCode, " +
            "       netp.a74 as teeth, " +
            "       netp.a75 as surface ," +
            "       np.name as nhiTxName " +
            "from disposal d " +
            "left join appointment a on d.registration_id = a.registration_id " +
            "left join patient p on p.id = a.patient_id " +
            "left join jhi_user ju on ju.id = a.doctor_user_id " +
            "left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "left join treatment_procedure tp on d.id = tp.disposal_id " +
            "left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "left join nhi_procedure np on np.id = tp.nhi_procedure_id " +
            "where d.date_time between :begin and :end " +
            "and ned.id is not null " +
            "and netp.treatment_procedure_id is not null " +
            "and d.id not in (:excludeDisposals) " +
            "order by d.date_time, doctorId, patientId" +
            ";"
    )
    List<NhiRuleCheckMonthDeclarationTxDTO> findNhiMonthDeclarationTx(
        @Param(value = "begin") Instant begin,
        @Param(value = "end") Instant end,
        @Param(value = "excludeDisposals") List<Long> excludeDisposals
    );

    Optional<NhiExtendTreatmentProcedure> findByTreatmentProcedure_Id(Long treatmentProcedureId);
}
