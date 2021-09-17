package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.KaoPingDistrictRegularDto;
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
public class KaoPingDistrictRegularService implements DistrictService {

    @Override
    public Optional<KaoPingDistrictRegularDto> metric(
        LocalDate baseDate, MetricSubject metricSubject, List<? extends NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, metricSubject, source));
    }

    public List<KaoPingDistrictRegularDto> metric(
        LocalDate baseDate, List<MetricSubject> subjects, List<? extends NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private KaoPingDistrictRegularDto buildMetric(
        LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, MetricSubject subject, List<? extends NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source).applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricK1 = new K1Formula(metricConfig).calculate();
        BigDecimal metricK2 = new K2Formula(metricConfig).calculate();
        BigDecimal metricK11 = new K11Formula(metricConfig).calculate();
        BigDecimal metricJ2h5 = new J2h5Formula(metricConfig).calculate();
        BigDecimal metricK10 = new K10Formula(metricConfig).calculate();
        BigDecimal metricK3 = new K3Formula(metricConfig).calculate();
        BigDecimal metricK4 = new K4Formula(metricConfig).calculate();
        BigDecimal metricK5 = new K5Formula(metricConfig).calculate();
        BigDecimal metricK6 = new K6Formula(metricConfig).calculate();
        BigDecimal metricK7 = new K7Formula(metricConfig).calculate();
        BigDecimal metricK12 = new K12Formula(metricConfig).calculate();
        BigDecimal metricK14 = new K14Formula(metricConfig).calculate();

        KaoPingDistrictRegularDto kaoPingDistrictRegularDto = new KaoPingDistrictRegularDto();
        kaoPingDistrictRegularDto.setType(metricSubjectType);
        kaoPingDistrictRegularDto.setK1(metricK1);
        kaoPingDistrictRegularDto.setK2(metricK2);
        kaoPingDistrictRegularDto.setK11(metricK11);
        kaoPingDistrictRegularDto.setJ2h5(metricJ2h5);
        kaoPingDistrictRegularDto.setK10(metricK10);
        kaoPingDistrictRegularDto.setK3(metricK3);
        kaoPingDistrictRegularDto.setK4(metricK4);
        kaoPingDistrictRegularDto.setK5(metricK5);
        kaoPingDistrictRegularDto.setK6(metricK6);
        kaoPingDistrictRegularDto.setK7(metricK7);
        kaoPingDistrictRegularDto.setK12(metricK12);
        kaoPingDistrictRegularDto.setK14(metricK14);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getMetricSubject().getId());
            doctorData.setDoctorName(metricConfig.getMetricSubject().getName());
            kaoPingDistrictRegularDto.setDoctor(doctorData);
        }

        return kaoPingDistrictRegularDto;
    }

}
