package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.metric.util.NumericUtils;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

public class SpecialTreatmentItem {

    private int caseNumber;

    private BigDecimal percentageOfCaseNumber;

    private long points;

    private BigDecimal percentageOfPoints;

    public SpecialTreatmentItem(int caseNumber, long points) {
        this.caseNumber = caseNumber;
        this.points = points;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public BigDecimal getPercentageOfCaseNumber() {
        return percentageOfCaseNumber;
    }

    public void calculatePercentageOfCaseNumber(int totalCaseNumber) {
        try {
            percentageOfCaseNumber = divide(caseNumber, totalCaseNumber);
        } catch (ArithmeticException e) {
            percentageOfCaseNumber = BigDecimal.ZERO;
        }
    }

    public BigDecimal getPercentageOfPoints() {
        return percentageOfPoints;
    }

    public void calculatePercentageOfPoints(Long totalPoints) {
        try {
            percentageOfPoints = divide(points, totalPoints);
        } catch (ArithmeticException e) {
            percentageOfPoints = BigDecimal.ZERO;
        }
    }
}
