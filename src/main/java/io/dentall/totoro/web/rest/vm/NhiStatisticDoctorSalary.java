package io.dentall.totoro.web.rest.vm;

public class NhiStatisticDoctorSalary {

    /**
     * 總點數
     */
    private Long total = 0L;

    /**
     * 感染控制診察點數
     */
    private Long infectionExaminationPoint = 0L;

    /**
     * 一般診察點數
     */
    private Long regularExaminationPoint = 0L;

    /**
     * 診療點數
     */
    private Long treatmentPoint = 0L;

    /**
     * 牙周類型點數
     */
    private Long perioPoint = 0L;

    /**
     * 兒童類型點數
     */
    private Long pedoPoint = 0L;

    /**
     * 根管類型點數
     */
    private Long endoPoint = 0L;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getInfectionExaminationPoint() {
        return infectionExaminationPoint;
    }

    public void setInfectionExaminationPoint(Long infectionExaminationPoint) {
        this.infectionExaminationPoint = infectionExaminationPoint;
    }

    public Long getRegularExaminationPoint() {
        return regularExaminationPoint;
    }

    public void setRegularExaminationPoint(Long regularExaminationPoint) {
        this.regularExaminationPoint = regularExaminationPoint;
    }

    public Long getTreatmentPoint() {
        return treatmentPoint;
    }

    public void setTreatmentPoint(Long treatmentPoint) {
        this.treatmentPoint = treatmentPoint;
    }

    public Long getPerioPoint() {
        return perioPoint;
    }

    public void setPerioPoint(Long perioPoint) {
        this.perioPoint = perioPoint;
    }

    public Long getPedoPoint() {
        return pedoPoint;
    }

    public void setPedoPoint(Long pedoPoint) {
        this.pedoPoint = pedoPoint;
    }

    public Long getEndoPoint() {
        return endoPoint;
    }

    public void setEndoPoint(Long endoPoint) {
        this.endoPoint = endoPoint;
    }
}
