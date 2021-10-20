package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.KaoPingDistrictReductionDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.source.ClinicSubject;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubject;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;
import io.dentall.totoro.domain.Holiday;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.doctor;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class KaoPingDistrictReductionService implements DistrictService {

    @Override
    public Optional<KaoPingDistrictReductionDto> metric(
        LocalDate baseDate, MetricSubject metricSubject, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, metricSubject, source));
    }

    public List<KaoPingDistrictReductionDto> metric(
        LocalDate baseDate, List<MetricSubject> subjects, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    /**
     * 傳入資料要是全診所的資料才行，請勿傳入單個醫師的資料
     */
    public BigDecimal getJ1h2Metric(LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, List<MetricDisposal> source) {
        MetricConfig metricConfig = new MetricConfig(new ClinicSubject(), baseDate, source).applyHolidayMap(holidayMap);
        return new J1h2Formula(metricConfig).calculate();
    }

    private KaoPingDistrictReductionDto buildMetric(
        LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, MetricSubject subject, List<MetricDisposal> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source).applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricJ1h1 = new J1h1Formula(metricConfig).calculate();
        BigDecimal metricJ2h2 = new J2h2Formula(metricConfig).calculate();
        BigDecimal metricJ2h3 = new J2h3Formula(metricConfig).calculate();
        BigDecimal metricJ2h4 = new J2h4Formula(metricConfig).calculate();
        BigDecimal metricJ2h5 = new J2h5Formula(metricConfig).calculate();

        KaoPingDistrictReductionDto kaoPingDistrictReductionDto = new KaoPingDistrictReductionDto();
        kaoPingDistrictReductionDto.setType(metricSubjectType);
        kaoPingDistrictReductionDto.setJ1h1(metricJ1h1);
        kaoPingDistrictReductionDto.setJ2h2(metricJ2h2);
        kaoPingDistrictReductionDto.setJ2h3(metricJ2h3);
        kaoPingDistrictReductionDto.setJ2h4(metricJ2h4);
        kaoPingDistrictReductionDto.setJ2h5(metricJ2h5);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getMetricSubject().getId());
            doctorData.setDoctorName(metricConfig.getMetricSubject().getName());
            kaoPingDistrictReductionDto.setDoctor(doctorData);
        }

        return kaoPingDistrictReductionDto;
    }

}
