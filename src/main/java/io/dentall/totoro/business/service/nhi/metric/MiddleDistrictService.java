package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MiddleDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
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
public class MiddleDistrictService implements DistrictService {

    @Override
    public Optional<MiddleDistrictDto> metric(
        LocalDate baseDate, MetricSubject metricSubject, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, metricSubject, source));
    }

    public List<MiddleDistrictDto> metric(
        final LocalDate baseDate, List<MetricSubject> subjects, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private MiddleDistrictDto buildMetric(
        LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, MetricSubject subject, List<MetricDisposal> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source).applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricH1 = new H1Formula(metricConfig).calculate();
        BigDecimal metricH2 = new H2Formula(metricConfig).calculate();
        BigDecimal metricH3 = new H3Formula(metricConfig).calculate();
        BigDecimal metricH4 = new H4Formula(metricConfig).calculate();
        BigDecimal metricH5 = new H5Formula(metricConfig).calculate();
        BigDecimal metricH7 = new H7Formula(metricConfig).calculate();

        MiddleDistrictDto middleDistrictDto = new MiddleDistrictDto();
        middleDistrictDto.setType(metricSubjectType);
        middleDistrictDto.setH1(metricH1);
        middleDistrictDto.setH2(metricH2);
        middleDistrictDto.setH3(metricH3);
        middleDistrictDto.setH4(metricH4);
        middleDistrictDto.setH5(metricH5);
        middleDistrictDto.setH7(metricH7);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getMetricSubject().getId());
            doctorData.setDoctorName(metricConfig.getMetricSubject().getName());
            middleDistrictDto.setDoctor(doctorData);
        }

        return middleDistrictDto;
    }

}