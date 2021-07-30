package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.filter.*;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.vm.*;
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
                Filter quarterFilter = new QuarterFilter(quarterRange);

                Collector collector = new Collector(nhiMetricRawVMList).apply(doctorFilter).apply(monthSelectedFilter).apply(quarterFilter);
                String monthSelectedSourceName = monthSelectedFilter.filterKey().output();
                String quarterSourceName = quarterFilter.filterKey().output();

                BigDecimal metricL1 = new L1Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL2 = new L2Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL3 = new L3Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL4 = new L4Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL5 = new L5Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL6 = new L6Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL7 = new L7Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL8 = new L8Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL10 = new L10Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL11 = new L11Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL12 = new L12Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL13 = new L13Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL14 = new L14Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL15 = new L15Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL16 = new L16Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL17 = new L17Formula(monthSelectedSourceName).calculate(collector);
                BigDecimal metricL18 = new L18Formula(quarterSourceName).calculate(collector);
                BigDecimal metricL19 = new L19Formula(quarterSourceName).calculate(collector);

                Section5 section5 = new Section5();
                section5.setL1(metricL1);
                section5.setL2(metricL2);
                section5.setL3(metricL3);
                section5.setL4(metricL4);

                Section6 section6 = new Section6();
                section6.setL5(metricL5);
                section6.setL6(metricL6);

                Section7 section7 = new Section7();
                section7.setL7(metricL7);
                section7.setL8(metricL8);

                Section8 section8 = new Section8();
                section8.setL10(metricL10);

                Section10 section10 = new Section10();
                section10.setL11(metricL11);
                section10.setL12(metricL12);
                section10.setL13(metricL13);
                section10.setL14(metricL14);
                section10.setL15(metricL15);
                section10.setL16(metricL16);
                section10.setL17(metricL17);
                section10.setL18(metricL18);
                section10.setL19(metricL19);

                MetricLVM metricLVM = new MetricLVM();
                metricLVM.setDoctor(doctor);
                metricLVM.setSection5(section5);
                metricLVM.setSection6(section6);
                metricLVM.setSection7(section7);
                metricLVM.setSection8(section8);
                metricLVM.setSection10(section10);

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
