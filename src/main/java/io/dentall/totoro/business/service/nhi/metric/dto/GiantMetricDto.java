package io.dentall.totoro.business.service.nhi.metric.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;
import io.dentall.totoro.business.service.nhi.metric.vm.SpecialTreatmentItemVM;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class GiantMetricDto {

    private MetricSubjectType type;

    private DoctorData doctor;

    private MetricFDto metricFDto;

    private MetricLDto metricLDto;

    private List<DoctorSummaryDto> doctorSummary;

    private List<DisposalSummaryDto> disposalSummary;

    private SpecialTreatmentAnalysisDto specialTreatmentAnalysisDto;

    private Map<LocalDate, BigDecimal> dailyPoints;

    private Map<LocalDate, BigDecimal> dailyPt1;

    private Map<LocalDate, BigDecimal> dailyIc3;

    public MetricSubjectType getType() {
        return type;
    }

    public void setType(MetricSubjectType type) {
        this.type = type;
    }

    public DoctorData getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorData doctor) {
        this.doctor = doctor;
    }

    public MetricFDto getMetricFDto() {
        return metricFDto;
    }

    public void setMetricFDto(MetricFDto metricFDto) {
        this.metricFDto = metricFDto;
    }

    public MetricLDto getMetricLDto() {
        return metricLDto;
    }

    public void setMetricLDto(MetricLDto metricLDto) {
        this.metricLDto = metricLDto;
    }

    public List<DoctorSummaryDto> getDoctorSummary() {
        return doctorSummary;
    }

    public void setDoctorSummary(List<DoctorSummaryDto> doctorSummary) {
        this.doctorSummary = doctorSummary;
    }

    public List<DisposalSummaryDto> getDisposalSummary() {
        return disposalSummary;
    }

    public void setDisposalSummary(List<DisposalSummaryDto> disposalSummary) {
        this.disposalSummary = disposalSummary;
    }

    public SpecialTreatmentAnalysisDto getSpecialTreatmentAnalysisDto() {
        return specialTreatmentAnalysisDto;
    }

    public void setSpecialTreatmentAnalysisDto(SpecialTreatmentAnalysisDto specialTreatmentAnalysisDto) {
        this.specialTreatmentAnalysisDto = specialTreatmentAnalysisDto;
    }

    public Map<LocalDate, BigDecimal> getDailyPoints() {
        return dailyPoints;
    }

    public void setDailyPoints(Map<LocalDate, BigDecimal> dailyPoints) {
        this.dailyPoints = dailyPoints;
    }

    public Map<LocalDate, BigDecimal> getDailyPt1() {
        return dailyPt1;
    }

    public void setDailyPt1(Map<LocalDate, BigDecimal> dailyPt1) {
        this.dailyPt1 = dailyPt1;
    }

    public Map<LocalDate, BigDecimal> getDailyIc3() {
        return dailyIc3;
    }

    public void setDailyIc3(Map<LocalDate, BigDecimal> dailyIc3) {
        this.dailyIc3 = dailyIc3;
    }
}
