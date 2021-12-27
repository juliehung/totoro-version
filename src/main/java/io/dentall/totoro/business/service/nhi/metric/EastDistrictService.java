package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.EastDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
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
public class EastDistrictService implements DistrictService {

    @Override
    public Optional<EastDistrictDto> metric(
        LocalDate baseDate, MetricSubject metricSubject, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, metricSubject, source));
    }

    public List<EastDistrictDto> metric(
        final LocalDate baseDate, List<MetricSubject> subjects, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private EastDistrictDto buildMetric(
        LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, MetricSubject subject, List<MetricDisposal> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source).applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricG6 = new G6Formula(metricConfig).calculate();
        BigDecimal metricG8h1 = new G8h1Formula(metricConfig).calculate();
        BigDecimal metricG8h2 = new G8h2Formula(metricConfig).calculate();
        BigDecimal metricG8h3 = new G8h3Formula(metricConfig).calculate();
        BigDecimal metricG8h4 = new G8h4Formula(metricConfig).calculate();

        EastDistrictDto eastDistrictDto = new EastDistrictDto();
        eastDistrictDto.setType(metricSubjectType);
        eastDistrictDto.setG6(metricG6);
        eastDistrictDto.setG8h1(metricG8h1);
        eastDistrictDto.setG8h2(metricG8h2);
        eastDistrictDto.setG8h3(metricG8h3);
        eastDistrictDto.setG8h4(metricG8h4);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getMetricSubject().getId());
            doctorData.setDoctorName(metricConfig.getMetricSubject().getName());
            eastDistrictDto.setDoctor(doctorData);
        }

        return eastDistrictDto;
    }

}