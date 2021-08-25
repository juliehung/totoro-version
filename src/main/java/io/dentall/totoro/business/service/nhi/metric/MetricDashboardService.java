package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.*;
import io.dentall.totoro.business.service.nhi.metric.formula.*;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.SourceKey;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;
import io.dentall.totoro.business.service.nhi.metric.vm.NameValue;
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

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.CLINIC;
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
public class MetricDashboardService {

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    public MetricDashboardService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        UserService userService, UserRepository userRepository) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public List<GiantMetricDto> metric(final LocalDate baseDate, List<Long> excludeDisposalIds) {
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
            .map(subject -> buildMetric(baseDate, allSubject, subject, nhiMetricRawVMList))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private GiantMetricDto buildMetric(LocalDate baseDate, List<User> allSubject, User subject, List<NhiMetricRawVM> source) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source);

        if (!metricConfig.isSourceExist(metricConfig.getSubjectSource().key()) || metricConfig.retrieveSource(metricConfig.getSubjectSource().key()).size() == 0) {
            return null;
        }

        MetricSubjectType metricSubjectType = metricConfig.getSubjectType();
        BigDecimal metricL1 = new L1Formula(metricConfig).calculate();
        BigDecimal metricL2 = new L2Formula(metricConfig).calculate();
        BigDecimal metricL3 = new L3Formula(metricConfig).calculate();
        BigDecimal metricL4 = new L4Formula(metricConfig).calculate();
        BigDecimal metricL5 = new L5Formula(metricConfig).calculate();
        BigDecimal metricL6 = new L6Formula(metricConfig).calculate();
        BigDecimal metricL7 = new L7Formula(metricConfig).calculate();
        BigDecimal metricL8 = new L8Formula(metricConfig).calculate();
        BigDecimal metricL11 = new L11Formula(metricConfig).calculate();
        BigDecimal metricL12 = new L12Formula(metricConfig).calculate();
        BigDecimal metricL13 = new L13Formula(metricConfig).calculate();
        BigDecimal metricL14 = new L14Formula(metricConfig).calculate();
        BigDecimal metricL15 = new L15Formula(metricConfig).calculate();
        BigDecimal metricL16 = new L16Formula(metricConfig).calculate();
        BigDecimal metricL17 = new L17Formula(metricConfig).calculate();
        BigDecimal metricL18 = new L18Formula(metricConfig).calculate();
        BigDecimal metricL19 = new L19Formula(metricConfig).calculate();
        BigDecimal metricL20 = new L20Formula(metricConfig).calculate();
        BigDecimal metricL21 = new L21Formula(metricConfig).calculate();
        BigDecimal metricL22 = new L22Formula(metricConfig).calculate();
        BigDecimal metricL23 = new L23Formula(metricConfig).calculate();
        BigDecimal metricL24 = new L24Formula(metricConfig).calculate();
        BigDecimal metricL25 = new L25Formula(metricConfig).calculate();
        BigDecimal metricL26 = new L26Formula(metricConfig).calculate();
        BigDecimal metricL27 = new L27Formula(metricConfig).calculate();
        BigDecimal metricL28 = new L28Formula(metricConfig).calculate();
        BigDecimal metricL29 = new L29Formula(metricConfig).calculate();
        BigDecimal metricL30 = new L30Formula(metricConfig).calculate();
        BigDecimal metricL31 = new L31Formula(metricConfig).calculate();
        BigDecimal metricL32 = new L32Formula(metricConfig).calculate();
        BigDecimal metricL33 = new L33Formula(metricConfig).calculate();
        BigDecimal metricL34 = new L34Formula(metricConfig).calculate();
        BigDecimal metricL35 = new L35Formula(metricConfig).calculate();
        BigDecimal metricL36 = new L36Formula(metricConfig).calculate();
        BigDecimal metricL37 = new L37Formula(metricConfig).calculate();
        BigDecimal metricL38 = new L38Formula(metricConfig).calculate();
        BigDecimal metricL39 = new L39Formula(metricConfig).calculate();
        BigDecimal metricL40 = new L40Formula(metricConfig).calculate();
        BigDecimal metricL41 = new L41Formula(metricConfig).calculate();
        BigDecimal metricL42 = new L42Formula(metricConfig).calculate();
        BigDecimal metricL43 = new L43Formula(metricConfig).calculate();
        BigDecimal metricL44 = new L44Formula(metricConfig).calculate();
        BigDecimal metricL45 = new L45Formula(metricConfig).calculate();
        BigDecimal metricL46 = new L46Formula(metricConfig).calculate();
        BigDecimal metricL47 = new L47Formula(metricConfig).calculate();
        BigDecimal metricL48 = new L48Formula(metricConfig).calculate();
        BigDecimal metricL49 = new L49Formula(metricConfig).calculate();
        BigDecimal metricL50 = new L50Formula(metricConfig).calculate();
        BigDecimal metricL51 = new L51Formula(metricConfig).calculate();
        BigDecimal metricL52 = new L52Formula(metricConfig).calculate();
        BigDecimal metricL53 = new L53Formula(metricConfig).calculate();
        BigDecimal metricL54 = new L54Formula(metricConfig).calculate();
        BigDecimal metricL55 = new L55Formula(metricConfig).calculate();
        BigDecimal metricL56 = new L56Formula(metricConfig).calculate();
        SpecialTreatmentAnalysisDto specialTreatmentAnalysisDto = new SpecialTreatmentFormula(metricConfig).calculate();
        Map<LocalDate, BigDecimal> dailyPoints = new DailyPointsFormula(metricConfig).calculate();
        Map<LocalDate, BigDecimal> dailyPt1 = new DailyPt1Formula(metricConfig).calculate();
        Map<LocalDate, BigDecimal> dailyIc3 = new DailyIc3Formula(metricConfig).calculate();

        NameValue nameValue = new NameValue();
        List<DoctorSummaryDto> doctorSummaryDtoList = null;
        List<DisposalSummaryDto> disposalSummaryDtoList = null;
        if (metricSubjectType == CLINIC) {
            HighestDoctorDto highestDoctorDto = new L9Formula(metricConfig).calculate();
            allSubject.stream().filter(val -> val.getId().equals(highestDoctorDto.getId())).map(User::getFirstName).findFirst().ifPresent(nameValue::setName);
            nameValue.setValue(highestDoctorDto.getValue());
            doctorSummaryDtoList = new DoctorSummaryFormula(metricConfig).calculate();
        } else {
            HighestPatientDto highestPatientDto = new L10Formula(metricConfig).calculate();
            List<NhiMetricRawVM> list = metricConfig.retrieveSource(new SourceKey(new MonthSelectedSource(metricConfig)));
            list.stream().filter(vm -> highestPatientDto.getId().equals(vm.getPatientId())).map(NhiMetricRawVM::getPatientName).findAny().ifPresent(nameValue::setName);
            nameValue.setValue(highestPatientDto.getValue());
            disposalSummaryDtoList = new DisposalSummaryFormula(metricConfig).calculate();
        }

        MetricLDto metricLDto = new MetricLDto();
        metricLDto.setL1(metricL1);
        metricLDto.setL2(metricL2);
        metricLDto.setL3(metricL3);
        metricLDto.setL4(metricL4);
        metricLDto.setL5(metricL5);
        metricLDto.setL6(metricL6);
        metricLDto.setL7(metricL7);
        metricLDto.setL8(metricL8);
        metricLDto.setL11(metricL11);
        metricLDto.setL12(metricL12);
        metricLDto.setL13(metricL13);
        metricLDto.setL14(metricL14);
        metricLDto.setL15(metricL15);
        metricLDto.setL16(metricL16);
        metricLDto.setL17(metricL17);
        metricLDto.setL18(metricL18);
        metricLDto.setL19(metricL19);
        metricLDto.setL20(metricL20);
        metricLDto.setL21(metricL21);
        metricLDto.setL22(metricL22);
        metricLDto.setL23(metricL23);
        metricLDto.setL24(metricL24);
        metricLDto.setL25(metricL25);
        metricLDto.setL26(metricL26);
        metricLDto.setL27(metricL27);
        metricLDto.setL28(metricL28);
        metricLDto.setL29(metricL29);
        metricLDto.setL30(metricL30);
        metricLDto.setL31(metricL31);
        metricLDto.setL32(metricL32);
        metricLDto.setL33(metricL33);
        metricLDto.setL34(metricL34);
        metricLDto.setL35(metricL35);
        metricLDto.setL36(metricL36);
        metricLDto.setL37(metricL37);
        metricLDto.setL38(metricL38);
        metricLDto.setL39(metricL39);
        metricLDto.setL40(metricL40);
        metricLDto.setL41(metricL41);
        metricLDto.setL42(metricL42);
        metricLDto.setL43(metricL43);
        metricLDto.setL44(metricL44);
        metricLDto.setL45(metricL45);
        metricLDto.setL46(metricL46);
        metricLDto.setL47(metricL47);
        metricLDto.setL48(metricL48);
        metricLDto.setL49(metricL49);
        metricLDto.setL50(metricL50);
        metricLDto.setL51(metricL51);
        metricLDto.setL52(metricL52);
        metricLDto.setL53(metricL53);
        metricLDto.setL54(metricL54);
        metricLDto.setL55(metricL55);
        metricLDto.setL56(metricL56);

        GiantMetricDto giantMetricDto = new GiantMetricDto();
        giantMetricDto.setType(metricSubjectType);
        giantMetricDto.setMetricLDto(metricLDto);
        giantMetricDto.setDailyIc3(dailyIc3);
        giantMetricDto.setDailyPt1(dailyPt1);
        giantMetricDto.setDailyPoints(dailyPoints);
        giantMetricDto.setSpecialTreatmentAnalysisDto(specialTreatmentAnalysisDto);

        if (metricSubjectType == CLINIC) {
            metricLDto.setL9(nameValue);
            giantMetricDto.setDoctorSummary(doctorSummaryDtoList);
        } else {
            metricLDto.setL10(nameValue);
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            giantMetricDto.setDoctor(doctorData);
            giantMetricDto.setDisposalSummary(disposalSummaryDtoList);
        }

        return giantMetricDto;
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
