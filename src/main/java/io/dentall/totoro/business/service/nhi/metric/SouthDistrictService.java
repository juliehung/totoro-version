package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.SouthDistrictDto;
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

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.doctor;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class SouthDistrictService implements DistrictService {

    @Override
    public Optional<SouthDistrictDto> metric(LocalDate baseDate, User subject, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, subject, source));
    }

    public List<SouthDistrictDto> metric(final LocalDate baseDate, List<User> subjects, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private SouthDistrictDto buildMetric(LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, User subject, List<NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source);
        metricConfig.applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricA10 = new A10Formula(metricConfig).calculate();
        BigDecimal metricI2 = new I2Formula(metricConfig).calculate();
        BigDecimal metricI13 = new I13Formula(metricConfig).calculate();
        BigDecimal metricI11 = new I11Formula(metricConfig).calculate();
        BigDecimal metricI12 = new I12Formula(metricConfig).calculate();
        BigDecimal metricI15 = new I15Formula(metricConfig).calculate();
        BigDecimal metricI3 = new I3Formula(metricConfig).calculate();
        BigDecimal metricI4 = new I4Formula(metricConfig).calculate();
        BigDecimal metricI5 = new I5Formula(metricConfig).calculate();
        BigDecimal metricI6 = new I6Formula(metricConfig).calculate();
        BigDecimal metricI7 = new I7Formula(metricConfig).calculate();
        BigDecimal metricI8 = new I8Formula(metricConfig).calculate();
        BigDecimal metricI19 = new I9Formula(metricConfig).calculate();

        SouthDistrictDto southDistrictDto = new SouthDistrictDto();
        southDistrictDto.setType(metricSubjectType);
        southDistrictDto.setA10(metricA10);
        southDistrictDto.setI2(metricI2);
        southDistrictDto.setI13(metricI13);
        southDistrictDto.setI11(metricI11);
        southDistrictDto.setI12(metricI12);
        southDistrictDto.setI15(metricI15);
        southDistrictDto.setI3(metricI3);
        southDistrictDto.setI4(metricI4);
        southDistrictDto.setI5(metricI5);
        southDistrictDto.setI6(metricI6);
        southDistrictDto.setI7(metricI7);
        southDistrictDto.setI8(metricI8);
        southDistrictDto.setI19(metricI19);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            southDistrictDto.setDoctor(doctorData);
        }

        return southDistrictDto;
    }

}
