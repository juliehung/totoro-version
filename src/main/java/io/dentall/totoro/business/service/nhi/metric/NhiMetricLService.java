package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.service.nhi.metric.dto.*;
import io.dentall.totoro.business.service.nhi.metric.filter.*;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.mapper.SpecialTreatmentMapper;
import io.dentall.totoro.business.service.nhi.metric.mapper.TimeLineDataMapper;
import io.dentall.totoro.business.service.nhi.metric.vm.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.filter.MetricSubjectType.CLINIC;
import static io.dentall.totoro.security.AuthoritiesConstants.ADMIN;
import static io.dentall.totoro.security.AuthoritiesConstants.DOCTOR;
import static io.dentall.totoro.service.util.DateTimeUtil.*;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class NhiMetricLService {

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    public NhiMetricLService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        UserService userService, UserRepository userRepository) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public List<MetricLVM> metric(final LocalDate baseDate, List<Long> excludeDisposalIds) {
        Optional<User> userOptional = this.userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            return emptyList();
        }

        User user = userOptional.get();
        excludeDisposalIds = ofNullable(excludeDisposalIds).filter(list -> list.size() > 0).orElse(singletonList(0L));
        BeginEnd quarterRange = getCurrentQuarterMonthsRangeInstant(convertLocalDateToBeginOfDayInstant(baseDate));
        List<User> allSubject = findAllSubject(user);
        Instant begin = quarterRange.getBegin().minus(1095, DAYS);
        List<NhiMetricRawVM> nhiMetricRawVMList = nhiExtendDisposalRepository.findMetricRaw(
            begin,
            quarterRange.getEnd(),
            excludeDisposalIds
        );

        return allSubject.parallelStream()
            .map(subject -> buildMetricL(baseDate, quarterRange, allSubject, subject, nhiMetricRawVMList))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private MetricLVM buildMetricL(LocalDate baseDate, BeginEnd quarterRange, List<User> allSubject, User subject, List<NhiMetricRawVM> source) {
        Collector collector = new Collector(source);
        Source<NhiMetricRawVM, NhiMetricRawVM> subjectSource = subject.getId().equals(Long.MIN_VALUE) ? new ClinicSource() : new DoctorSource(subject.getId());
        collector.apply(subjectSource);

        if (!collector.isSourceExist(subjectSource.outputKey()) || collector.retrieveSource(subjectSource.outputKey()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = subjectSource.getClass().isAssignableFrom(ClinicSource.class) ? CLINIC : MetricSubjectType.DOCTOR;
        Source<NhiMetricRawVM, NhiMetricRawVM> threeYearNearSource = new ThreeYearNearSource(baseDate);
        Source<NhiMetricRawVM, NhiMetricRawVM> twoYearNearSource = new TwoYearNearSource(baseDate);
        Source<NhiMetricRawVM, NhiMetricRawVM> oneYearNearSource = new OneYearNearSource(baseDate);
        Source<NhiMetricRawVM, NhiMetricRawVM> halfYearNearSource = new HalfYearNearSource(baseDate);
        Source<NhiMetricRawVM, NhiMetricRawVM> quarterSource = new QuarterSource(quarterRange);
        Source<NhiMetricRawVM, NhiMetricRawVM> threeMonthNearSource = new ThreeMonthNearSource(baseDate);
        Source<NhiMetricRawVM, NhiMetricRawVM> monthSelectedSource = new MonthSelectedSource(baseDate);
        Source<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> dailyByMonthSelectedSource = new DailyByMonthSelectedSource(baseDate);
        Source<NhiMetricRawVM, Map<NhiSpecialCode, List<NhiMetricRawVM>>> specialCodeMonthSelectedSource = new SpecialCodeMonthSelectedSource();
        Source<NhiMetricRawVM, OdDto> odThreeYearNearSource = new OdThreeYearNearSource();
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odPermanentThreeYearNearByPatientSource = new OdPermanentThreeYearNearByPatientSource();
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odDeciduousThreeYearNearByPatientSource = new OdDeciduousThreeYearNearByPatientSource();
        Source<OdDto, OdDto> odTwoYearNearSource = new OdTwoYearNearSource(baseDate);
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odPermanentTwoYearNearByPatientSource = new OdPermanentTwoYearNearByPatientSource();
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odDeciduousTwoYearNearByPatientSource = new OdDeciduousTwoYearNearByPatientSource();
        Source<OdDto, OdDto> odOneYearNearSource = new OdOneYearNearSource(baseDate);
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odPermanentOneYearNearByPatientSource = new OdPermanentOneYearNearByPatientSource();
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odDeciduousOneYearNearByPatientSource = new OdDeciduousOneYearNearByPatientSource();
        Source<NhiMetricRawVM, OdDto> odQuarterSource = new OdQuarterSource();
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odPermanentQuarterByPatientSource = new OdPermanentQuarterByPatientSource();
        Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odDeciduousQuarterByPatientSource = new OdDeciduousQuarterByPatientSource();
        Source<OdDto, OdDto> odMonthSelectedSource = new OdMonthSelectedSource(baseDate);

        collector
            .apply(threeYearNearSource)
            .apply(twoYearNearSource)
            .apply(oneYearNearSource)
            .apply(halfYearNearSource)
            .apply(quarterSource)
            .apply(threeMonthNearSource)
            .apply(monthSelectedSource)
            .apply(dailyByMonthSelectedSource)
            .apply(specialCodeMonthSelectedSource)
            .apply(odThreeYearNearSource)
            .apply(odPermanentThreeYearNearByPatientSource)
            .apply(odDeciduousThreeYearNearByPatientSource)
            .apply(odTwoYearNearSource)
            .apply(odPermanentTwoYearNearByPatientSource)
            .apply(odDeciduousTwoYearNearByPatientSource)
            .apply(odOneYearNearSource)
            .apply(odPermanentOneYearNearByPatientSource)
            .apply(odDeciduousOneYearNearByPatientSource)
            .apply(odQuarterSource)
            .apply(odPermanentQuarterByPatientSource)
            .apply(odDeciduousQuarterByPatientSource)
            .apply(odMonthSelectedSource);

        BigDecimal metricL1 = new L1Formula(collector, monthSelectedSource).calculate();
        BigDecimal metricL2 = new L2Formula(collector, monthSelectedSource).calculate();
        BigDecimal metricL3 = new L3Formula(collector, monthSelectedSource).calculate();
        BigDecimal metricL4 = new L4Formula(collector, monthSelectedSource).calculate();
        BigDecimal metricL5 = new L5Formula(collector, monthSelectedSource).calculate();
        BigDecimal metricL6 = new L6Formula(collector, monthSelectedSource).calculate();
        BigDecimal metricL7 = new L7Formula(collector, monthSelectedSource).calculate();
        BigDecimal metricL8 = new L8Formula(collector, monthSelectedSource).calculate();
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
        BigDecimal metricL25 = new L25Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousOneYearNearByPatientSource).calculate();
        BigDecimal metricL26 = new L26Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousTwoYearNearByPatientSource).calculate();
        BigDecimal metricL27 = new L27Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousTwoYearNearByPatientSource).calculate();
        BigDecimal metricL28 = new L28Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousTwoYearNearByPatientSource).calculate();
        BigDecimal metricL29 = new L29Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousThreeYearNearByPatientSource).calculate();
        BigDecimal metricL30 = new L30Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousThreeYearNearByPatientSource).calculate();
        BigDecimal metricL31 = new L31Formula(collector, odPermanentQuarterByPatientSource, odPermanentOneYearNearByPatientSource).calculate();
        BigDecimal metricL32 = new L32Formula(collector, odPermanentQuarterByPatientSource, odPermanentTwoYearNearByPatientSource).calculate();
        BigDecimal metricL33 = new L33Formula(collector, odPermanentQuarterByPatientSource, odPermanentTwoYearNearByPatientSource).calculate();
        BigDecimal metricL34 = new L34Formula(collector, odPermanentQuarterByPatientSource, odPermanentTwoYearNearByPatientSource).calculate();
        BigDecimal metricL35 = new L35Formula(collector, odPermanentQuarterByPatientSource, odPermanentThreeYearNearByPatientSource).calculate();
        BigDecimal metricL36 = new L36Formula(collector, odPermanentQuarterByPatientSource, odPermanentThreeYearNearByPatientSource).calculate();
        BigDecimal metricL37 = new L37Formula(collector, odPermanentQuarterByPatientSource, odPermanentOneYearNearByPatientSource).calculate();
        BigDecimal metricL38 = new L38Formula(collector, odPermanentQuarterByPatientSource, odPermanentTwoYearNearByPatientSource).calculate();
        BigDecimal metricL39 = new L39Formula(collector, odPermanentQuarterByPatientSource, odPermanentTwoYearNearByPatientSource).calculate();
        BigDecimal metricL40 = new L40Formula(collector, odPermanentQuarterByPatientSource, odPermanentTwoYearNearByPatientSource).calculate();
        BigDecimal metricL41 = new L41Formula(collector, odPermanentQuarterByPatientSource, odPermanentThreeYearNearByPatientSource).calculate();
        BigDecimal metricL42 = new L42Formula(collector, odPermanentQuarterByPatientSource, odPermanentThreeYearNearByPatientSource).calculate();
        BigDecimal metricL43 = new L43Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousOneYearNearByPatientSource).calculate();
        BigDecimal metricL44 = new L44Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousTwoYearNearByPatientSource).calculate();
        BigDecimal metricL45 = new L45Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousTwoYearNearByPatientSource).calculate();
        BigDecimal metricL46 = new L46Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousTwoYearNearByPatientSource).calculate();
        BigDecimal metricL47 = new L47Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousThreeYearNearByPatientSource).calculate();
        BigDecimal metricL48 = new L48Formula(collector, odDeciduousQuarterByPatientSource, odDeciduousThreeYearNearByPatientSource).calculate();
        BigDecimal metricL49 = new L49Formula(collector, odMonthSelectedSource).calculate();
        BigDecimal metricL50 = new L50Formula(collector, odMonthSelectedSource).calculate();
        BigDecimal metricL51 = new L51Formula(collector, odMonthSelectedSource).calculate();
        BigDecimal metricL52 = new L52Formula(collector, odMonthSelectedSource).calculate();
        BigDecimal metricL53 = new L53Formula(collector, odQuarterSource).calculate();
        BigDecimal metricL54 = new L54Formula(collector, odQuarterSource).calculate();
        BigDecimal metricL55 = new L55Formula(collector, odQuarterSource).calculate();
        BigDecimal metricL56 = new L56Formula(collector, odQuarterSource).calculate();
        SpecialTreatmentAnalysisDto specialTreatmentAnalysisDto = new SpecialTreatmentFormula(collector, specialCodeMonthSelectedSource).calculate();
        Map<LocalDate, BigDecimal> dailyPoints = new DailyPointsFormula(collector, dailyByMonthSelectedSource).calculate();
        Map<LocalDate, BigDecimal> dailyPt1 = new DailyPt1Formula(collector, dailyByMonthSelectedSource).calculate();
        Map<LocalDate, BigDecimal> dailyIc3 = new DailyIc3Formula(collector, dailyByMonthSelectedSource).calculate();

        NameValue nameValue = new NameValue();
        List<DoctorSummaryDto> doctorSummaryDtoList = null;
        List<DisposalSummaryDto> disposalSummaryDtoList = null;
        if (metricSubjectType == CLINIC) {
            HighestDoctorDto highestDoctorDto = new L9Formula(collector, monthSelectedSource).calculate();
            allSubject.stream().filter(val -> val.getId().equals(highestDoctorDto.getId())).map(User::getFirstName).findFirst().ifPresent(nameValue::setName);
            nameValue.setValue(highestDoctorDto.getValue());
            doctorSummaryDtoList = new DoctorSummaryFormula(collector, monthSelectedSource).calculate();
        } else {
            HighestPatientDto highestPatientDto = new L10Formula(collector, monthSelectedSource).calculate();
            List<NhiMetricRawVM> list = collector.retrieveSource(monthSelectedSource.outputKey());
            list.stream().filter(vm -> highestPatientDto.getId().equals(vm.getPatientId())).map(NhiMetricRawVM::getPatientName).findAny().ifPresent(nameValue::setName);
            nameValue.setValue(highestPatientDto.getValue());
            disposalSummaryDtoList = new DisposalSummaryFormula(collector, monthSelectedSource).calculate();
        }

        Section5 section5 = new Section5();
        section5.setL1(new MetricData(metricL1));
        section5.setL2(new MetricData(metricL2));
        section5.setL3(new MetricData(metricL3));
        section5.setL4(new MetricData(metricL4));

        Section6 section6 = new Section6();
        section6.setL5(new MetricData(metricL5));
        section6.setL6(new MetricData(metricL6));
        section6.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(dailyPt1.entrySet()));

        Section7 section7 = new Section7();
        section7.setL7(new MetricData(metricL7));
        section7.setL8(new MetricData(metricL8));
        section7.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(dailyIc3.entrySet()));

        Section8 section8 = new Section8();
        if (metricSubjectType == CLINIC) {
            section8.setL9(nameValue);
        } else {
            section8.setL10(nameValue);
        }

        Section9 section9 = new Section9();
        section9.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(dailyPoints.entrySet()));

        Section10 section10 = new Section10();
        section10.setL11(new MetricData(metricL11));
        section10.setL12(new MetricData(metricL12));
        section10.setL13(new MetricData(metricL13));
        section10.setL14(new MetricData(metricL14));
        section10.setL15(new MetricData(metricL15));
        section10.setL16(new MetricData(metricL16));
        section10.setL17(new MetricData(metricL17));
        section10.setL18(new MetricData(metricL18));
        section10.setL19(new MetricData(metricL19));

        Section11 section11 = new Section11();
        section11.setP1(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP1()));
        section11.setP2(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP2()));
        section11.setP3(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP3()));
        section11.setP4(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP4()));
        section11.setP5(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP5()));
        section11.setP6(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP6()));
        section11.setP7(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP7()));
        section11.setP8(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP8()));
        section11.setOther(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getOther()));
        section11.setSummary(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getSummary()));
        section11.setP1p5(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP1p5()));
        section11.setP2p3(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP2p3()));
        section11.setP4p8(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP4p8()));
        section11.setP6p7AndOther(SpecialTreatmentMapper.INSTANCE.mapToVM(specialTreatmentAnalysisDto.getP6p7AndOther()));

        Section12 section12 = new Section12();
        section12.setL20(new MetricData(metricL20));
        section12.setL21(new MetricData(metricL21));
        section12.setL22(new MetricData(metricL22));
        section12.setL23(new MetricData(metricL23));

        Section13 section13 = new Section13();
        section13.setL24(new MetricData(metricL24));

        Section14 section14 = new Section14();
        section14.setL25(new MetricData(metricL25));
        section14.setL26(new MetricData(metricL26));
        section14.setL27(new MetricData(metricL27));
        section14.setL28(new MetricData(metricL28));
        section14.setL29(new MetricData(metricL29));
        section14.setL30(new MetricData(metricL30));
        section14.setL31(new MetricData(metricL31));
        section14.setL32(new MetricData(metricL32));
        section14.setL33(new MetricData(metricL33));
        section14.setL34(new MetricData(metricL34));
        section14.setL35(new MetricData(metricL35));
        section14.setL36(new MetricData(metricL36));

        Section15 section15 = new Section15();
        section15.setL37(new MetricData(metricL37));
        section15.setL38(new MetricData(metricL38));
        section15.setL39(new MetricData(metricL39));
        section15.setL40(new MetricData(metricL40));
        section15.setL41(new MetricData(metricL41));
        section15.setL42(new MetricData(metricL42));
        section15.setL43(new MetricData(metricL43));
        section15.setL44(new MetricData(metricL44));
        section15.setL45(new MetricData(metricL45));
        section15.setL46(new MetricData(metricL46));
        section15.setL47(new MetricData(metricL47));
        section15.setL48(new MetricData(metricL48));

        Section16 section16 = new Section16();
        section16.setL49(new MetricData(metricL49));
        section16.setL50(new MetricData(metricL50));
        section16.setL51(new MetricData(metricL51));
        section16.setL52(new MetricData(metricL52));

        Section17 section17 = new Section17();
        section17.setL53(new MetricData(metricL53));
        section17.setL54(new MetricData(metricL54));
        section17.setL55(new MetricData(metricL55));
        section17.setL56(new MetricData(metricL56));

        Section18 section18 = new Section18();
        section18.setDoctorSummary(doctorSummaryDtoList);
        section18.setDisposalSummary(disposalSummaryDtoList);

        MetricLVM metricLVM = new MetricLVM();
        metricLVM.setType(metricSubjectType.name().toLowerCase());

        if (metricSubjectType == MetricSubjectType.DOCTOR) {
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(subject.getId());
            doctorData.setDoctorName(subject.getFirstName());
            metricLVM.setDoctor(doctorData);
        }

        metricLVM.setSection5(section5);
        metricLVM.setSection6(section6);
        metricLVM.setSection7(section7);
        metricLVM.setSection8(section8);
        metricLVM.setSection9(section9);
        metricLVM.setSection10(section10);
        metricLVM.setSection11(section11);
        metricLVM.setSection12(section12);
        metricLVM.setSection13(section13);
        metricLVM.setSection14(section14);
        metricLVM.setSection15(section15);
        metricLVM.setSection16(section16);
        metricLVM.setSection17(section17);
        metricLVM.setSection18(section18);

        return metricLVM;
    }

    private List<User> findAllSubject(User user) {
        boolean isDoctor = user.getAuthorities().stream().anyMatch(authority -> DOCTOR.equals(authority.getName()));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(authority -> ADMIN.equals(authority.getName()));

        if (!isDoctor && !isAdmin) {
            return emptyList();
        }

        List<User> usersActivated = userRepository.findAllByActivatedIsTrue();
        List<User> doctors = usersActivated.stream()
            .filter(userActivated -> userActivated.getAuthorities().stream().anyMatch(authority -> DOCTOR.equals(authority.getName())))
            .collect(toList());

        if (isDoctor) {
            doctors = doctors.stream().filter(doctor -> doctor.getId().equals(user.getId())).collect(toList());
        } else {
            User clinic = new User();
            clinic.setId(Long.MIN_VALUE);
            doctors.add(clinic);
        }

        return doctors;
    }

}
