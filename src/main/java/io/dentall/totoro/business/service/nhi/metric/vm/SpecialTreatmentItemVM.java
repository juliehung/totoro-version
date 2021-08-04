package io.dentall.totoro.business.service.nhi.metric.vm;

import java.math.BigDecimal;

public class SpecialTreatmentItemVM {

    private long points;

    private int caseCount;

    private BigDecimal percentageOfPoints;

    private BigDecimal percentageOfCaseCount;

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public int getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(int caseCount) {
        this.caseCount = caseCount;
    }

    public BigDecimal getPercentageOfPoints() {
        return percentageOfPoints;
    }

    public void setPercentageOfPoints(BigDecimal percentageOfPoints) {
        this.percentageOfPoints = percentageOfPoints;
    }

    public BigDecimal getPercentageOfCaseCount() {
        return percentageOfCaseCount;
    }

    public void setPercentageOfCaseCount(BigDecimal percentageOfCaseCount) {
        this.percentageOfCaseCount = percentageOfCaseCount;
    }

}
