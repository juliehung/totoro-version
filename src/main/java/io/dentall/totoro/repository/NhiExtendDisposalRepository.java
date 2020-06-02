package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.repository.dao.MonthDisposalDAO;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the NhiExtendDisposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendDisposalRepository extends JpaRepository<NhiExtendDisposal, Long>, JpaSpecificationExecutor<NhiExtendDisposal> {

    Set<NhiExtendDisposalTable> findNhiExtendDisposalByDate(LocalDate date);

    Optional<NhiExtendDisposalTable> findNhiExtendDisposalByDisposal_Id(Long id);

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
        "patient.name ) " +
        "from " +
        "   NhiExtendDisposal as nhiExtendDisposal left outer join nhiExtendDisposal.disposal as disposal," +
        "   Patient as patient " +
        "where 1 = 1 " +
        "and nhiExtendDisposal.patientId = patient.id " +
        "and " + dateBetween)
    List<MonthDisposalDAO> findDisposalIdAndNhiExtendDisposalPrimByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select count(nhiExtendDisposal) from NhiExtendDisposal nhiExtendDisposal where " + dateBetween)
    long countByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
