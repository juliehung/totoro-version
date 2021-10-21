package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.TaipeiDistrictDto;
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
public class TaipeiDistrictService implements DistrictService {

    @Override
    public Optional<TaipeiDistrictDto> metric(
        LocalDate baseDate, MetricSubject metricSubject, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, metricSubject, source));
    }

    public List<TaipeiDistrictDto> metric(
        LocalDate baseDate, List<MetricSubject> subjects, List<MetricDisposal> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private TaipeiDistrictDto buildMetric(
        LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, MetricSubject subject, List<MetricDisposal> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source).applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricF1h1 = new F1h1Formula(metricConfig).calculate();
        BigDecimal metricF1h2 = new F1h2Formula(metricConfig).calculate();
        BigDecimal metricF1h3 = new F1h3Formula(metricConfig).calculate();
        BigDecimal metricF1h5 = new F1h5Formula(metricConfig).calculate();
        BigDecimal metricF1h6 = new F1h6Formula(metricConfig).calculate();
        BigDecimal metricF3h1 = new F3h1Formula(metricConfig).calculate();
        BigDecimal metricF3h2 = new F3h2Formula(metricConfig).calculate();
        BigDecimal metricF4h3 = new F4h3Formula(metricConfig).calculate();
        BigDecimal metricF5h3 = new F5h3Formula(metricConfig).calculate();
        BigDecimal metricI12 = new I12Formula(metricConfig).calculate();

        TaipeiDistrictDto taipeiDistrictDto = new TaipeiDistrictDto();
        taipeiDistrictDto.setType(metricSubjectType);
        taipeiDistrictDto.setF1h1(metricF1h1);
        taipeiDistrictDto.setF1h2(metricF1h2);
        taipeiDistrictDto.setF1h3(metricF1h3);
        taipeiDistrictDto.setF1h5(metricF1h5);
        taipeiDistrictDto.setF1h6(metricF1h6);
        taipeiDistrictDto.setF3h1(metricF3h1);
        taipeiDistrictDto.setF3h2(metricF3h2);
        taipeiDistrictDto.setF4h3(metricF4h3);
        taipeiDistrictDto.setF5h3(metricF5h3);
        taipeiDistrictDto.setI12(metricI12);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getMetricSubject().getId());
            doctorData.setDoctorName(metricConfig.getMetricSubject().getName());
            taipeiDistrictDto.setDoctor(doctorData);
        }

        return taipeiDistrictDto;
    }

}
