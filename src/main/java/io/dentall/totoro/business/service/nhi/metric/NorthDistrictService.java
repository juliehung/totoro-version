package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.GiantMetricDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricADto;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.DOCTOR;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class NorthDistrictService {

    public List<GiantMetricDto> metric(final LocalDate baseDate, List<User> subjects, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private GiantMetricDto buildMetric(LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, User subject, List<NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source);
        metricConfig.applyHolidayMap(holidayMap);

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

        MetricADto metricADto = new MetricADto();
        metricADto.setA10(metricA10);
        metricADto.setA14(metricA14);
        metricADto.setA15h1(metricA15h1);
        metricADto.setA15h2(metricA15h2);
        metricADto.setA17h1(metricA17h1);
        metricADto.setA17h2(metricA17h2);
        metricADto.setA18h1(metricA18h1);
        metricADto.setA18h2(metricA18h2);
        metricADto.setA7(metricA7);
        metricADto.setA8(metricA8);
        metricADto.setA9(metricA9);

        GiantMetricDto giantMetricDto = new GiantMetricDto();
        giantMetricDto.setType(metricSubjectType);
        giantMetricDto.setMetricADto(metricADto);

        if (metricSubjectType == DOCTOR) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            giantMetricDto.setDoctor(doctorData);
        }

        return giantMetricDto;
    }

}
