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
                Collector collector = new Collector(nhiMetricRawVMList);
                Source<NhiMetricRawVM, NhiMetricRawVM> doctorSource = new DoctorSource(doctor.getId());
                Source<NhiMetricRawVM, NhiMetricRawVM> monthSelectedSource = new MonthSelectedSource(baseDate);
                Source<NhiMetricRawVM, NhiMetricRawVM> quarterSource = new QuarterSource(quarterRange);
                Source<NhiMetricRawVM, NhiMetricRawVM> threeMonthNearSource = new ThreeMonthNearSource(baseDate);
                Source<NhiMetricRawVM, NhiMetricRawVM> oneYearNearSource = new OneYearNearSource(baseDate);
                Source<NhiMetricRawVM, NhiMetricRawVM> halfYearNearSource = new HalfYearNearSource(baseDate);

                collector
                    .apply(doctorSource)
                    .apply(monthSelectedSource)
                    .apply(quarterSource)
                    .apply(threeMonthNearSource)
                    .apply(oneYearNearSource)
                    .apply(halfYearNearSource);

                BigDecimal metricL1 = new L1Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL2 = new L2Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL3 = new L3Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL4 = new L4Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL5 = new L5Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL6 = new L6Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL7 = new L7Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL8 = new L8Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL10 = new L10Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL11 = new L11Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL12 = new L12Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL13 = new L13Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL14 = new L14Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL15 = new L15Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL16 = new L16Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL17 = new L17Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL18 = new L18Formula(collector, quarterSource).calculate();
                BigDecimal metricL19 = new L19Formula(collector, quarterSource).calculate();
                BigDecimal metricL20 = new L20Formula(collector, monthSelectedSource).calculate();
                BigDecimal metricL21 = new L21Formula(collector, threeMonthNearSource).calculate();
                BigDecimal metricL22 = new L22Formula(collector, quarterSource).calculate();
                BigDecimal metricL23 = new L23Formula(collector, oneYearNearSource).calculate();
                BigDecimal metricL24 = new L24Formula(collector, halfYearNearSource).calculate();

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

                Section12 section12 = new Section12();
                section12.setL20(metricL20);
                section12.setL21(metricL21);
                section12.setL22(metricL22);
                section12.setL23(metricL23);

                Section13 section13 = new Section13();
                section13.setL24(metricL24);

                MetricLVM metricLVM = new MetricLVM();
                metricLVM.setDoctor(doctor);
                metricLVM.setSection5(section5);
                metricLVM.setSection6(section6);
                metricLVM.setSection7(section7);
                metricLVM.setSection8(section8);
                metricLVM.setSection10(section10);
                metricLVM.setSection12(section12);
                metricLVM.setSection13(section13);

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
