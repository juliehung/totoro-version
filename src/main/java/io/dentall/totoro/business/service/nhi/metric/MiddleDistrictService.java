package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.GiantMetricDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricFDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricHDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricLDto;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.service.HolidayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.DOCTOR;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.getHolidayMap;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class MiddleDistrictService {

    private final HolidayService holidayService;

    public MiddleDistrictService(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

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
        BigDecimal metricH1 = new H1Formula(metricConfig).calculate();
        BigDecimal metricH2 = new H2Formula(metricConfig).calculate();
        BigDecimal metricH3 = new H3Formula(metricConfig).calculate();
        BigDecimal metricH4 = new H4Formula(metricConfig).calculate();
        BigDecimal metricH5 = new H5Formula(metricConfig).calculate();
        BigDecimal metricH7 = new H7Formula(metricConfig).calculate();

        MetricHDto metricHDto = new MetricHDto();
        metricHDto.setH1(metricH1);
        metricHDto.setH2(metricH2);
        metricHDto.setH3(metricH3);
        metricHDto.setH4(metricH4);
        metricHDto.setH5(metricH5);
        metricHDto.setH7(metricH7);

        GiantMetricDto giantMetricDto = new GiantMetricDto();
        giantMetricDto.setType(metricSubjectType);
        giantMetricDto.setMetricHDto(metricHDto);

        if (metricSubjectType == DOCTOR) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            giantMetricDto.setDoctor(doctorData);
        }

        return giantMetricDto;
    }

}
