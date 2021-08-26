package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMetricReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhiMetricReportRepository extends JpaRepository<NhiMetricReport, Long> {
}
