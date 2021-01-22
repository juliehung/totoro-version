package io.dentall.totoro.repository;

import io.dentall.totoro.business.repository.RemappingDomainToTableDtoRepository;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.repository.dao.MonthDisposalDAO;
import io.dentall.totoro.service.dto.CalculateBaseData;
import io.dentall.totoro.service.dto.NhiExtendTreatmentProcedureDTO;
import io.dentall.totoro.service.dto.NhiIndexEndoDTO;
import io.dentall.totoro.service.dto.StatisticSpDTO;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.web.rest.vm.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the NhiExtendDisposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendDisposalRepository extends RemappingDomainToTableDtoRepository, JpaRepository<NhiExtendDisposal, Long>, JpaSpecificationExecutor<NhiExtendDisposal> {

    @Query(
        nativeQuery = true,
        value =
            "select " +
            "    ned.disposal_id as disposalId, a17, a18, a19, a23, a31, a32, a54," +
            "    patient_identity as patientIdentity, serial_number as serialNumber, examination_code as examinationCode, examination_point as examinationPoint, " +
            "    netp.treatment_procedure_id as treatmentProcedureId, a71, a72, a73, a74, a75, a76, a77, a78, " +
            "    p.id as pid, p.name as pname, ju.id as did, ju.first_name as dname " +
            "from disposal d " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join registration r on d.registration_id = r.id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on a.patient_id = p.id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "         left join jhi_user ju on ju.id = a.doctor_user_id " +
            "where a19 <> '2' and date_time between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3) " +
            "   or a19 = '2' and replenishment_date between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3);"
    )
    List<NhiIndexTreatmentProcedureVM> findNhiIndexTreatmentProcedures(Instant begin, Instant end, List<Long> excludeDisposalId);

    @Query(
        nativeQuery = true,
        value = "with   " +
            "     nhi_tx_base as ( " +
            "        select " +
            "            ned.*, " +
            "            netp.*, " +
            "            p.id as pid, " +
            "            p.name as pname, " +
            "            ju.id as did, " +
            "            ju.first_name as dname " +
            "        from disposal d " +
            "        left join treatment_procedure tp on d.id = tp.disposal_id " +
            "        left join registration r on d.registration_id = r.id " +
            "        left join appointment a on r.id = a.registration_id " +
            "        left join patient p on a.patient_id = p.id " +
            "        left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "        left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "        left join jhi_user ju on ju.id = a.doctor_user_id " +
            "                    where a19 <> '2' and date_time between ?1 and ?2  and a18 is not null and trim(a18) <> '' and d.id not in (?3)" +
            "                       or a19 = '2' and replenishment_date between ?1 and ?2  and a18 is not null and trim(a18) <> '' and d.id not in (?3)" +
            "    ), " +
            "    tooth_clean_total_time as ( " +
            "        select did, count(*) as total_times " +
            "        from nhi_tx_base " +
            "        where a73 in ('91004C','91017C','91018C','91104C','91005C') " +
            "        group by did" +
            "    ), " +
            "    tooth_clean_distinct_pat as ( " +
            "        select did, count(*) as total_pat " +
            "        from ( " +
            "            select did, patient_id " +
            "            from nhi_tx_base " +
            "            where a73 in ('91004C','91017C','91018C','91104C','91005C') " +
            "            group by did, patient_id " +
            "        ) as tx_pat " +
            "        group by did " +
            "    ) " +
            "    select tctt.did as did, " +
            "        total_times as totalTime, " +
            "        total_pat as totalPat, " +
            "        case " +
            "            when total_pat > 0 " +
            "            then cast(total_times as float8) / cast(total_pat as float8) " +
            "        end as timePatRate " +
            "    from tooth_clean_total_time tctt " +
            "    left join tooth_clean_distinct_pat tcdp on tctt.did = tcdp.did " +
            "    order by did "
    )
    List<NhiIndexToothCleanVM> calculateToothCleanIndex(Instant begin, Instant end, List<Long> excludeDisposalId);

    @Query(
        nativeQuery = true,
        value = "with " +
            "     nhi_tx_base as ( " +
            "        select " +
            "            ned.*, " +
            "            netp.*, " +
            "            p.id as pid, " +
            "            p.name as pname, " +
            "            ju.id as did, " +
            "            ju.first_name as dname " +
            "        from disposal d " +
            "        left join treatment_procedure tp on d.id = tp.disposal_id " +
            "        left join registration r on d.registration_id = r.id " +
            "        left join appointment a on r.id = a.registration_id " +
            "        left join patient p on a.patient_id = p.id " +
            "        left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "        left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "        left join jhi_user ju on ju.id = a.doctor_user_id " +
            "                     where a19 <> '2' and date_time between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3) " +
            "                        or a19 = '2' and replenishment_date between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3)), " +
            "    od_total_pat as ( " +
            "        select did, count(*) as total_pat " +
            "        from ( " +
            "                 select did, pid" +
            "                 from nhi_tx_base ntb " +
            "                 where a73 like '89%C' " +
            "             ) as tmp_total_pat " +
            "        group by did" +
            "    ), " +
            "    od_distinct_total_pat as ( " +
            "        select did, count(*) as distinct_total_pat " +
            "        from ( " +
            "                 select distinct did, pid " +
            "                 from nhi_tx_base ntb " +
            "                 where a73 like '89%C' " +
            "                 group by did, pid " +
            "             ) as tmp_total_pat " +
            "        group by did " +
            "    ), " +
            "    od_total_tooth as ( " +
            "        select did, sum(length(a74)/2) as total_tooth " +
            "        from nhi_tx_base " +
            "        where a73 like '89%C' " +
            "        group by did " +
            "    ), " +
            "    od_total_surface as ( " +
            "        select did, sum(surface_count) as total_surface " +
            "        from ( " +
            "                 select did, count(*) as surface_count " +
            "                 from nhi_tx_base " +
            "                 where a73 in ('89001C', '89004C', '89008C', '89011C', '89013C') " +
            "                 group by did " +
            "                 union " +
            "                 select did, count(*) * 2 as surface_count " +
            "                 from nhi_tx_base " +
            "                 where a73 in ('89002C', '89005C', '89009C') " +
            "                 group by did " +
            "                 union " +
            "                 select did, count(*) * 3 as surface_count " +
            "                 from nhi_tx_base " +
            "                 where a73 in ('89003C', '89010C', '89012C') " +
            "                 group by did " +
            "                 union " +
            "                 select did, count(*) * 4 as surface_count " +
            "                 from nhi_tx_base " +
            "                 where a73 in ('89014C', '89015C') " +
            "                 group by did " +
            "             ) as tmp_surface " +
            "        group by did " +
            "        order by did " +
            "    ) " +
            "select od_total_pat.did as did,  " +
            "       total_pat as totalPat,  " +
            "       distinct_total_pat as distinctTotalPat,  " +
            "       total_tooth as totalTooth,  " +
            "       total_surface as totalSurface,  " +
            "       case  " +
            "           when total_tooth > 0 then total_surface/total_tooth  " +
            "           else 0  " +
            "       end as surfaceToothRate,  " +
            "       case  " +
            "           when cast(total_pat as float8) > 0 then total_tooth/cast(total_pat as float8)  " +
            "           else 0  " +
            "       end as toothPeopleRate,  " +
            "       case  " +
            "           when total_pat > 0 then total_surface/total_pat  " +
            "           else 0  " +
            "       end as surfacePeopleRate " +
            "from od_total_pat " +
            "left join od_distinct_total_pat on od_total_pat.did = od_distinct_total_pat.did " +
            "left join od_total_tooth on od_total_pat.did = od_total_tooth.did " +
            "left join od_total_surface on od_total_pat.did = od_total_surface.did " +
            "order by od_total_pat.did "
    )
    List<NhiIndexOdVM> calculateOdIndex(Instant begin, Instant end, List<Long> excludeDisposalId);

    @Query(
        nativeQuery = true,
        value = "with nhi_tx_base as (select a.*, ned.*, netp.*, p.id as pid, p.name as pname, ju.id as did, ju.first_name as dname " +
            "                     from disposal d " +
            "                              left join treatment_procedure tp on d.id = tp.disposal_id " +
            "                              left join registration r on d.registration_id = r.id " +
            "                              left join appointment a on r.id = a.registration_id " +
            "                              left join patient p on a.patient_id = p.id " +
            "                              left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "                              left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "                              left join jhi_user ju on ju.id = a.doctor_user_id " +
            "                     where a19 <> '2' and date_time between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3) " +
            "                        or a19 = '2' and replenishment_date between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3)), " +
            "     nhi_doc_exam as ( " +
            "         select did as did, " +
            "                examination_code, " +
            "                examination_point, " +
            "                sum(examination_point), " +
            "                serial_number " +
            "         from nhi_tx_base " +
            "         where examination_code is not null " +
            "           and trim(examination_code) <> '' " +
            "         group by did, disposal_id, examination_code, examination_point, serial_number " +
            "     ) " +
            "select did                    as did, " +
            "       examination_code       as nhiExamCode, " +
            "       examination_point      as nhiExamPoint, " +
            "       count(*)               as totalNumber, " +
            "       sum(examination_point) as totalPoint, " +
            "       serial_number as serialNumber " +
            "from nhi_doc_exam " +
            "group by did, examination_code, examination_point, serial_number " +
            "order by did, examination_code;"
    )
    List<NhiDoctorExamVM> calculateDoctorNhiExam(Instant begin, Instant end, List<Long> excludeDisposalId);

    @Query(
        nativeQuery = true,
        value = "with nhi_tx_base as (select a.*, ned.*, netp.*, p.id as pid, p.name as pname, ju.id as did, ju.first_name as dname " +
            "                     from disposal d " +
            "                              left join treatment_procedure tp on d.id = tp.disposal_id " +
            "                              left join registration r on d.registration_id = r.id " +
            "                              left join appointment a on r.id = a.registration_id " +
            "                              left join patient p on a.patient_id = p.id " +
            "                              left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "                              left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "                              left join jhi_user ju on ju.id = a.doctor_user_id " +
            "                     where a19 <> '2' and date_time between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3) " +
            "                        or a19 = '2' and replenishment_date between ?1 and ?2 and a18 is not null and trim(a18) <> '' and d.id not in (?3)), " +
            "     nhi_doctor_tx as ( " +
            "        select did, serial_number, a73, np.name as nhiTxName, np.point as nhiTxPoint, count(*) as totalCount, count(*) * np.point as totalPoint " +
            "        from nhi_tx_base " +
            "        left join nhi_procedure np on a73 = code " +
            "        group by did, treatment_procedure_id, a73, np.name, np.point, serial_number " +
            "        order by did, a73 " +
            "     ) " +
            "select did, " +
            "       a73 as nhiTxCode, " +
            "       nhiTxName, " +
            "       nhiTxPoint, " +
            "       count(*) as totalNumber, " +
            "       count(*) * nhiTxPoint as totalPoint, " +
            "       serial_number as serialNumber " +
            "from nhi_doctor_tx group by did, a73, nhiTxName, nhiTxPoint, serial_number order by did, a73"
    )
    List<NhiDoctorTxVM> calculateDoctorNhiTx(Instant begin, Instant end, List<Long> excludeDisposalId);

    @Query(
        "select new io.dentall.totoro.service.dto.StatisticSpDTO(d.createdBy, np.specificCode, np.point) " +
        "from NhiExtendDisposal nhiExtendDisposal " +
        "left join Disposal d on nhiExtendDisposal.disposal.id = d.id " +
        "left join TreatmentProcedure t on d.id = t.disposal.id " +
        "left join NhiExtendTreatmentProcedure nt on t.id = nt.treatmentProcedure.id " +
        "left join NhiProcedure np on t.nhiProcedure.id = np.id " +
        "where np.id is not null and nhiExtendDisposal.date between :start and :end and nhiExtendDisposal.replenishmentDate is null " +
        "or np.id is not null and nhiExtendDisposal.a19 = '2' and nhiExtendDisposal.replenishmentDate between :start and :end"
    )
    List<StatisticSpDTO> findSp(@Param("start") LocalDate start, @Param("end") LocalDate end);

    List<NhiExtendDisposalTable> findNhiExtendDisposalByDateBetweenAndReplenishmentDateIsNullOrReplenishmentDateBetweenAndA19Equals(
        LocalDate start,
        LocalDate end,
        LocalDate rstart,
        LocalDate rend,
        String a19);

    Page<NhiExtendDisposalTable> findNhiExtendDisposalByDateBetweenAndReplenishmentDateIsNullOrReplenishmentDateBetweenAndA19Equals(
        LocalDate start,
        LocalDate end,
        LocalDate rstart,
        LocalDate rend,
        String a19,
        Pageable pageable);

    Set<NhiExtendDisposalTable> findNhiExtendDisposalByDate(LocalDate date);

    // 實際上我們系統中，nhi_ext_disp - disp 的關係是 many-to-one，實際上系統確實也有這種類型的資料，因此 當有多個時 便不適用於 Optional 這種情境（因為這帶有 0 | 1）多個則不合法
    // 會出 exception。使用這個 repository 的邏輯便會是 取得 List by disposal_id 且 order by Id，取最後一筆資料
    List<NhiExtendDisposalTable> findNhiExtendDisposalByDisposal_IdOrderById(Long id);

    <T> Collection<T> findByDisposal_IdOrderByIdDesc(Long id, Class<T> type);

    String dateBetween = "((nhiExtendDisposal.date between :start and :end and nhiExtendDisposal.replenishmentDate is null) or " +
        "(nhiExtendDisposal.a19 = '2' and nhiExtendDisposal.replenishmentDate between :start and :end)) ";

    String dateGte = "((nhiExtendDisposal.date >= :gte and nhiExtendDisposal.replenishmentDate is null) or " +
        "(nhiExtendDisposal.a19 = '2' and nhiExtendDisposal.replenishmentDate >= :gte)) ";

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where nhiExtendDisposal.date = :date"
    )
    List<NhiExtendDisposal> findByDate(@Param("date") LocalDate date);

    @Query("select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " + dateBetween)
    List<NhiExtendDisposal> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " +
            "nhiExtendDisposal.patientId = :patientId and " + dateBetween
    )
    List<NhiExtendDisposal> findByDateBetweenAndPatientId(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("patientId") Long patientId);

    /**
     *
     * 因為NhiExtendDisposalRepository.findByDateBetweenAndPatientId在某些patient會有效能上的問題，所以特地另寫這個方法來直接取得資料
     *
     * @param start
     * @param end
     * @param patientId
     * @return
     */
    @Query(
            "select new io.dentall.totoro.service.dto.NhiExtendTreatmentProcedureDTO( " +
            "nhiExtendTreatmentProcedure.id," +
            "nhiExtendTreatmentProcedure.a71," +
            "nhiExtendTreatmentProcedure.a72," +
            "nhiExtendTreatmentProcedure.a73," +
            "nhiExtendTreatmentProcedure.a74," +
            "nhiExtendTreatmentProcedure.a75," +
            "nhiExtendTreatmentProcedure.a76," +
            "nhiExtendTreatmentProcedure.a77," +
            "nhiExtendTreatmentProcedure.a78," +
            "nhiExtendTreatmentProcedure.a79," +
            "nhiExtendTreatmentProcedure.check," +
            "nhiExtendDisposal.a14," +
            "nhiExtendDisposal.date," +
            "nhiExtendDisposal.replenishmentDate" +
            ")" +
            "  from NhiExtendDisposal nhiExtendDisposal " +
            "  left join NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure on nhiExtendDisposal.id = nhiExtendTreatmentProcedure.nhiExtendDisposal.id " +
            " where " +
            "nhiExtendDisposal.patientId = :patientId and " + dateBetween
        )
    List<NhiExtendTreatmentProcedureDTO> findByDateBetweenAndPatientId2(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("patientId") Long patientId);

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " +
            "nhiExtendDisposal.patientId = :patientId and " + dateGte
    )
    List<NhiExtendDisposal> findByDateGreaterThanEqualAndPatientId(@Param("gte") LocalDate gte, @Param("patientId") Long patientId);

    List<NhiExtendDisposal> findByPatientId(Long patientId);

    @Query("select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " + dateBetween + "order by patientId, a18")
    Page<NhiExtendDisposal> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end, Pageable pageable);

    @Query("select new io.dentall.totoro.repository.dao.MonthDisposalDAO(disposal.id, nhiExtendDisposal.id, " +
        "nhiExtendDisposal.a11, nhiExtendDisposal.a12, nhiExtendDisposal.a13, " +
        "nhiExtendDisposal.a14, nhiExtendDisposal.a15, nhiExtendDisposal.a16, " +
        "nhiExtendDisposal.a17, nhiExtendDisposal.a18, nhiExtendDisposal.a19, " +
        "nhiExtendDisposal.a22, nhiExtendDisposal.a23, nhiExtendDisposal.a25, " +
        "nhiExtendDisposal.a26, nhiExtendDisposal.a27, nhiExtendDisposal.a31, " +
        "nhiExtendDisposal.a32, nhiExtendDisposal.a41, nhiExtendDisposal.a42, " +
        "nhiExtendDisposal.a43, nhiExtendDisposal.a44, nhiExtendDisposal.a54, " +
        "nhiExtendDisposal.date, nhiExtendDisposal.uploadStatus, " +
        "nhiExtendDisposal.examinationCode, nhiExtendDisposal.examinationPoint, " +
        "nhiExtendDisposal.patientIdentity, nhiExtendDisposal.serialNumber, " +
        "nhiExtendDisposal.patientId, nhiExtendDisposal.category, " +
        "nhiExtendDisposal.replenishmentDate, " +
        "nhiExtendDisposal.checkedMonthDeclaration, " +
        "nhiExtendDisposal.checkedAuditing," +
        "patient.name, " +
        "patient.vipPatient ) " +
        "from " +
        "   NhiExtendDisposal as nhiExtendDisposal left outer join nhiExtendDisposal.disposal as disposal," +
        "   Patient as patient " +
        "where 1 = 1 " +
        "and nhiExtendDisposal.patientId = patient.id " +
        "and " + dateBetween)
    List<MonthDisposalDAO> findDisposalIdAndNhiExtendDisposalPrimByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select count(nhiExtendDisposal) from NhiExtendDisposal nhiExtendDisposal where " + dateBetween)
    long countByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    <T> List<T> findByDisposal_TreatmentProcedures_Id(Long treatmentId, Class<T> clazz);

    @Query(
        nativeQuery = true,
        value = "select " +
            "d.id as disposalId, " +
            "d.date_time as disposalDate, " +
            "ned.examination_code as examinationCode, " +
            "ned.examination_point as examinationPoint, " +
            "ned.patient_identity as patientIdentity, " +
            "ned.serial_number as serialNumber, " +
            "ned.a31 as visitTotalPoint, " +
            "ned.a32 as copayment, " +
            "np.code as txCode, " +
            "tp.total as txPoint, " +
            "np.specific_code as specificCode, " +
            "a.patient_id as patientId, " +
            "eu.user_id as doctorId " +
            "from disposal d " +
            "    left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "    left join treatment_procedure tp on d.id = tp.disposal_id " +
            "    left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "    left join nhi_procedure np on tp.nhi_procedure_id = np.id " +
            "    left join appointment a on d.registration_id = a.registration_id " +
            "    left join extend_user eu on ned.a15 = eu.national_id " +
            "    left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "   and d.id not in :excludeDisposalId and trim(ned.serial_number) <> '' and trim(ned.a18) <> '' and tp.nhi_procedure_id is not null " +
            "or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "   and d.id not in :excludeDisposalId and trim(ned.serial_number) <> '' and trim(ned.a18) <> '' and tp.nhi_procedure_id is not null " +
            "order by d.id, tp.id "
    )
    List<CalculateBaseData> findCalculateBaseDataByDate(
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end,
            @Param("excludeDisposalId") List<Long> excludeDisposalId
    );

    @Query(
        nativeQuery = true,
        value = "select " +
            "d.id as disposalId, " +
            "d.date_time as disposalDate, " +
            "ned.examination_code as examinationCode, " +
            "ned.examination_point as examinationPoint, " +
            "ned.patient_identity as patientIdentity, " +
            "ned.serial_number as serialNumber, " +
            "ned.a31 as visitTotalPoint, " +
            "ned.a32 as copayment, " +
            "np.code as txCode, " +
            "tp.total as txPoint, " +
            "np.specific_code as specificCode, " +
            "a.patient_id as patientId, " +
            "eu.user_id as doctorId, " +
            "p.name as patientName, " +
            "p.vip_patient as vipPatient " +
            "from disposal d " +
            "    left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "    left join treatment_procedure tp on d.id = tp.disposal_id " +
            "    left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "    left join nhi_procedure np on tp.nhi_procedure_id = np.id " +
            "    left join appointment a on d.registration_id = a.registration_id " +
            "    left join patient p on a.patient_id = p.id " +
            "    left join extend_user eu on ned.a15 = eu.national_id " +
            "    left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end and a.doctor_user_id = :doctorId " +
            "   and d.id not in :excludeDisposalId and trim(ned.serial_number) <> '' and trim(ned.a18) <> '' and tp.nhi_procedure_id is not null " +
            "or ned.a19 = '2' and ned.replenishment_date between :begin and :end and a.doctor_user_id = :doctorId " +
            "   and d.id not in :excludeDisposalId and trim(ned.serial_number) <> '' and trim(ned.a18) <> '' and tp.nhi_procedure_id is not null " +
            "order by d.id, tp.id "
    )
    List<CalculateBaseData> findCalculateBaseDataByDateAndDoctorId(
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end,
            @Param("doctorId") Long doctorId,
            @Param("excludeDisposalId") List<Long> excludeDisposalId
    );

    @Query(nativeQuery = true,
            value = "select a.doctor_user_id did, netp.a73, netp.a74 " +
                    "  from disposal d " +
                    "  left join appointment a                       on d.registration_id = a.registration_id " +
                    "  left join nhi_extend_disposal ned             on d.id              = ned.disposal_id " +
                    "  left join treatment_procedure tp              on d.id              = tp.disposal_id " +
                    "  left join nhi_extend_treatment_procedure netp on tp.id             = netp.treatment_procedure_id " +
                    " where (ned.a19 <> '2' " +
                    "   and ned.jhi_date between :begin and :end " +
                    "   and netp.a73 in :endoList " +
                    "   and d.id not in :excludeDisposalIds " +
                    "   and trim(ned.serial_number) <> '' " +
                    "   and trim(ned.a18) <> ''  " +
                    "   and tp.nhi_procedure_id is not null) " +
                    "    or (ned.a19 = '2' " +
                    "   and ned.replenishment_date between :begin and :end " +
                    "   and netp.a73 in :endoList " +
                    "   and d.id not in :excludeDisposalIds " +
                    "   and trim(ned.serial_number) <> '' " +
                    "   and trim(ned.a18) <> '' " +
                    "   and tp.nhi_procedure_id is not null) " +
                " order by a.doctor_user_id")
    List<NhiIndexEndoDTO> findEndoIndexRawData(
            @Param("begin") Instant begin,
            @Param("end") Instant end,
            @Param("endoList") List<String> endoList,
            @Param("excludeDisposalIds") List<Long> excludeDisposalIds);

    @Query(
        nativeQuery = true,
        value = "select " +
            "d.id as disposalId, " +
            "d.date_time as disposalDate, " +
            "ned.examination_code as examinationCode, " +
            "ned.examination_point as examinationPoint, " +
            "ned.patient_identity as patientIdentity, " +
            "ned.serial_number as serialNumber, " +
            "ned.a31 as visitTotalPoint, " +
            "ned.a32 as copayment, " +
            "a.patient_id as patientId, " +
            "a.doctor_user_id as doctorId, " +
            "p.name as patientName, " +
            "p.vip_patient as vipPatient " +
            "from disposal d " +
            "    left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "    left join appointment a on d.registration_id = a.registration_id " +
            "    left join patient p on a.patient_id = p.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "   and d.id not in :excludeDisposalId and trim(ned.serial_number) <> '' and trim(ned.a18) <> '' and tp.nhi_procedure_id is not null " +
            "or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "   and d.id not in :excludeDisposalId and trim(ned.serial_number) <> '' and trim(ned.a18) <> '' and tp.nhi_procedure_id is not null " +
            "order by d.id "
    )
    List<CalculateBaseData> findCalculateBaseDataWithoutTx(
        @Param("begin") LocalDate begin,
        @Param("end") LocalDate end,
        @Param("excludeDisposalId") List<Long> excludeDisposalId
    );
}
