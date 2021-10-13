package io.dentall.totoro.repository;

import io.dentall.totoro.business.service.report.context.ReportMapper;
import io.dentall.totoro.business.service.report.dto.*;
import io.dentall.totoro.domain.ReportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Repository
public interface ReportDataRepository extends JpaRepository<ReportRecord, Long> {

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "    and p.id in :patientIds " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "    and p.id in :patientIds " +
            "    and trim(ned.a18) <> '' ")
    List<DisposalDto> findDisposalBetweenAndPatient(@Param(value = "begin") LocalDate begin, @Param(value = "end") LocalDate end, @Param(value = "patientIds") List<Long> patientIds);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       netp.a73                  as procedureCode, " +
            "       netp.a74                  as procedureTooth, " +
            "       netp.a75                  as procedureSurface, " +
            "       tp.total                  as procedurePoint, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join nhi_procedure np on np.id = tp.nhi_procedure_id " +
            "         left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "    and tp.nhi_procedure_id is not null " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "    and tp.nhi_procedure_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<NhiDto> findNhiBetween(@Param(value = "begin") LocalDate begin, @Param(value = "end") LocalDate end);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       netp.a73                  as procedureCode, " +
            "       netp.a74                  as procedureTooth, " +
            "       netp.a75                  as procedureSurface, " +
            "       tp.total                  as procedurePoint, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join nhi_procedure np on np.id = tp.nhi_procedure_id " +
            "         left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "    and netp.a73 in :codes " +
            "    and tp.nhi_procedure_id is not null " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "    and netp.a73 in :codes " +
            "    and tp.nhi_procedure_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<NhiDto> findNhiBetweenAndCode(@Param(value = "begin") LocalDate begin, @Param(value = "end") LocalDate end, @Param(value = "codes") List<String> codes);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       netp.a73                  as procedureCode, " +
            "       netp.a74                  as procedureTooth, " +
            "       netp.a75                  as procedureSurface, " +
            "       tp.total                  as procedurePoint, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join nhi_procedure np on np.id = tp.nhi_procedure_id " +
            "         left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date >= :begin " +
            "    and p.id in :patientIds " +
            "    and tp.nhi_procedure_id is not null " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date >= :begin " +
            "    and p.id in :patientIds " +
            "    and tp.nhi_procedure_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<NhiDto> findNhiAfterAndPatient(@Param(value = "begin") LocalDate begin, @Param(value = "patientIds") List<Long> patientIds);


    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       netp.a73                  as procedureCode, " +
            "       netp.a74                  as procedureTooth, " +
            "       netp.a75                  as procedureSurface, " +
            "       tp.total                  as procedurePoint, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join nhi_procedure np on np.id = tp.nhi_procedure_id " +
            "         left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where p.id in :patientIds " +
            "    and netp.a73 in :codes " +
            "    and tp.nhi_procedure_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<NhiDto> findNhiPatientAndCode(@Param(value = "patientIds") List<Long> patientIds, @Param(value = "codes") List<String> codes);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       netp.a73                  as procedureCode, " +
            "       netp.a74                  as procedureTooth, " +
            "       netp.a75                  as procedureSurface, " +
            "       tp.total                  as procedurePoint, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join nhi_procedure np on np.id = tp.nhi_procedure_id " +
            "         left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where d.id in :disposalIds ")
    List<NhiDto> findNhiDisposalIds(@Param(value = "disposalIds") List<Long> disposalIds);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       np.content                as procedureName, " +
            "       npt.minor                 as procedureMinorType, " +
            "       tp.id                     as treatmentProcedureId, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join procedure np on np.id = tp.procedure_id " +
            "         left join procedure_type npt on npt.id = np.procedure_type_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "    and tp.procedure_id is not null " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "    and tp.procedure_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<OwnExpenseDto> findOwnExpenseBetween(@Param(value = "begin") LocalDate begin, @Param(value = "end") LocalDate end);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       np.content                as procedureName, " +
            "       npt.minor                 as procedureMinorType, " +
            "       tp.id                     as treatmentProcedureId, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join procedure np on np.id = tp.procedure_id " +
            "         left join procedure_type npt on npt.id = np.procedure_type_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date >= :begin " +
            "    and p.id in :patientIds " +
            "    and tp.procedure_id is not null " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date >= :begin " +
            "    and p.id in :patientIds " +
            "    and tp.procedure_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<OwnExpenseDto> findOwnExpenseAfterAndPatient(@Param(value = "begin") LocalDate begin, @Param(value = "patientIds") List<Long> patientIds);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       np.id                     as procedureId, " +
            "       np.content                as procedureName, " +
            "       npt.minor                 as procedureMinorType, " +
            "       tp.id                     as treatmentProcedureId, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_procedure tp on d.id = tp.disposal_id " +
            "         left join procedure np on np.id = tp.procedure_id " +
            "         left join procedure_type npt on npt.id = np.procedure_type_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "    and tp.procedure_id in :procedureIds " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "    and tp.procedure_id in :procedureIds " +
            "    and trim(ned.a18) <> '' ")
    List<OwnExpenseDto> findOwnExpenseBetweenAndProcedureId(
        @Param(value = "begin") LocalDate begin, @Param(value = "end") LocalDate end, @Param(value = "procedureIds") List<Long> procedureIds);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       dg.id                     as drugId, " +
            "       dg.name                   as drugName, " +
            "       dg.nhi_code               as drugNhiCode, " +
            "       td.day                    as drugDay, " +
            "       td.way                    as drugWay, " +
            "       td.frequency              as drugFrequency, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_drug td on td.prescription_id = d.prescription_id " +
            "         left join drug dg on dg.id = td.drug_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date between :begin and :end " +
            "    and td.prescription_id is not null " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date between :begin and :end " +
            "    and td.prescription_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<DrugDto> findDrugBetween(@Param(value = "begin") LocalDate begin, @Param(value = "end") LocalDate end);

    @Query(nativeQuery = true,
        value = "select d.id                      as disposalId, " +
            "       case " +
            "           when ned.a19 = '1' then ned.jhi_date " +
            "           when ned.a19 = '2' then ned.replenishment_date " +
            "           else ned.jhi_date end as disposalDate, " +
            "       ned.a18                   as cardNumber, " +
            "       ju.id                     as doctorId, " +
            "       ju.first_name             as doctorName, " +
            "       p.id                      as patientId, " +
            "       p.name                    as patientName, " +
            "       p.birth                   as patientBirth, " +
            "       p.phone                   as patientPhone, " +
            "       p.note                    as patientNote, " +
            "       dg.id                     as drugId, " +
            "       dg.name                   as drugName, " +
            "       dg.nhi_code               as drugNhiCode, " +
            "       td.day                    as drugDay, " +
            "       td.way                    as drugWay, " +
            "       td.frequency              as drugFrequency, " +
            "       r.arrival_time            as registrationDate " +
            "from disposal d " +
            "         left join registration r on r.id = d.registration_id " +
            "         left join appointment a on r.id = a.registration_id " +
            "         left join patient p on p.id = a.patient_id " +
            "         left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "         left join treatment_drug td on td.prescription_id = d.prescription_id " +
            "         left join drug dg on dg.id = td.drug_id " +
            "         left join extend_user eu on ned.a15 = eu.national_id " +
            "         left join jhi_user ju on eu.user_id = ju.id " +
            "where ned.a19 = '1' and ned.jhi_date >= :begin " +
            "    and p.id in :patientIds " +
            "    and td.prescription_id is not null " +
            "    and trim(ned.a18) <> '' " +
            "   or ned.a19 = '2' and ned.replenishment_date >= :begin " +
            "    and p.id in :patientIds " +
            "    and td.prescription_id is not null " +
            "    and trim(ned.a18) <> '' ")
    List<DrugDto> findDrugAfterAndPatient(@Param(value = "begin") LocalDate begin, @Param(value = "patientIds") List<Long> patientIds);

    @Query(nativeQuery = true,
        value = "with base as (select p.id                      as                                                  patientId, " +
            "                     d.id                      as                                                  disposalId, " +
            "                     netp.a73                  as                                                  procedureCode, " +
            "                     case " +
            "                         when ned.a19 = '1' then ned.jhi_date " +
            "                         when ned.a19 = '2' then ned.replenishment_date " +
            "                         else ned.jhi_date end as                                                  disposalDate, " +
            "                     row_number() over (partition by p.id, netp.a73 order by case " +
            "                                                                       when ned.a19 = '1' then ned.jhi_date " +
            "                                                                       when ned.a19 = '2' then ned.replenishment_date " +
            "                                                                       else ned.jhi_date end desc) row_number " +
            "              from disposal d " +
            "                       left join registration r on r.id = d.registration_id " +
            "                       left join appointment a on r.id = a.registration_id " +
            "                       left join patient p on p.id = a.patient_id " +
            "                       left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "                       left join treatment_procedure tp on d.id = tp.disposal_id " +
            "                       left join nhi_procedure np on np.id = tp.nhi_procedure_id " +
            "                       left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "              where netp.a73 in :codes) " +
            "select patientId, disposalId, disposalDate, procedureCode " +
            "from base " +
            "where row_number = 1")
    List<NhiDto> findAllPatientLatestNhi(@Param("codes") List<String> codes);

    default List<DisposalVo> findDisposalVoBetweenAndPatient(LocalDate begin, LocalDate end, List<Long> patientIds) {
        return findDisposalBetweenAndPatient(begin, end, patientIds).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToDisposalVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<NhiVo> findNhiVoBetween(LocalDate begin, LocalDate end) {
        return findNhiBetween(begin, end).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToNhiVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<NhiVo> findNhiVoBetweenAndCode(LocalDate begin, LocalDate end, List<String> codes) {
        return findNhiBetweenAndCode(begin, end, codes).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToNhiVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<NhiVo> findNhiVoAfterAndPatient(LocalDate begin, List<Long> patientIds) {
        return findNhiAfterAndPatient(begin, patientIds).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToNhiVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<NhiVo> findAllPatientLatestNhiVo(List<String> codes) {
        return findAllPatientLatestNhi(codes).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToNhiVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<OwnExpenseVo> findOwnExpenseVoBetween(LocalDate begin, LocalDate end) {
        return findOwnExpenseBetween(begin, end).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToOwnExpenseVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<OwnExpenseVo> findOwnExpenseVoAfterAndPatient(LocalDate begin, List<Long> patientIds) {
        return findOwnExpenseAfterAndPatient(begin, patientIds).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToOwnExpenseVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<OwnExpenseVo> findOwnExpenseVoBetweenAndProcedureId(LocalDate begin, LocalDate end, List<Long> procedureIds) {
        return findOwnExpenseBetweenAndProcedureId(begin, end, procedureIds).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToOwnExpenseVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<DrugVo> findDrugVoBetween(LocalDate begin, LocalDate end) {
        return findDrugBetween(begin, end).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToDrugVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<DrugVo> findDrugVoAfterAndPatient(LocalDate begin, List<Long> patientIds) {
        return findDrugAfterAndPatient(begin, patientIds).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToDrugVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<TeethCleaningVo> findNhiVoDisposalIds(List<Long> disposalIds) {
        return findNhiDisposalIds(disposalIds).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToTeethCleaningVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }

    default List<FluoridationVo> findFluoridationVoDisposalIds(List<Long> disposalIds) {
        return findNhiDisposalIds(disposalIds).stream().parallel()
            .map(ReportMapper.INSTANCE::mapToFluoridationVo).filter(vo -> nonNull(vo.getDisposalDate())).collect(toList());
    }
}
