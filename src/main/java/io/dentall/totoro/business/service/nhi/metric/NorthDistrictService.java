package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.NorthDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubject;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
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
public class NorthDistrictService implements DistrictService {

    @Override
    public Optional<NorthDistrictDto> metric(
        LocalDate baseDate, MetricSubject metricSubject, List<? extends NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, metricSubject, source));
    }

    public List<NorthDistrictDto> metric(
        final LocalDate baseDate, List<MetricSubject> subjects, List<? extends NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private NorthDistrictDto buildMetric(
        LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, MetricSubject subject, List<? extends NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source).applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricA10 = new A10Formula(metricConfig).calculate();
        BigDecimal metricA14 = new A14Formula(metricConfig).calculate();
        BigDecimal metricA15h1 = new A15h1Formula(metricConfig).calculate();
        BigDecimal metricA15h2 = new A15h2Formula(metricConfig).calculate();
        BigDecimal metricA17h1 = new A17h1Formula(metricConfig).calculate();
        BigDecimal metricA17h2 = new A17h2Formula(metricConfig).calculate();
        BigDecimal metricA18h1 = new A18h1Formula(metricConfig).calculate();
        BigDecimal metricA18h2 = new A18h2Formula(metricConfig).calculate();
        BigDecimal metricA7 = new A7Formula(metricConfig).calculate();
        BigDecimal metricA8 = new A8Formula(metricConfig).calculate();
        BigDecimal metricA9 = new A9Formula(metricConfig).calculate();

        NorthDistrictDto northDistrictDto = new NorthDistrictDto();
        northDistrictDto.setType(metricSubjectType);
        northDistrictDto.setA10(metricA10);
        northDistrictDto.setA14(metricA14);
        northDistrictDto.setA15h1(metricA15h1);
        northDistrictDto.setA15h2(metricA15h2);
        northDistrictDto.setA17h1(metricA17h1);
        northDistrictDto.setA17h2(metricA17h2);
        northDistrictDto.setA18h1(metricA18h1);
        northDistrictDto.setA18h2(metricA18h2);
        northDistrictDto.setA7(metricA7);
        northDistrictDto.setA8(metricA8);
        northDistrictDto.setA9(metricA9);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getMetricSubject().getId());
            doctorData.setDoctorName(metricConfig.getMetricSubject().getName());
            northDistrictDto.setDoctor(doctorData);
        }

        return northDistrictDto;
    }

}
