package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.code.NhiCodeHashSet;
import io.dentall.totoro.business.service.nhi.metric.dto.*;
import io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper;
import io.dentall.totoro.business.service.nhi.metric.mapper.SpecialTreatmentMapper;
import io.dentall.totoro.business.service.nhi.metric.mapper.TimeLineDataMapper;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.service.nhi.metric.vm.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.HolidayService;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CLINIC_ID;
import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.clinic;
import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.doctor;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.getHolidayMap;
import static io.dentall.totoro.business.service.nhi.util.ToothUtil.splitA74;
import static io.dentall.totoro.security.AuthoritiesConstants.ADMIN;
import static io.dentall.totoro.security.AuthoritiesConstants.DOCTOR;
import static io.dentall.totoro.service.util.DateTimeUtil.convertLocalDateToBeginOfDayInstant;
import static io.dentall.totoro.service.util.DateTimeUtil.getCurrentQuarterMonthsRangeInstant;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class MetricService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final UserService userService;

    private final UserRepository userRepository;

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final HolidayService holidayService;

    private final MetricDashboardService metricDashboardService;

    private final KaoPingDistrictReductionService kaoPingDistrictReductionService;

    public MetricService(UserService userService,
                         UserRepository userRepository,
                         NhiExtendDisposalRepository nhiExtendDisposalRepository,
                         HolidayService holidayService, MetricDashboardService metricDashboardService,
                         KaoPingDistrictReductionService kaoPingDistrictReductionService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.holidayService = holidayService;
        this.metricDashboardService = metricDashboardService;
        this.kaoPingDistrictReductionService = kaoPingDistrictReductionService;
    }

    public List<MetricLVM> getDashboardMetric(LocalDate baseDate, List<Long> excludeDisposalIds) {
        Optional<User> userOptional = this.userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            return emptyList();
        }

        User user = userOptional.get();
        excludeDisposalIds = ofNullable(excludeDisposalIds).filter(list -> list.size() > 0).orElse(singletonList(0L));
        DateTimeUtil.BeginEnd quarterRange = getCurrentQuarterMonthsRangeInstant(convertLocalDateToBeginOfDayInstant(baseDate));
        List<MetricSubject> subjects = findAllSubject(user);
        Instant begin = quarterRange.getBegin().minus(1095, DAYS); // 季 + 三年(1095)
        List<MetricDisposal> source = fetchSource(begin, quarterRange.getEnd(), excludeDisposalIds);
        int baseYear = baseDate.getYear();
        Map<LocalDate, Optional<Holiday>> holidayMap = getHolidayMap(holidayService, baseYear, baseYear - 1, baseYear - 2, baseYear - 3);

        List<DashboardDto> dashboardDtoList = metricDashboardService.metric(baseDate, subjects, source, holidayMap);

        return dashboardDtoList.stream().map(dto -> {
            MetricSubjectType metricSubjectType = dto.getType();

            Section5 section5 = new Section5();
            section5.setL1(new MetricData(dto.getL1()));
            section5.setL2(new MetricData(dto.getL2()));
            section5.setL3(new MetricData(dto.getL3()));
            section5.setL4(new MetricData(dto.getL4()));

            Section6 section6 = new Section6();
            section6.setL5(new MetricData(dto.getL5()));
            section6.setL6(new MetricData(dto.getL6()));
            section6.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(dto.getDailyPt1()));

            Section7 section7 = new Section7();
            section7.setL7(new MetricData(dto.getL7()));
            section7.setL8(new MetricData(dto.getL8()));
            section7.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(dto.getDailyIc3()));

            Section8 section8 = new Section8();
            if (metricSubjectType == clinic) {
                section8.setL9(dto.getL9());
            } else {
                section8.setL10(dto.getL10());
            }

            Section9 section9 = new Section9();
            section9.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(dto.getDailyPoints()));

            Section10 section10 = new Section10();
            section10.setL11(new MetricData(dto.getL11()));
            section10.setL12(new MetricData(dto.getL12()));
            section10.setL13(new MetricData(dto.getL13()));
            section10.setL14(new MetricData(dto.getL14()));
            section10.setL15(new MetricData(dto.getL15()));
            section10.setL16(new MetricData(dto.getL16()));
            section10.setL17(new MetricData(dto.getL17()));
            section10.setL18(new MetricData(dto.getL18()));
            section10.setL19(new MetricData(dto.getL19()));

            Section11 section11 = new Section11();
            section11.setP1(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP1()));
            section11.setP2(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP2()));
            section11.setP3(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP3()));
            section11.setP4(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP4()));
            section11.setP5(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP5()));
            section11.setP6(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP6()));
            section11.setP7(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP7()));
            section11.setP8(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP8()));
            section11.setOther(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getOther()));
            section11.setSummary(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getSummary()));
            section11.setP1p5(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP1p5()));
            section11.setP2p3(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP2p3()));
            section11.setP4p8(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP4p8()));
            section11.setP6p7AndOther(SpecialTreatmentMapper.INSTANCE.mapToVM(dto.getSpecialTreatmentAnalysisDto().getP6p7AndOther()));

            Section12 section12 = new Section12();
            section12.setL20(new MetricData(dto.getL20()));
            section12.setL21(new MetricData(dto.getL21()));
            section12.setL22(new MetricData(dto.getL22()));
            section12.setL23(new MetricData(dto.getL23()));

            Section13 section13 = new Section13();
            section13.setL24(new MetricData(dto.getL24()));

            Section14 section14 = new Section14();
            section14.setL25(new MetricData(dto.getL25()));
            section14.setL26(new MetricData(dto.getL26()));
            section14.setL27(new MetricData(dto.getL27()));
            section14.setL28(new MetricData(dto.getL28()));
            section14.setL29(new MetricData(dto.getL29()));
            section14.setL30(new MetricData(dto.getL30()));
            section14.setL31(new MetricData(dto.getL31()));
            section14.setL32(new MetricData(dto.getL32()));
            section14.setL33(new MetricData(dto.getL33()));
            section14.setL34(new MetricData(dto.getL34()));
            section14.setL35(new MetricData(dto.getL35()));
            section14.setL36(new MetricData(dto.getL36()));

            Section15 section15 = new Section15();
            section15.setL37(new MetricData(dto.getL37()));
            section15.setL38(new MetricData(dto.getL38()));
            section15.setL39(new MetricData(dto.getL39()));
            section15.setL40(new MetricData(dto.getL40()));
            section15.setL41(new MetricData(dto.getL41()));
            section15.setL42(new MetricData(dto.getL42()));
            section15.setL43(new MetricData(dto.getL43()));
            section15.setL44(new MetricData(dto.getL44()));
            section15.setL45(new MetricData(dto.getL45()));
            section15.setL46(new MetricData(dto.getL46()));
            section15.setL47(new MetricData(dto.getL47()));
            section15.setL48(new MetricData(dto.getL48()));

            Section16 section16 = new Section16();
            section16.setL49(new MetricData(dto.getL49()));
            section16.setL50(new MetricData(dto.getL50()));
            section16.setL51(new MetricData(dto.getL51()));
            section16.setL52(new MetricData(dto.getL52()));

            Section17 section17 = new Section17();
            section17.setL53(new MetricData(dto.getL53()));
            section17.setL54(new MetricData(dto.getL54()));
            section17.setL55(new MetricData(dto.getL55()));
            section17.setL56(new MetricData(dto.getL56()));

            Section18 section18 = new Section18();
            if (metricSubjectType == clinic) {
                section18.setCount(dto.getDoctorSummary().size());
                section18.setTotal(dto.getDoctorSummary().stream().mapToLong(DoctorSummaryDto::getTotal).sum());
                section18.setDoctorSummary(dto.getDoctorSummary());
            } else {
                section18.setCount(dto.getDisposalSummary().size());
                section18.setTotal(dto.getDisposalSummary().stream().mapToLong(DisposalSummaryDto::getTotal).sum());
                section18.setDisposalSummary(dto.getDisposalSummary());
            }

            MetricLVM metricLVM = new MetricLVM();
            if (metricSubjectType == doctor) {
                metricLVM.setDoctor(dto.getDoctor());
            }
            metricLVM.setType(metricSubjectType.name());
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
        }).collect(toList());
    }

    public CompositeDistrictDto getCompositeDistrictMetric(
        LocalDate baseDate, List<Long> excludeDisposalIds,
        List<Long> doctorIds,
        List<Class<? extends DistrictService>> metricServiceClass,
        User user
    ) {
        if (user == null) {
            return new CompositeDistrictDto();
        }

        doctorIds = ofNullable(doctorIds).orElse(emptyList());
        excludeDisposalIds = ofNullable(excludeDisposalIds).filter(list -> list.size() > 0).orElse(singletonList(0L));
        List<MetricSubject> subjects = doctorIds.size() == 0 ? findAllSubject(user) : findSpecificSubject(user, doctorIds);
        DateTimeUtil.BeginEnd quarterRange = getCurrentQuarterMonthsRangeInstant(convertLocalDateToBeginOfDayInstant(baseDate));
        Instant begin = quarterRange.getBegin().minus(1095, DAYS); // 季 + 三年(1095)
        List<MetricDisposal> source = fetchSource(begin, quarterRange.getEnd(), excludeDisposalIds);
        int baseYear = baseDate.getYear();
        Map<LocalDate, Optional<Holiday>> holidayMap = getHolidayMap(holidayService, baseYear, baseYear - 1, baseYear - 2, baseYear - 3);

        Map<Class<?>, List<DistrictDto>> districtMetricMap =
            subjects.parallelStream()
                .map(subject -> new MetricConfig(subject, baseDate, source).applyHolidayMap(holidayMap))
                .map(metricConfig -> runMetricService(metricConfig, metricServiceClass))
                .flatMap(Collection::stream)
                .map(Optional::get)
                .collect(groupingBy(Object::getClass));

        CompositeDistrictDto compositeDistrictDto = applyToCompositeDistrictDto(districtMetricMap);

        if (metricServiceClass.contains(KaoPingDistrictReductionService.class)) {
            BigDecimal metricJ1h2 = kaoPingDistrictReductionService.getJ1h2Metric(baseDate, holidayMap, source);

            compositeDistrictDto.getKaoPingDistrictReductionDtoList()
                .forEach(dto -> dto.setJ1h2(metricJ1h2));
        }

        return compositeDistrictDto;
    }

    private List<Optional<? extends DistrictDto>> runMetricService(MetricConfig metricConfig, List<Class<? extends DistrictService>> metricServiceClass) {
        List<MetricDisposal> subSource = metricConfig.retrieveSource(metricConfig.getSubjectDisposalSource().key());
        return metricServiceClass.parallelStream()
            .map(clz -> applicationContext.getBean(clz))
            .map(service -> service.metric(metricConfig.getBaseDate(), metricConfig.getMetricSubject(), subSource, metricConfig.getHolidayMap()))
            .filter(Optional::isPresent)
            .collect(toList());
    }

    private CompositeDistrictDto applyToCompositeDistrictDto(Map<Class<?>, List<DistrictDto>> districtMetricMap) {
        CompositeDistrictDto compositeDistrictDto = new CompositeDistrictDto();
        compositeDistrictDto.setTaipeiDistrictDtoList(cast(districtMetricMap, TaipeiDistrictDto.class));
        compositeDistrictDto.setNorthDistrictDtoList(cast(districtMetricMap, NorthDistrictDto.class));
        compositeDistrictDto.setMiddleDistrictDtoList(cast(districtMetricMap, MiddleDistrictDto.class));
        compositeDistrictDto.setSouthDistrictDtoList(cast(districtMetricMap, SouthDistrictDto.class));
        compositeDistrictDto.setEastDistrictDtoList(cast(districtMetricMap, EastDistrictDto.class));
        compositeDistrictDto.setKaoPingDistrictRegularDtoList(cast(districtMetricMap, KaoPingDistrictRegularDto.class));
        compositeDistrictDto.setKaoPingDistrictReductionDtoList(cast(districtMetricMap, KaoPingDistrictReductionDto.class));
        return compositeDistrictDto;
    }

    private <T> List<T> cast(Map<Class<?>, List<DistrictDto>> districtMetricMap, Class<T> clz) {
        return districtMetricMap.getOrDefault(clz, new ArrayList<>()).stream().map(clz::cast).collect(toList());
    }

    private List<MetricDisposal> fetchSource(Instant begin, Instant end, List<Long> excludeDisposalIds) {
        List<NhiMetricRawVM> source = nhiExtendDisposalRepository.findMetricRaw(
            begin,
            end,
            excludeDisposalIds
        );

        NhiCodeHashSet calculateByTooth = MetricConstants.calculateByTooth;

        Collection<MetricDisposal> disposals = source.stream().parallel()
            .map(NhiMetricRawMapper.INSTANCE::mapToMetricTreatment)
            .reduce(new ConcurrentHashMap<Long, MetricDisposal>(source.size() / 2), (map, treatment) -> {
                    MetricDisposal disposal = ofNullable(map.get(treatment.getDisposalId())).orElse(NhiMetricRawMapper.INSTANCE.mapToMetricDisposal(treatment));
                    if (nonNull(treatment.getTreatmentProcedureCode())) {
                        if (calculateByTooth.contains(treatment.getTreatmentProcedureCode())) {
                            List<String> teeth = splitA74(treatment.getTreatmentProcedureTooth());
                            teeth.forEach(tooth -> disposal.addTooth(new MetricTooth(treatment, tooth)));
                        } else {
                            disposal.addTooth(new MetricTooth(treatment, ""));
                        }
                    }
                    map.putIfAbsent(disposal.getDisposalId(), disposal);
                    return map;
                },
                (map1, map2) -> {
                    map1.putAll(map2);
                    return map1;
                })
            .values();

        disposals.stream().parallel().forEach(disposal -> {
            if (disposal.getToothList().size() == 0) {
                disposal.setToothList(emptyList());
            }
        });

        return new ArrayList<>(disposals);
    }

    private List<MetricSubject> findSpecificSubject(User user, List<Long> subjectIds) {
        List<MetricSubject> userList = findAllSubject(user);
        return subjectIds.size() == 0 ?
            userList : userList.stream().filter(s -> !s.getId().equals(CLINIC_ID)).filter(s -> subjectIds.contains(s.getId())).collect(toList());
    }

    private List<MetricSubject> findAllSubject(User user) {
        boolean isDoctor = user.getAuthorities().stream().anyMatch(authority -> DOCTOR.equals(authority.getName()));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(authority -> ADMIN.equals(authority.getName()));

        if (!isDoctor && !isAdmin) {
            return emptyList();
        }

        List<User> usersActivated = userRepository.findAllByActivatedIsTrue();
        List<MetricSubject> doctors = usersActivated.stream()
            .filter(userActivated -> userActivated.getAuthorities().stream().anyMatch(authority -> DOCTOR.equals(authority.getName())))
            .map(DoctorSubject::new)
            .collect(toList());

        if (isDoctor) {
            doctors = doctors.stream().filter(doctor -> doctor.getId().equals(user.getId())).collect(toList());
        } else {
            doctors.add(MetricConstants.CLINIC);
        }

        return doctors;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
