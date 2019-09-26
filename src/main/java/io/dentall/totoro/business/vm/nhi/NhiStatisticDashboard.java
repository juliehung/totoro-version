package io.dentall.totoro.business.vm.nhi;

import java.util.HashMap;
import java.util.Map;

public class NhiStatisticDashboard {

    private Long doctorId;

    private NhiCircleVM summaryCircle = new NhiCircleVM();

    // 案件總數
    private int totalCases = 0;

    // 根管案件數
    private int endoCases = 0;

    // 補牙案件數
    private int gvCases = 0;

    // 牙周整合案件數
    private int periCases = 0;

    // 其他案件數
    private int otherCases = 0;

    // 根管案比例
    private double endoRatio = 0.0;

    // 補牙比例
    private double gvRatio = 0.0;

    // 牙周整合比例
    private double periRatio = 0.0;

    // 其他比例
    private double otherRatio = 0.0;

    private Map<String, NhiCircleVM> circleMap = new HashMap<>();

    public NhiStatisticDashboard() {

        for (int idx = 1; idx < 10; idx++) {
            String idxStr = String.valueOf(idx);
            String specialCode = "P".concat(idxStr);
            circleMap.put(specialCode, new NhiCircleVM().specificCode(specialCode));
        }

        circleMap.put("other", new NhiCircleVM().specificCode("other"));
    }

    public NhiStatisticDashboard calculateRatio() {
        this.endoRatio = Double.valueOf(this.endoCases) / Double.valueOf(this.totalCases);
        this.gvRatio = Double.valueOf(this.gvCases) / Double.valueOf(this.totalCases);
        this.periRatio = Double.valueOf(this.periCases) / Double.valueOf(this.totalCases);
        this.otherRatio = Double.valueOf(this.otherCases) / Double.valueOf(this.totalCases);

        return this;
    }

    public NhiStatisticDashboard incrementTotalCases() {
        ++this.totalCases;
        return this;
    }

    public NhiStatisticDashboard incrementEndoCases() {
        ++this.endoCases;
        return this;
    }

    public NhiStatisticDashboard incrementGvCases() {
        ++this.gvCases;
        return this;
    }

    public NhiStatisticDashboard incrementPeriCases() {
        ++this.periCases;
        return this;
    }

    public NhiStatisticDashboard incrementOtherCases() {
        ++this.otherCases;
        return this;
    }

    public NhiStatisticDashboard doctorId(Long doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public NhiStatisticDashboard summaryCircle(NhiCircleVM summaryCircle) {
        this.summaryCircle = summaryCircle;
        return this;
    }

    public NhiStatisticDashboard endoRatio(double endoRatio) {
        this.endoRatio = endoRatio;
        return this;
    }

    public NhiStatisticDashboard gvRatio(double gvRatio) {
        this.gvRatio = gvRatio;
        return this;
    }

    public NhiStatisticDashboard periRatio(double periRatio) {
        this.periRatio = periRatio;
        return this;
    }

    public NhiStatisticDashboard otherRatio(double otherRatio) {
        this.otherRatio = otherRatio;
        return this;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public NhiCircleVM getSummaryCircle() {
        return summaryCircle;
    }

    public void setSummaryCircle(NhiCircleVM summaryCircle) {
        this.summaryCircle = summaryCircle;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getEndoCases() {
        return endoCases;
    }

    public void setEndoCases(int endoCases) {
        this.endoCases = endoCases;
    }

    public int getGvCases() {
        return gvCases;
    }

    public void setGvCases(int gvCases) {
        this.gvCases = gvCases;
    }

    public int getPeriCases() {
        return periCases;
    }

    public void setPeriCases(int periCases) {
        this.periCases = periCases;
    }

    public int getOtherCases() {
        return otherCases;
    }

    public void setOtherCases(int otherCases) {
        this.otherCases = otherCases;
    }

    public double getEndoRatio() {
        return endoRatio;
    }

    public void setEndoRatio(double endoRatio) {
        this.endoRatio = endoRatio;
    }

    public double getGvRatio() {
        return gvRatio;
    }

    public void setGvRatio(double gvRatio) {
        this.gvRatio = gvRatio;
    }

    public double getPeriRatio() {
        return periRatio;
    }

    public void setPeriRatio(double periRatio) {
        this.periRatio = periRatio;
    }

    public double getOtherRatio() {
        return otherRatio;
    }

    public void setOtherRatio(double otherRatio) {
        this.otherRatio = otherRatio;
    }

    public Map<String, NhiCircleVM> getCircleMap() {
        return circleMap;
    }

    public void setCircleMap(Map<String, NhiCircleVM> circleMap) {
        this.circleMap = circleMap;
    }
}
