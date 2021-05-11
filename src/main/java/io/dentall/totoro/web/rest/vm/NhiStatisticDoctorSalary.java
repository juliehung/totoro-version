package io.dentall.totoro.web.rest.vm;

import java.math.BigDecimal;
import java.time.Instant;

public class NhiStatisticDoctorSalary {

    /**
     * c2-4 合計點數
     * 總點數 (tx total + examination code)
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
     * c1-3 診療點數 (sum all tx point)
     */
    private Long treatmentPoint = 0L;

    /**
     * 牙周類型點數 (period)
     */
    private Long perioPoint = 0L;

    /**
     * 兒童類型點數 (not defined yet)
     */
    private Long pedoPoint = 0L;

    /**
     * 根管類型點數 (endo)
     */
    private Long endoPoint = 0L;

    /**
     * c2-3 申報件數
     */
    private Long totalDisposal = 0L;

    /**
     * c1-9 部分負擔點數
     */
    private Long copayment = 0L;

    /**
     * c3-1 一日平均申請點數
     */
    private BigDecimal avgApplyPointPerDay;

    /**
     * c3-2 一人平均申請點數
     */
    private BigDecimal avgApplyPointPerPatient;

    /**
     * c3-3 一件平均申請點數
     */
    private BigDecimal avgApplyPointPerCase;

    /**
     * c3-4 一人平均合計點數
     */
    private BigDecimal avgTotalPointPerPatient;

    /**
     * c3-5 一件平均合計點數
     */
    private BigDecimal avgTotalPointPerCase;

    /**
     * c3-6 一日平均件數
     */
    private BigDecimal avgCasePerDay;

    /**
     * c3-7 一人平均件數
     */
    private BigDecimal avgCasePerPatient;

    /**
     * c2-1 看診天數
     */
    private BigDecimal sumWorkingDays = BigDecimal.ZERO;

    /**
     * c2-2 看診人數
     */
    private BigDecimal sumVisitPatients = BigDecimal.ZERO;

    /**
     * c2-5 申請點數
     */
    private BigDecimal sumApplyPoints = BigDecimal.ZERO;

    /**
     * c1-1 診察費點數
     */
    private BigDecimal sumExaminationPoints = BigDecimal.ZERO;

    /**
     * c2-6 同療程件數
     */
    private BigDecimal sumSameTreatmentCases = BigDecimal.ZERO;

    /**
     * c1-2 診察費占比
     */
    private BigDecimal percentageOfExaminationPointOfTotalPoint = BigDecimal.ZERO;

    /**
     * c1-4 診療費占比
     */
    private BigDecimal percentageOfTreatmentPointOfTotalPoint = BigDecimal.ZERO;

    /**
     * c1-10 部分負擔占比
     */
    private BigDecimal percentageOfCopaymentOfTotalPoint = BigDecimal.ZERO;

    /**
     * p1-1 根管治療，總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP1 = BigDecimal.ZERO;

    /**
     * p1-2 根管治療，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP1 = BigDecimal.ZERO;

    /**
     * p1-3 根管治療，總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP1 = BigDecimal.ZERO;

    /**
     * p1-4 根管治療，總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP1 = BigDecimal.ZERO;

    /**
     * p2-1 銀粉填充，總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP2 = BigDecimal.ZERO;

    /**
     * p2-2 銀粉填充，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP2 = BigDecimal.ZERO;

    /**
     * p2-3 銀粉填充，總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP2 = BigDecimal.ZERO;

    /**
     * p2-4 銀粉填充，總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP2 = BigDecimal.ZERO;

    /**
     * p3-1 複合樹脂（玻璃璃子）充填，總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP3 = BigDecimal.ZERO;

    /**
     * p3-2 複合樹脂（玻璃璃子）充填，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP3 = BigDecimal.ZERO;

    /**
     * p3-3 複合樹脂（玻璃璃子）充填，總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP3 = BigDecimal.ZERO;

    /**
     * p3-4 複合樹脂（玻璃璃子）充填，總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP3 = BigDecimal.ZERO;

    /**
     * p4-1 牙周病手術（含齒齦下刮除術），總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP4 = BigDecimal.ZERO;

    /**
     * p4-2 牙周病手術（含齒齦下刮除術），總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP4 = BigDecimal.ZERO;

    /**
     * p4-3 牙周病手術（含齒齦下刮除術），總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP4 = BigDecimal.ZERO;

    /**
     * p4-4 牙周病手術（含齒齦下刮除術），總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP4 = BigDecimal.ZERO;

    /**
     * p5-1 兒童斷髓處理，總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP5 = BigDecimal.ZERO;

    /**
     * p5-2 兒童斷髓處理，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP5 = BigDecimal.ZERO;

    /**
     * p5-3 兒童斷髓處理，總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP5 = BigDecimal.ZERO;

    /**
     * p5-4 兒童斷髓處理，總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP5 = BigDecimal.ZERO;

    /**
     * p6-1 高壓氧治療，總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP6 = BigDecimal.ZERO;

    /**
     * p6-2 高壓氧治療，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP6 = BigDecimal.ZERO;

    /**
     * p6-3 高壓氧治療，總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP6 = BigDecimal.ZERO;

    /**
     * p6-4 高壓氧治療，總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP6 = BigDecimal.ZERO;

    /**
     * p7-1 口腔外科門診手術（包括拔牙），總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP7 = BigDecimal.ZERO;

    /**
     * p7-2 口腔外科門診手術（包括拔牙），總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP7 = BigDecimal.ZERO;

    /**
     * p7-3 口腔外科門診手術（包括拔牙），總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP7 = BigDecimal.ZERO;

    /**
     * p7-4 口腔外科門診手術（包括拔牙），總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP7 = BigDecimal.ZERO;

    /**
     * p8-1 治療性牙結石清除，總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentP8 = BigDecimal.ZERO;

    /**
     * p8-2 治療性牙結石清除，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentP8 = BigDecimal.ZERO;

    /**
     * p8-3 治療性牙結石清除，總點數
     */
    private BigDecimal pointsOfSpecialTreatmentP8 = BigDecimal.ZERO;

    /**
     * p8-4 治療性牙結石清除，總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentP8 = BigDecimal.ZERO;

    /**
     * p9-1 其他，總件數
     */
    private BigDecimal caseNumberOfSpecialTreatmentNone = BigDecimal.ZERO;

    /**
     * p9-2 其他，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfSpecialTreatmentNone = BigDecimal.ZERO;

    /**
     * p9-3 其他，總點數
     */
    private BigDecimal pointsOfSpecialTreatmentNone = BigDecimal.ZERO;

    /**
     * p9-4 其他，總點數百分比
     */
    private BigDecimal percentageOfPointsOfSpecialTreatmentNone = BigDecimal.ZERO;

    /**
     * p10-1 P4 + P8，總件數
     */
    private BigDecimal caseNumberOfP4P8 = BigDecimal.ZERO;

    /**
     * p10-2 P4 + P8，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfP4P8 = BigDecimal.ZERO;

    /**
     * p10-3 P4 + P8，總點數
     */
    private BigDecimal pointsOfP4P8 = BigDecimal.ZERO;

    /**
     * p10-4 P4 + P8，總點數百分比
     */
    private BigDecimal percentageOfPointsOfP4P8 = BigDecimal.ZERO;

    /**
     * p11-1 P2 + P3，總件數
     */
    private BigDecimal caseNumberOfP2P3 = BigDecimal.ZERO;

    /**
     * p11-2 P2 + P3，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfP2P3 = BigDecimal.ZERO;

    /**
     * p11-3 P2 + P3，總點數
     */
    private BigDecimal pointsOfP2P3 = BigDecimal.ZERO;

    /**
     * p11-4 P2 + P3，總點數百分比
     */
    private BigDecimal percentageOfPointsOfP2P3 = BigDecimal.ZERO;

    /**
     * p12-1 P1 + P5，總件數
     */
    private BigDecimal caseNumberOfP1P5 = BigDecimal.ZERO;

    /**
     * p12-2 P1 + P5，總件數百分比
     */
    private BigDecimal percentageOfCaseNumberOfP1P5 = BigDecimal.ZERO;

    /**
     * p12-3 P1 + P5，總點數
     */
    private BigDecimal pointsOfP1P5 = BigDecimal.ZERO;

    /**
     * p12-4 P1 + P5，總點數百分比
     */
    private BigDecimal percentageOfPointsOfP1P5 = BigDecimal.ZERO;

    /**
     * p13-1 P1 ~ P8 + other 總和件數
     */
    private BigDecimal sumCaseNumberOfSpecialTreatments = BigDecimal.ZERO;

    /**
     * p13-1 P1 ~ P8 + other 總和點數
     */
    private BigDecimal sumPointsOfSpecialTreatments = BigDecimal.ZERO;

    /**
     * 病患號碼(expand 才有)
     */
    private Long patientId;

    /**
     * 病患姓名(expand 才有)
     */
    private String patientName;

    /**
     * 病患是否為VIP(expand 才有)
     */
    private Boolean vipPatient;

    /**
     * 治療時間(expand 才有)
     */
    private Instant disposalDate;

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Boolean getVipPatient() { return this.vipPatient; }

    public void setVipPatient(Boolean vipPatient) { this.vipPatient = vipPatient; }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Instant getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Instant disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Long getCopayment() {
        return copayment;
    }

    public void setCopayment(Long copayment) {
        this.copayment = copayment;
    }

    public Long getTotalDisposal() {
        return totalDisposal;
    }

    public void setTotalDisposal(Long totalDisposal) {
        this.totalDisposal = totalDisposal;
    }

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

    public BigDecimal getAvgApplyPointPerDay() {
        return avgApplyPointPerDay;
    }

    public void setAvgApplyPointPerDay(BigDecimal avgApplyPointPerDay) {
        this.avgApplyPointPerDay = avgApplyPointPerDay;
    }

    public BigDecimal getAvgApplyPointPerPatient() {
        return avgApplyPointPerPatient;
    }

    public void setAvgApplyPointPerPatient(BigDecimal avgApplyPointPerPatient) {
        this.avgApplyPointPerPatient = avgApplyPointPerPatient;
    }

    public BigDecimal getAvgApplyPointPerCase() {
        return avgApplyPointPerCase;
    }

    public void setAvgApplyPointPerCase(BigDecimal avgApplyPointPerCase) {
        this.avgApplyPointPerCase = avgApplyPointPerCase;
    }

    public BigDecimal getAvgTotalPointPerPatient() {
        return avgTotalPointPerPatient;
    }

    public void setAvgTotalPointPerPatient(BigDecimal avgTotalPointPerPatient) {
        this.avgTotalPointPerPatient = avgTotalPointPerPatient;
    }

    public BigDecimal getAvgTotalPointPerCase() {
        return avgTotalPointPerCase;
    }

    public void setAvgTotalPointPerCase(BigDecimal avgTotalPointPerCase) {
        this.avgTotalPointPerCase = avgTotalPointPerCase;
    }

    public BigDecimal getAvgCasePerDay() {
        return avgCasePerDay;
    }

    public void setAvgCasePerDay(BigDecimal avgCasePerDay) {
        this.avgCasePerDay = avgCasePerDay;
    }

    public BigDecimal getAvgCasePerPatient() {
        return avgCasePerPatient;
    }

    public void setAvgCasePerPatient(BigDecimal avgCasePerPatient) {
        this.avgCasePerPatient = avgCasePerPatient;
    }

    public BigDecimal getSumWorkingDays() {
        return sumWorkingDays;
    }

    public void setSumWorkingDays(BigDecimal sumWorkingDays) {
        this.sumWorkingDays = sumWorkingDays;
    }

    public BigDecimal getSumVisitPatients() {
        return sumVisitPatients;
    }

    public void setSumVisitPatients(BigDecimal sumVisitPatients) {
        this.sumVisitPatients = sumVisitPatients;
    }

    public BigDecimal getSumApplyPoints() {
        return sumApplyPoints;
    }

    public void setSumApplyPoints(BigDecimal sumApplyPoints) {
        this.sumApplyPoints = sumApplyPoints;
    }

    public BigDecimal getSumSameTreatmentCases() {
        return sumSameTreatmentCases;
    }

    public BigDecimal getPercentageOfExaminationPointOfTotalPoint() {
        return percentageOfExaminationPointOfTotalPoint;
    }

    public void setPercentageOfExaminationPointOfTotalPoint(BigDecimal percentageOfExaminationPointOfTotalPoint) {
        this.percentageOfExaminationPointOfTotalPoint = percentageOfExaminationPointOfTotalPoint;
    }

    public BigDecimal getPercentageOfTreatmentPointOfTotalPoint() {
        return percentageOfTreatmentPointOfTotalPoint;
    }

    public void setPercentageOfTreatmentPointOfTotalPoint(BigDecimal percentageOfTreatmentPointOfTotalPoint) {
        this.percentageOfTreatmentPointOfTotalPoint = percentageOfTreatmentPointOfTotalPoint;
    }

    public BigDecimal getPercentageOfCopaymentOfTotalPoint() {
        return percentageOfCopaymentOfTotalPoint;
    }

    public void setPercentageOfCopaymentOfTotalPoint(BigDecimal percentageOfCopaymentOfTotalPoint) {
        this.percentageOfCopaymentOfTotalPoint = percentageOfCopaymentOfTotalPoint;
    }

    public BigDecimal getSumExaminationPoints() {
        return sumExaminationPoints;
    }

    public void setSumExaminationPoints(BigDecimal sumExaminationPoints) {
        this.sumExaminationPoints = sumExaminationPoints;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP1() {
        return caseNumberOfSpecialTreatmentP1;
    }

    public void setCaseNumberOfSpecialTreatmentP1(BigDecimal caseNumberOfSpecialTreatmentP1) {
        this.caseNumberOfSpecialTreatmentP1 = caseNumberOfSpecialTreatmentP1;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP1() {
        return percentageOfCaseNumberOfSpecialTreatmentP1;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP1(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP1) {
        this.percentageOfCaseNumberOfSpecialTreatmentP1 = percentageOfCaseNumberOfSpecialTreatmentP1;
    }

    public BigDecimal getPointsOfSpecialTreatmentP1() {
        return pointsOfSpecialTreatmentP1;
    }

    public void setPointsOfSpecialTreatmentP1(BigDecimal pointsOfSpecialTreatmentP1) {
        this.pointsOfSpecialTreatmentP1 = pointsOfSpecialTreatmentP1;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP1() {
        return percentageOfPointsOfSpecialTreatmentP1;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP1(BigDecimal percentageOfPointsOfSpecialTreatmentP1) {
        this.percentageOfPointsOfSpecialTreatmentP1 = percentageOfPointsOfSpecialTreatmentP1;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP2() {
        return caseNumberOfSpecialTreatmentP2;
    }

    public void setCaseNumberOfSpecialTreatmentP2(BigDecimal caseNumberOfSpecialTreatmentP2) {
        this.caseNumberOfSpecialTreatmentP2 = caseNumberOfSpecialTreatmentP2;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP2() {
        return percentageOfCaseNumberOfSpecialTreatmentP2;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP2(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP2) {
        this.percentageOfCaseNumberOfSpecialTreatmentP2 = percentageOfCaseNumberOfSpecialTreatmentP2;
    }

    public BigDecimal getPointsOfSpecialTreatmentP2() {
        return pointsOfSpecialTreatmentP2;
    }

    public void setPointsOfSpecialTreatmentP2(BigDecimal pointsOfSpecialTreatmentP2) {
        this.pointsOfSpecialTreatmentP2 = pointsOfSpecialTreatmentP2;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP2() {
        return percentageOfPointsOfSpecialTreatmentP2;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP2(BigDecimal percentageOfPointsOfSpecialTreatmentP2) {
        this.percentageOfPointsOfSpecialTreatmentP2 = percentageOfPointsOfSpecialTreatmentP2;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP3() {
        return caseNumberOfSpecialTreatmentP3;
    }

    public void setCaseNumberOfSpecialTreatmentP3(BigDecimal caseNumberOfSpecialTreatmentP3) {
        this.caseNumberOfSpecialTreatmentP3 = caseNumberOfSpecialTreatmentP3;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP3() {
        return percentageOfCaseNumberOfSpecialTreatmentP3;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP3(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP3) {
        this.percentageOfCaseNumberOfSpecialTreatmentP3 = percentageOfCaseNumberOfSpecialTreatmentP3;
    }

    public BigDecimal getPointsOfSpecialTreatmentP3() {
        return pointsOfSpecialTreatmentP3;
    }

    public void setPointsOfSpecialTreatmentP3(BigDecimal pointsOfSpecialTreatmentP3) {
        this.pointsOfSpecialTreatmentP3 = pointsOfSpecialTreatmentP3;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP3() {
        return percentageOfPointsOfSpecialTreatmentP3;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP3(BigDecimal percentageOfPointsOfSpecialTreatmentP3) {
        this.percentageOfPointsOfSpecialTreatmentP3 = percentageOfPointsOfSpecialTreatmentP3;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP4() {
        return caseNumberOfSpecialTreatmentP4;
    }

    public void setCaseNumberOfSpecialTreatmentP4(BigDecimal caseNumberOfSpecialTreatmentP4) {
        this.caseNumberOfSpecialTreatmentP4 = caseNumberOfSpecialTreatmentP4;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP4() {
        return percentageOfCaseNumberOfSpecialTreatmentP4;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP4(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP4) {
        this.percentageOfCaseNumberOfSpecialTreatmentP4 = percentageOfCaseNumberOfSpecialTreatmentP4;
    }

    public BigDecimal getPointsOfSpecialTreatmentP4() {
        return pointsOfSpecialTreatmentP4;
    }

    public void setPointsOfSpecialTreatmentP4(BigDecimal pointsOfSpecialTreatmentP4) {
        this.pointsOfSpecialTreatmentP4 = pointsOfSpecialTreatmentP4;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP4() {
        return percentageOfPointsOfSpecialTreatmentP4;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP4(BigDecimal percentageOfPointsOfSpecialTreatmentP4) {
        this.percentageOfPointsOfSpecialTreatmentP4 = percentageOfPointsOfSpecialTreatmentP4;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP5() {
        return caseNumberOfSpecialTreatmentP5;
    }

    public void setCaseNumberOfSpecialTreatmentP5(BigDecimal caseNumberOfSpecialTreatmentP5) {
        this.caseNumberOfSpecialTreatmentP5 = caseNumberOfSpecialTreatmentP5;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP5() {
        return percentageOfCaseNumberOfSpecialTreatmentP5;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP5(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP5) {
        this.percentageOfCaseNumberOfSpecialTreatmentP5 = percentageOfCaseNumberOfSpecialTreatmentP5;
    }

    public BigDecimal getPointsOfSpecialTreatmentP5() {
        return pointsOfSpecialTreatmentP5;
    }

    public void setPointsOfSpecialTreatmentP5(BigDecimal pointsOfSpecialTreatmentP5) {
        this.pointsOfSpecialTreatmentP5 = pointsOfSpecialTreatmentP5;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP5() {
        return percentageOfPointsOfSpecialTreatmentP5;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP5(BigDecimal percentageOfPointsOfSpecialTreatmentP5) {
        this.percentageOfPointsOfSpecialTreatmentP5 = percentageOfPointsOfSpecialTreatmentP5;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP6() {
        return caseNumberOfSpecialTreatmentP6;
    }

    public void setCaseNumberOfSpecialTreatmentP6(BigDecimal caseNumberOfSpecialTreatmentP6) {
        this.caseNumberOfSpecialTreatmentP6 = caseNumberOfSpecialTreatmentP6;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP6() {
        return percentageOfCaseNumberOfSpecialTreatmentP6;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP6(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP6) {
        this.percentageOfCaseNumberOfSpecialTreatmentP6 = percentageOfCaseNumberOfSpecialTreatmentP6;
    }

    public BigDecimal getPointsOfSpecialTreatmentP6() {
        return pointsOfSpecialTreatmentP6;
    }

    public void setPointsOfSpecialTreatmentP6(BigDecimal pointsOfSpecialTreatmentP6) {
        this.pointsOfSpecialTreatmentP6 = pointsOfSpecialTreatmentP6;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP6() {
        return percentageOfPointsOfSpecialTreatmentP6;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP6(BigDecimal percentageOfPointsOfSpecialTreatmentP6) {
        this.percentageOfPointsOfSpecialTreatmentP6 = percentageOfPointsOfSpecialTreatmentP6;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP7() {
        return caseNumberOfSpecialTreatmentP7;
    }

    public void setCaseNumberOfSpecialTreatmentP7(BigDecimal caseNumberOfSpecialTreatmentP7) {
        this.caseNumberOfSpecialTreatmentP7 = caseNumberOfSpecialTreatmentP7;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP7() {
        return percentageOfCaseNumberOfSpecialTreatmentP7;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP7(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP7) {
        this.percentageOfCaseNumberOfSpecialTreatmentP7 = percentageOfCaseNumberOfSpecialTreatmentP7;
    }

    public BigDecimal getPointsOfSpecialTreatmentP7() {
        return pointsOfSpecialTreatmentP7;
    }

    public void setPointsOfSpecialTreatmentP7(BigDecimal pointsOfSpecialTreatmentP7) {
        this.pointsOfSpecialTreatmentP7 = pointsOfSpecialTreatmentP7;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP7() {
        return percentageOfPointsOfSpecialTreatmentP7;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP7(BigDecimal percentageOfPointsOfSpecialTreatmentP7) {
        this.percentageOfPointsOfSpecialTreatmentP7 = percentageOfPointsOfSpecialTreatmentP7;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentP8() {
        return caseNumberOfSpecialTreatmentP8;
    }

    public void setCaseNumberOfSpecialTreatmentP8(BigDecimal caseNumberOfSpecialTreatmentP8) {
        this.caseNumberOfSpecialTreatmentP8 = caseNumberOfSpecialTreatmentP8;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentP8() {
        return percentageOfCaseNumberOfSpecialTreatmentP8;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentP8(BigDecimal percentageOfCaseNumberOfSpecialTreatmentP8) {
        this.percentageOfCaseNumberOfSpecialTreatmentP8 = percentageOfCaseNumberOfSpecialTreatmentP8;
    }

    public BigDecimal getPointsOfSpecialTreatmentP8() {
        return pointsOfSpecialTreatmentP8;
    }

    public void setPointsOfSpecialTreatmentP8(BigDecimal pointsOfSpecialTreatmentP8) {
        this.pointsOfSpecialTreatmentP8 = pointsOfSpecialTreatmentP8;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentP8() {
        return percentageOfPointsOfSpecialTreatmentP8;
    }

    public void setPercentageOfPointsOfSpecialTreatmentP8(BigDecimal percentageOfPointsOfSpecialTreatmentP8) {
        this.percentageOfPointsOfSpecialTreatmentP8 = percentageOfPointsOfSpecialTreatmentP8;
    }

    public BigDecimal getCaseNumberOfSpecialTreatmentNone() {
        return caseNumberOfSpecialTreatmentNone;
    }

    public void setCaseNumberOfSpecialTreatmentNone(BigDecimal caseNumberOfSpecialTreatmentNone) {
        this.caseNumberOfSpecialTreatmentNone = caseNumberOfSpecialTreatmentNone;
    }

    public BigDecimal getPercentageOfCaseNumberOfSpecialTreatmentNone() {
        return percentageOfCaseNumberOfSpecialTreatmentNone;
    }

    public void setPercentageOfCaseNumberOfSpecialTreatmentNone(BigDecimal percentageOfCaseNumberOfSpecialTreatmentNone) {
        this.percentageOfCaseNumberOfSpecialTreatmentNone = percentageOfCaseNumberOfSpecialTreatmentNone;
    }

    public BigDecimal getPointsOfSpecialTreatmentNone() {
        return pointsOfSpecialTreatmentNone;
    }

    public void setPointsOfSpecialTreatmentNone(BigDecimal pointsOfSpecialTreatmentNone) {
        this.pointsOfSpecialTreatmentNone = pointsOfSpecialTreatmentNone;
    }

    public BigDecimal getPercentageOfPointsOfSpecialTreatmentNone() {
        return percentageOfPointsOfSpecialTreatmentNone;
    }

    public void setPercentageOfPointsOfSpecialTreatmentNone(BigDecimal percentageOfPointsOfSpecialTreatmentNone) {
        this.percentageOfPointsOfSpecialTreatmentNone = percentageOfPointsOfSpecialTreatmentNone;
    }

    public BigDecimal getPercentageOfCaseNumberOfP4P8() {
        return percentageOfCaseNumberOfP4P8;
    }

    public void setPercentageOfCaseNumberOfP4P8(BigDecimal percentageOfCaseNumberOfP4P8) {
        this.percentageOfCaseNumberOfP4P8 = percentageOfCaseNumberOfP4P8;
    }

    public BigDecimal getPointsOfP4P8() {
        return pointsOfP4P8;
    }

    public void setPointsOfP4P8(BigDecimal pointsOfP4P8) {
        this.pointsOfP4P8 = pointsOfP4P8;
    }

    public BigDecimal getPercentageOfPointsOfP4P8() {
        return percentageOfPointsOfP4P8;
    }

    public void setPercentageOfPointsOfP4P8(BigDecimal percentageOfPointsOfP4P8) {
        this.percentageOfPointsOfP4P8 = percentageOfPointsOfP4P8;
    }

    public BigDecimal getPercentageOfCaseNumberOfP2P3() {
        return percentageOfCaseNumberOfP2P3;
    }

    public void setPercentageOfCaseNumberOfP2P3(BigDecimal percentageOfCaseNumberOfP2P3) {
        this.percentageOfCaseNumberOfP2P3 = percentageOfCaseNumberOfP2P3;
    }

    public BigDecimal getPointsOfP2P3() {
        return pointsOfP2P3;
    }

    public void setPointsOfP2P3(BigDecimal pointsOfP2P3) {
        this.pointsOfP2P3 = pointsOfP2P3;
    }

    public BigDecimal getPercentageOfPointsOfP2P3() {
        return percentageOfPointsOfP2P3;
    }

    public void setPercentageOfPointsOfP2P3(BigDecimal percentageOfPointsOfP2P3) {
        this.percentageOfPointsOfP2P3 = percentageOfPointsOfP2P3;
    }

    public BigDecimal getPercentageOfCaseNumberOfP1P5() {
        return percentageOfCaseNumberOfP1P5;
    }

    public void setPercentageOfCaseNumberOfP1P5(BigDecimal percentageOfCaseNumberOfP1P5) {
        this.percentageOfCaseNumberOfP1P5 = percentageOfCaseNumberOfP1P5;
    }

    public BigDecimal getPointsOfP1P5() {
        return pointsOfP1P5;
    }

    public void setPointsOfP1P5(BigDecimal pointsOfP1P5) {
        this.pointsOfP1P5 = pointsOfP1P5;
    }

    public BigDecimal getPercentageOfPointsOfP1P5() {
        return percentageOfPointsOfP1P5;
    }

    public void setPercentageOfPointsOfP1P5(BigDecimal percentageOfPointsOfP1P5) {
        this.percentageOfPointsOfP1P5 = percentageOfPointsOfP1P5;
    }

    public BigDecimal getCaseNumberOfP4P8() {
        return caseNumberOfP4P8;
    }

    public void setCaseNumberOfP4P8(BigDecimal caseNumberOfP4P8) {
        this.caseNumberOfP4P8 = caseNumberOfP4P8;
    }

    public BigDecimal getCaseNumberOfP2P3() {
        return caseNumberOfP2P3;
    }

    public void setCaseNumberOfP2P3(BigDecimal caseNumberOfP2P3) {
        this.caseNumberOfP2P3 = caseNumberOfP2P3;
    }

    public BigDecimal getCaseNumberOfP1P5() {
        return caseNumberOfP1P5;
    }

    public void setCaseNumberOfP1P5(BigDecimal caseNumberOfP1P5) {
        this.caseNumberOfP1P5 = caseNumberOfP1P5;
    }

    public BigDecimal getSumCaseNumberOfSpecialTreatments() {
        return sumCaseNumberOfSpecialTreatments;
    }

    public void setSumCaseNumberOfSpecialTreatments(BigDecimal sumCaseNumberOfSpecialTreatments) {
        this.sumCaseNumberOfSpecialTreatments = sumCaseNumberOfSpecialTreatments;
    }

    public BigDecimal getSumPointsOfSpecialTreatments() {
        return sumPointsOfSpecialTreatments;
    }

    public void setSumPointsOfSpecialTreatments(BigDecimal sumPointsOfSpecialTreatments) {
        this.sumPointsOfSpecialTreatments = sumPointsOfSpecialTreatments;
    }

    public void setSumSameTreatmentCases(BigDecimal sumSameTreatmentCases) {
        this.sumSameTreatmentCases = sumSameTreatmentCases;
    }
}
