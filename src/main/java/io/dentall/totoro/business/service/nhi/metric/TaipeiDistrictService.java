package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.GiantMetricDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricFDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricLDto;
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
public class TaipeiDistrictService {

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
        BigDecimal metricF1h1 = new F1h1Formula(metricConfig).calculate();
        BigDecimal metricF1h2 = new F1h2Formula(metricConfig).calculate();
        BigDecimal metricF1h3 = new F1h3Formula(metricConfig).calculate();
        BigDecimal metricF2h4 = new F2h4Formula(metricConfig).calculate();
        BigDecimal metricF3h1 = new F3h1Formula(metricConfig).calculate();
        BigDecimal metricF3h2 = new F3h2Formula(metricConfig).calculate();
        BigDecimal metricF4h3 = new F4h3Formula(metricConfig).calculate();
        BigDecimal metricF5h3 = new F5h3Formula(metricConfig).calculate();
        BigDecimal metricF5h4 = new F5h4Formula(metricConfig).calculate();
        BigDecimal metricF5h5 = new F5h5Formula(metricConfig).calculate();
        BigDecimal metricF5h6 = new F5h6Formula(metricConfig).calculate();
        BigDecimal metricF5h7 = new F5h7Formula(metricConfig).calculate();
        BigDecimal metricF5h8 = new F5h8Formula(metricConfig).calculate();
        BigDecimal metricL22 = new L22Formula(metricConfig).calculate();

        MetricFDto metricFDto = new MetricFDto();
        metricFDto.setF1h1(metricF1h1);
        metricFDto.setF1h2(metricF1h2);
        metricFDto.setF1h3(metricF1h3);
        metricFDto.setF2h4(metricF2h4);
        metricFDto.setF3h1(metricF3h1);
        metricFDto.setF3h2(metricF3h2);
        metricFDto.setF4h3(metricF4h3);
        metricFDto.setF4h3(metricF4h3);
        metricFDto.setF5h3(metricF5h3);
        metricFDto.setF5h4(metricF5h4);
        metricFDto.setF5h5(metricF5h5);
        metricFDto.setF5h6(metricF5h6);
        metricFDto.setF5h7(metricF5h7);
        metricFDto.setF5h8(metricF5h8);

        MetricLDto metricLDto = new MetricLDto();
        metricLDto.setL22(metricL22);

        GiantMetricDto giantMetricDto = new GiantMetricDto();
        giantMetricDto.setType(metricSubjectType);
        giantMetricDto.setMetricFDto(metricFDto);
        giantMetricDto.setMetricLDto(metricLDto);

        if (metricSubjectType == DOCTOR) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            giantMetricDto.setDoctor(doctorData);
        }

        return giantMetricDto;
    }

}
