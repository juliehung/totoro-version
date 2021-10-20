package io.dentall.totoro.business.service.nhi.metric.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MetricDisposal {

    private Long disposalId;

    private LocalDate disposalDate;

    private Long patientId;

    private String patientName;

    private Long doctorId;

    private String doctorName;

    private String examCode;

    private String examPoint;

    private String partialBurden;

    private List<MetricTooth> toothList = new ArrayList<>();

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public LocalDate getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamPoint() {
        return examPoint;
    }

    public void setExamPoint(String examPoint) {
        this.examPoint = examPoint;
    }

    public String getPartialBurden() {
        return partialBurden;
    }

    public void setPartialBurden(String partialBurden) {
        this.partialBurden = partialBurden;
    }

    public List<MetricTooth> getToothList() {
        return toothList;
    }

    public void setToothList(List<MetricTooth> toothList) {
        this.toothList = toothList;
    }

    public void addTooth(MetricTooth tooth) {
        this.toothList.add(tooth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetricDisposal that = (MetricDisposal) o;
        return Objects.equals(disposalId, that.disposalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disposalId);
    }

}
