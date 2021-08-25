package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.EastDistrictDto;
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
public class EastDistrictService {

    public List<EastDistrictDto> metric(final LocalDate baseDate, List<User> subjects, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private EastDistrictDto buildMetric(LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, User subject, List<NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source);
        metricConfig.applyHolidayMap(holidayMap);

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

        if (metricSubjectType == DOCTOR) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            eastDistrictDto.setDoctor(doctorData);
        }

        return eastDistrictDto;
    }

}
