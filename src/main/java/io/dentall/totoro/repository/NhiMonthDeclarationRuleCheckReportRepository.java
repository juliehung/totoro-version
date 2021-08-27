package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMonthDeclarationRuleCheckReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhiMonthDeclarationRuleCheckReportRepository extends JpaRepository<NhiMonthDeclarationRuleCheckReport, Long> {
    List<NhiMonthDeclarationRuleCheckReport> findByYearMonthEquals(String yearMonthString);
}
