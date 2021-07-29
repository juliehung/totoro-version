package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.filter.DoctorFilter;
import io.dentall.totoro.business.service.nhi.metric.filter.Filter;
import io.dentall.totoro.business.service.nhi.metric.filter.MonthSelectedFilter;
import io.dentall.totoro.business.service.nhi.metric.vm.MetricLVM;
import io.dentall.totoro.business.service.nhi.metric.vm.Section5;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.util.DateTimeUtil.BeginEnd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.dentall.totoro.security.AuthoritiesConstants.DOCTOR;
import static io.dentall.totoro.service.util.DateTimeUtil.convertLocalDateToBeginOfDayInstant;
import static io.dentall.totoro.service.util.DateTimeUtil.getCurrentQuarterMonthsRangeInstant;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class NhiMetricLService {

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final UserRepository userRepository;

    public NhiMetricLService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        UserRepository userRepository
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.userRepository = userRepository;
    }

    public List<MetricLVM> metric(final LocalDate baseDate, List<Long> excludeDisposalIds) {
        excludeDisposalIds = ofNullable(excludeDisposalIds).filter(list -> list.size() > 0).orElse(asList(0L));
        BeginEnd quarterRange = getCurrentQuarterMonthsRangeInstant(convertLocalDateToBeginOfDayInstant(baseDate));
        List<User> doctors = findAllDoctor();
        Instant begin = quarterRange.getBegin().minus(1095, ChronoUnit.DAYS);
        List<NhiMetricRawVM> nhiMetricRawVMList = nhiExtendDisposalRepository.findMetricRaw(
            begin,
            quarterRange.getEnd(),
            excludeDisposalIds
        );

        List<MetricLVM> metricLVMList = doctors.parallelStream()
            .map(doctor -> {
                DoctorFilter doctorFilter = new DoctorFilter(doctor.getId());
                Filter monthSelectedFilter = new MonthSelectedFilter(baseDate);

                Collector collector = new Collector(nhiMetricRawVMList).apply(doctorFilter).apply(monthSelectedFilter);
                String monthSelectedSourceName = monthSelectedFilter.filterKey().output();

                BigDecimal metricL1 = new L1Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL2 = new L2Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL3 = new L3Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL4 = new L4Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL5 = new L5Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL6 = new L6Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL7 = new L7Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL8 = new L8Formula(monthSelectedSourceName).calculate(collector);

                Section5 section5 = new Section5();
                section5.setL1(metricL1);
                section5.setL2(metricL2);
                section5.setL3(metricL3);
                section5.setL4(metricL4);
                section5.setL5(metricL5);
                section5.setL6(metricL6);
                section5.setL7(metricL7);
                section5.setL8(metricL8);

                MetricLVM metricLVM = new MetricLVM();
                metricLVM.setDoctor(doctor);
                metricLVM.setSection5(section5);

                return metricLVM;
            }).collect(toList());

        return metricLVMList;
    }

    private List<User> findAllDoctor() {
        List<User> doctors = userRepository.findAllByActivatedIsTrue();
        return doctors.stream()
            .filter(doctor -> doctor.getAuthorities().stream().anyMatch(authority -> DOCTOR.equals(authority.getName())))
            .collect(toList());
    }

}
