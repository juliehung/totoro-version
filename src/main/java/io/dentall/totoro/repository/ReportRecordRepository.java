package io.dentall.totoro.repository;

import io.dentall.totoro.business.service.report.context.ReportCategory;
import io.dentall.totoro.domain.ReportRecord;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRecordRepository extends JpaRepository<ReportRecord, Long>, JpaSpecificationExecutor<ReportRecord> {

    int countByCategoryAndStatus(ReportCategory category, BatchStatus status);

    @Query(nativeQuery = true,
        value = "select * " +
            "from report_record " +
            "where category = 'TREATMENT' " +
            "and attrs ->> 'treatmentType' = :treatmentType " +
            "and attrs ->> 'treatmentId' = :treatmentId " +
            "order by last_modified_date desc")
    List<ReportRecord> findTreatmentReport(@Param("treatmentType") String treatmentType, @Param("treatmentId") String treatmentId);

    List<ReportRecord> findByCategoryOrderByCreatedDateDesc(ReportCategory category);
}
