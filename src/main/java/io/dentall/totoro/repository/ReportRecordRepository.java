package io.dentall.totoro.repository;

import io.dentall.totoro.business.service.report.context.ReportCategory;
import io.dentall.totoro.domain.ReportRecord;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRecordRepository extends JpaRepository<ReportRecord, Long>, JpaSpecificationExecutor<ReportRecord> {

    int countByCategoryAndStatus(ReportCategory category, BatchStatus status);

    List<ReportRecord> findByCategoryOrderByCreatedDateDesc(ReportCategory category);
}
