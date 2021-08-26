package io.dentall.totoro.business.service.nhi.metric.dto;

public class DoctorPoint1Dto {

    private Long doctorId;

    private String doctorName;

    private Long point1;

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

    public Long getPoint1() {
        return point1;
    }

    public void setPoint1(Long point1) {
        this.point1 = point1;
    }
}
