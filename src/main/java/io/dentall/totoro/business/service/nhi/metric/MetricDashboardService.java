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

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.CLINIC;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class MetricDashboardService {

    public List<DashboardDto> metric(final LocalDate baseDate, List<User> subjects, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return subjects.parallelStream()
            .map(subject -> buildMetric(baseDate, subjects, subject, source, holidayMap))
            .filter(Objects::nonNull)
            .collect(toList());
    }

    private DashboardDto buildMetric(LocalDate baseDate, List<User> allSubject, User subject, List<NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap) {
        MetricConfig metricConfig = new MetricConfig(subject, baseDate, source);
        metricConfig.applyHolidayMap(holidayMap);

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

        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setType(metricSubjectType);
        dashboardDto.setL1(metricL1);
        dashboardDto.setL2(metricL2);
        dashboardDto.setL3(metricL3);
        dashboardDto.setL4(metricL4);
        dashboardDto.setL5(metricL5);
        dashboardDto.setL6(metricL6);
        dashboardDto.setL7(metricL7);
        dashboardDto.setL8(metricL8);
        dashboardDto.setL11(metricL11);
        dashboardDto.setL12(metricL12);
        dashboardDto.setL13(metricL13);
        dashboardDto.setL14(metricL14);
        dashboardDto.setL15(metricL15);
        dashboardDto.setL16(metricL16);
        dashboardDto.setL17(metricL17);
        dashboardDto.setL18(metricL18);
        dashboardDto.setL19(metricL19);
        dashboardDto.setL20(metricL20);
        dashboardDto.setL21(metricL21);
        dashboardDto.setL22(metricL22);
        dashboardDto.setL23(metricL23);
        dashboardDto.setL24(metricL24);
        dashboardDto.setL25(metricL25);
        dashboardDto.setL26(metricL26);
        dashboardDto.setL27(metricL27);
        dashboardDto.setL28(metricL28);
        dashboardDto.setL29(metricL29);
        dashboardDto.setL30(metricL30);
        dashboardDto.setL31(metricL31);
        dashboardDto.setL32(metricL32);
        dashboardDto.setL33(metricL33);
        dashboardDto.setL34(metricL34);
        dashboardDto.setL35(metricL35);
        dashboardDto.setL36(metricL36);
        dashboardDto.setL37(metricL37);
        dashboardDto.setL38(metricL38);
        dashboardDto.setL39(metricL39);
        dashboardDto.setL40(metricL40);
        dashboardDto.setL41(metricL41);
        dashboardDto.setL42(metricL42);
        dashboardDto.setL43(metricL43);
        dashboardDto.setL44(metricL44);
        dashboardDto.setL45(metricL45);
        dashboardDto.setL46(metricL46);
        dashboardDto.setL47(metricL47);
        dashboardDto.setL48(metricL48);
        dashboardDto.setL49(metricL49);
        dashboardDto.setL50(metricL50);
        dashboardDto.setL51(metricL51);
        dashboardDto.setL52(metricL52);
        dashboardDto.setL53(metricL53);
        dashboardDto.setL54(metricL54);
        dashboardDto.setL55(metricL55);
        dashboardDto.setL56(metricL56);
        dashboardDto.setDailyIc3(dailyIc3);
        dashboardDto.setDailyPt1(dailyPt1);
        dashboardDto.setDailyPoints(dailyPoints);
        dashboardDto.setSpecialTreatmentAnalysisDto(specialTreatmentAnalysisDto);

        if (metricSubjectType == CLINIC) {
            dashboardDto.setL9(nameValue);
            dashboardDto.setDoctorSummary(doctorSummaryDtoList);
        } else {
            dashboardDto.setL10(nameValue);
            DoctorData doctorData = new DoctorData();
            doctorData.setDoctorId(metricConfig.getSubject().getId());
            doctorData.setDoctorName(metricConfig.getSubject().getFirstName());
            dashboardDto.setDoctor(doctorData);
            dashboardDto.setDisposalSummary(disposalSummaryDtoList);
        }

        return dashboardDto;
    }

}
