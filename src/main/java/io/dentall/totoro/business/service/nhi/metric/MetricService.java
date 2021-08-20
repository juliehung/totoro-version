package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.GiantMetricDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricLDto;
import io.dentall.totoro.business.service.nhi.metric.mapper.SpecialTreatmentMapper;
import io.dentall.totoro.business.service.nhi.metric.mapper.TimeLineDataMapper;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.security.AuthoritiesConstants;
import io.dentall.totoro.service.HolidayService;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CLINIC_ID;
import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.CLINIC;
import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.DOCTOR;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.getHolidayMap;
import static io.dentall.totoro.security.AuthoritiesConstants.ADMIN;
import static io.dentall.totoro.service.util.DateTimeUtil.convertLocalDateToBeginOfDayInstant;
import static io.dentall.totoro.service.util.DateTimeUtil.getCurrentQuarterMonthsRangeInstant;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class MetricService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final HolidayService holidayService;

    private final MetricDashboardService metricDashboardService;

    private final TaipeiDistrictService taipeiDistrictService;

    public MetricService(UserService userService,
                         UserRepository userRepository,
                         NhiExtendDisposalRepository nhiExtendDisposalRepository,
                         HolidayService holidayService, MetricDashboardService metricDashboardService,
                         TaipeiDistrictService taipeiDistrictService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.holidayService = holidayService;
        this.metricDashboardService = metricDashboardService;
        this.taipeiDistrictService = taipeiDistrictService;
    }

    public List<MetricLVM> getDashboardMetric(final LocalDate baseDate, List<Long> excludeDisposalIds) {
        Optional<User> userOptional = this.userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            return emptyList();
        }

        User user = userOptional.get();
        excludeDisposalIds = ofNullable(excludeDisposalIds).filter(list -> list.size() > 0).orElse(singletonList(0L));
        DateTimeUtil.BeginEnd quarterRange = getCurrentQuarterMonthsRangeInstant(convertLocalDateToBeginOfDayInstant(baseDate));
        List<User> subjects = findAllSubject(user);
        Instant begin = quarterRange.getBegin().minus(1095, DAYS);
        List<NhiMetricRawVM> source = nhiExtendDisposalRepository.findMetricRaw(
            begin,
            quarterRange.getEnd(),
            excludeDisposalIds
        );

        List<GiantMetricDto> giantMetricDtoList = metricDashboardService.metric(baseDate, subjects, source);

        return giantMetricDtoList.stream().map(giantDto -> {
            MetricSubjectType metricSubjectType = giantDto.getType();
            MetricLDto metricLDto = giantDto.getMetricLDto();
            Section5 section5 = new Section5();
            section5.setL1(new MetricData(metricLDto.getL1()));
            section5.setL2(new MetricData(metricLDto.getL2()));
            section5.setL3(new MetricData(metricLDto.getL3()));
            section5.setL4(new MetricData(metricLDto.getL4()));

            Section6 section6 = new Section6();
            section6.setL5(new MetricData(metricLDto.getL5()));
            section6.setL6(new MetricData(metricLDto.getL6()));
            section6.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(giantDto.getDailyPt1().entrySet()));

            Section7 section7 = new Section7();
            section7.setL7(new MetricData(metricLDto.getL7()));
            section7.setL8(new MetricData(metricLDto.getL8()));
            section7.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(giantDto.getDailyIc3().entrySet()));

            Section8 section8 = new Section8();
            if (metricSubjectType == CLINIC) {
                section8.setL9(metricLDto.getL9());
            } else {
                section8.setL10(metricLDto.getL10());
            }

            Section9 section9 = new Section9();
            section9.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(giantDto.getDailyPoints().entrySet()));

            Section10 section10 = new Section10();
            section10.setL11(new MetricData(metricLDto.getL11()));
            section10.setL12(new MetricData(metricLDto.getL12()));
            section10.setL13(new MetricData(metricLDto.getL13()));
            section10.setL14(new MetricData(metricLDto.getL14()));
            section10.setL15(new MetricData(metricLDto.getL15()));
            section10.setL16(new MetricData(metricLDto.getL16()));
            section10.setL17(new MetricData(metricLDto.getL17()));
            section10.setL18(new MetricData(metricLDto.getL18()));
            section10.setL19(new MetricData(metricLDto.getL19()));

            Section11 section11 = new Section11();
            section11.setP1(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP1()));
            section11.setP2(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP2()));
            section11.setP3(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP3()));
            section11.setP4(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP4()));
            section11.setP5(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP5()));
            section11.setP6(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP6()));
            section11.setP7(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP7()));
            section11.setP8(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP8()));
            section11.setOther(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getOther()));
            section11.setSummary(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getSummary()));
            section11.setP1p5(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP1p5()));
            section11.setP2p3(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP2p3()));
            section11.setP4p8(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP4p8()));
            section11.setP6p7AndOther(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP6p7AndOther()));

            Section12 section12 = new Section12();
            section12.setL20(new MetricData(metricLDto.getL20()));
            section12.setL21(new MetricData(metricLDto.getL21()));
            section12.setL22(new MetricData(metricLDto.getL22()));
            section12.setL23(new MetricData(metricLDto.getL23()));

            Section13 section13 = new Section13();
            section13.setL24(new MetricData(metricLDto.getL24()));

            Section14 section14 = new Section14();
            section14.setL25(new MetricData(metricLDto.getL25()));
            section14.setL26(new MetricData(metricLDto.getL26()));
            section14.setL27(new MetricData(metricLDto.getL27()));
            section14.setL28(new MetricData(metricLDto.getL28()));
            section14.setL29(new MetricData(metricLDto.getL29()));
            section14.setL30(new MetricData(metricLDto.getL30()));
            section14.setL31(new MetricData(metricLDto.getL31()));
            section14.setL32(new MetricData(metricLDto.getL32()));
            section14.setL33(new MetricData(metricLDto.getL33()));
            section14.setL34(new MetricData(metricLDto.getL34()));
            section14.setL35(new MetricData(metricLDto.getL35()));
            section14.setL36(new MetricData(metricLDto.getL36()));

            Section15 section15 = new Section15();
            section15.setL37(new MetricData(metricLDto.getL37()));
            section15.setL38(new MetricData(metricLDto.getL38()));
            section15.setL39(new MetricData(metricLDto.getL39()));
            section15.setL40(new MetricData(metricLDto.getL40()));
            section15.setL41(new MetricData(metricLDto.getL41()));
            section15.setL42(new MetricData(metricLDto.getL42()));
            section15.setL43(new MetricData(metricLDto.getL43()));
            section15.setL44(new MetricData(metricLDto.getL44()));
            section15.setL45(new MetricData(metricLDto.getL45()));
            section15.setL46(new MetricData(metricLDto.getL46()));
            section15.setL47(new MetricData(metricLDto.getL47()));
            section15.setL48(new MetricData(metricLDto.getL48()));

            Section16 section16 = new Section16();
            section16.setL49(new MetricData(metricLDto.getL49()));
            section16.setL50(new MetricData(metricLDto.getL50()));
            section16.setL51(new MetricData(metricLDto.getL51()));
            section16.setL52(new MetricData(metricLDto.getL52()));

            Section17 section17 = new Section17();
            section17.setL53(new MetricData(metricLDto.getL53()));
            section17.setL54(new MetricData(metricLDto.getL54()));
            section17.setL55(new MetricData(metricLDto.getL55()));
            section17.setL56(new MetricData(metricLDto.getL56()));

            Section18 section18 = new Section18();
            if (metricSubjectType == CLINIC) {
                section18.setCount(giantDto.getDoctorSummary().size());
                section18.setTotal(giantDto.getDoctorSummary().stream().mapToLong(DoctorSummaryDto::getTotal).sum());
                section18.setDoctorSummary(giantDto.getDoctorSummary());
            } else {
                section18.setCount(giantDto.getDisposalSummary().size());
                section18.setTotal(giantDto.getDisposalSummary().stream().mapToLong(DisposalSummaryDto::getTotal).sum());
                section18.setDisposalSummary(giantDto.getDisposalSummary());
            }

            MetricLVM metricLVM = new MetricLVM();
            if (metricSubjectType == DOCTOR) {
                metricLVM.setDoctor(giantDto.getDoctor());
            }
            metricLVM.setType(metricSubjectType.name().toLowerCase());
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
        }).collect(Collectors.toList());
    }

    public List<GiantMetricDto> getTaipeiDistrictMetric(final LocalDate baseDate, List<Long> excludeDisposalIds, List<Long> doctorIds) {
        Optional<User> userOptional = this.userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            return emptyList();
        }

        doctorIds = Optional.ofNullable(doctorIds).orElse(emptyList());
        User user = userOptional.get();
        excludeDisposalIds = ofNullable(excludeDisposalIds).filter(list -> list.size() > 0).orElse(singletonList(0L));
        List<User> subjects = doctorIds.size() == 0 ? findAllSubject(user) : findSpecificSubject(user, doctorIds);
        DateTimeUtil.BeginEnd quarterRange = getCurrentQuarterMonthsRangeInstant(convertLocalDateToBeginOfDayInstant(baseDate));
        Instant begin = quarterRange.getBegin().minus(1095, DAYS);
        List<NhiMetricRawVM> source = nhiExtendDisposalRepository.findMetricRaw(
            begin,
            quarterRange.getEnd(),
            excludeDisposalIds
        );
        int baseYear = baseDate.getYear();
        Map<LocalDate, Optional<Holiday>> holidayMap = getHolidayMap(holidayService, baseYear, baseYear - 1, baseYear - 2, baseYear - 3);

        return taipeiDistrictService.metric(baseDate, subjects, source, holidayMap);
    }

    private List<User> findSpecificSubject(User user, List<Long> subjectIds) {
        List<User> userList = findAllSubject(user);
        return subjectIds.size() == 0 ?
            userList : userList.stream().filter(s -> !s.getId().equals(CLINIC_ID)).filter(s -> subjectIds.contains(s.getId())).collect(toList());
    }

    private List<User> findAllSubject(User user) {
        boolean isDoctor = user.getAuthorities().stream().anyMatch(authority -> AuthoritiesConstants.DOCTOR.equals(authority.getName()));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(authority -> ADMIN.equals(authority.getName()));

        if (!isDoctor && !isAdmin) {
            return emptyList();
        }

        List<User> usersActivated = userRepository.findAllByActivatedIsTrue();
        List<User> doctors = usersActivated.stream()
            .filter(userActivated -> userActivated.getAuthorities().stream().anyMatch(authority -> AuthoritiesConstants.DOCTOR.equals(authority.getName())))
            .collect(toList());

        if (isDoctor) {
            doctors = doctors.stream().filter(doctor -> doctor.getId().equals(user.getId())).collect(toList());
        } else {
            User clinic = new User();
            clinic.setId(CLINIC_ID);
            doctors.add(clinic);
        }

        return doctors;
    }


}
