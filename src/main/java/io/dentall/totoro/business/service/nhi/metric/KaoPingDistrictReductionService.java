package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.DoctorPoint1Dto;
import io.dentall.totoro.business.service.nhi.metric.dto.KaoPingDistrictReductionDto;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConstants;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.UserRepository;
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
public class KaoPingDistrictReductionService implements DistrictService {

    private final UserRepository userRepository;

    public KaoPingDistrictReductionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<KaoPingDistrictReductionDto> metric(LocalDate baseDate, User subject, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(buildMetric(baseDate, holidayMap, subject, source));
    }

    public List<KaoPingDistrictReductionDto> metric(final LocalDate baseDate, List<User> subjects, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, holidayMap, subject, source))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    /**
     * 因為J1-1指標是要計算全部醫師 Point1 > 525000，所以傳入資料要是全診所的資料才行，請勿傳入單個醫師的資料
     */
    public List<DoctorPoint1Dto> getJ1h1Metric(LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, List<NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(MetricConstants.CLINIC, baseDate, source);
        metricConfig.applyHolidayMap(holidayMap);

        Map<Long, Long> map = new J1h1Formula(metricConfig).calculate();
        List<User> allUserList = userRepository.findAll();

        return map.entrySet().stream()
            .map(entry -> {
                Long doctorId = entry.getKey();
                Long point1 = entry.getValue();
                DoctorPoint1Dto dto = new DoctorPoint1Dto();
                dto.setDoctorId(doctorId);
                dto.setPoint1(point1);
                allUserList.stream().filter(u -> u.getId().equals(doctorId)).map(User::getFirstName).findFirst().ifPresent(dto::setDoctorName);
                return dto;
            }).collect(toList());
    }

    /**
     * 傳入資料要是全診所的資料才行，請勿傳入單個醫師的資料
     */
    public BigDecimal getJ1h2Metric(LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, List<NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(MetricConstants.CLINIC, baseDate, source);
        metricConfig.applyHolidayMap(holidayMap);
        return new J1h2Formula(metricConfig).calculate();
    }

    private KaoPingDistrictReductionDto buildMetric(LocalDate baseDate, Map<LocalDate, Optional<Holiday>> holidayMap, User subject, List<NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source);
        metricConfig.applyHolidayMap(holidayMap);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricJ2h2 = new J2h2Formula(metricConfig).calculate();
        BigDecimal metricJ2h3 = new J2h3Formula(metricConfig).calculate();
        BigDecimal metricJ2h4 = new J2h4Formula(metricConfig).calculate();
        BigDecimal metricJ2h5 = new J2h5Formula(metricConfig).calculate();

        KaoPingDistrictReductionDto kaoPingDistrictReductionDto = new KaoPingDistrictReductionDto();
        kaoPingDistrictReductionDto.setType(metricSubjectType);
        kaoPingDistrictReductionDto.setJ2h2(metricJ2h2);
        kaoPingDistrictReductionDto.setJ2h3(metricJ2h3);
        kaoPingDistrictReductionDto.setJ2h4(metricJ2h4);
        kaoPingDistrictReductionDto.setJ2h5(metricJ2h5);

        if (metricSubjectType == doctor) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            kaoPingDistrictReductionDto.setDoctor(doctorData);
        }

        return kaoPingDistrictReductionDto;
    }

}
