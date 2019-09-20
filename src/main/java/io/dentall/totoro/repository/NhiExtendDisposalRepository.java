package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the NhiExtendDisposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendDisposalRepository extends JpaRepository<NhiExtendDisposal, Long> {

    String dateBetween = "((nhiExtendDisposal.date between :start and :end and nhiExtendDisposal.a54 is null) or " +
        "(nhiExtendDisposal.a19 = '2' and nhiExtendDisposal.replenishmentDate between :start and :end)) ";

    String dateGte = "((nhiExtendDisposal.date >= :gte and nhiExtendDisposal.a54 is null) or " +
        "(nhiExtendDisposal.a19 = '2' and nhiExtendDisposal.replenishmentDate >= :gte)) ";

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " +
            "((nhiExtendDisposal.date = :date and nhiExtendDisposal.a54 is null) or " +
            "(nhiExtendDisposal.a19 = '2' and nhiExtendDisposal.replenishmentDate = :date))"
    )
    List<NhiExtendDisposal> findByDate(@Param("date") LocalDate date);

    @Query("select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " + dateBetween)
    List<NhiExtendDisposal> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " +
            "nhiExtendDisposal.patientId = :patientId and " + dateGte +
            "order by nhiExtendDisposal.date, nhiExtendDisposal.replenishmentDate desc"
    )
    List<NhiExtendDisposal> findByDateGreaterThanEqualAndPatientIdOrderByDateDesc(@Param("gte") LocalDate gte, @Param("patientId") Long patientId);

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " +
            "nhiExtendDisposal.patientId = :patientId and " + dateBetween
    )
    List<NhiExtendDisposal> findByDateBetweenAndPatientId(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("patientId") Long patientId);

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " +
            "nhiExtendDisposal.uploadStatus <> 'NONE' and " + dateBetween
    )
    List<NhiExtendDisposal> findByDateBetweenAndUploadStatusNotNone(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(
        "select nhiExtendDisposal from NhiExtendDisposal nhiExtendDisposal where " +
            "nhiExtendDisposal.uploadStatus <> 'NONE' and nhiExtendDisposal.patientId = :patientId and " + dateGte
    )
    List<NhiExtendDisposal> findByDateGreaterThanEqualAndPatientIdAndUploadStatusNotNone(@Param("gte") LocalDate gte, @Param("patientId") Long patientId);

    List<NhiExtendDisposal> findByPatientId(Long patientId);

    @Query(
        "select nhiExtendDisposal " +
            "from NhiExtendDisposal nhiExtendDisposal " +
            "where " + dateBetween +
            " and nhiExtendDisposal.uploadStatus <> 'NONE' and nhiExtendDisposal.patientId = :patientId"
    )
    List<NhiExtendDisposal> findByDateBetweenAndUploadStatusNotAndPatientId(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("patientId") Long patientId);
}
