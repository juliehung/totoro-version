package io.dentall.totoro.business.service.nhi.metric.dto;

public class DoctorSummaryDto extends SummaryDto {

    private Long doctorId;

    private String doctorName;

    private int totalDisposal = 0;

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

    public int getTotalDisposal() {
        return totalDisposal;
    }

    public void setTotalDisposal(int totalDisposal) {
        this.totalDisposal = totalDisposal;
    }
}
