package io.dentall.totoro.business.service.nhi.metric.dto;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

public class SpecialTreatmentItemDto {

    private int caseCount;

    private BigDecimal percentageOfCaseCount;

    private long points;

    private BigDecimal percentageOfPoints;

    public SpecialTreatmentItemDto(int caseCount, long points) {
        this.caseCount = caseCount;
        this.points = points;
    }

    public int getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(int caseCount) {
        this.caseCount = caseCount;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public BigDecimal getPercentageOfCaseCount() {
        return percentageOfCaseCount;
    }

    public void calculatePercentageOfCaseCount(int totalCaseNumber) {
        try {
            percentageOfCaseCount = divide(caseCount, totalCaseNumber);
        } catch (ArithmeticException e) {
            percentageOfCaseCount = BigDecimal.ZERO;
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
