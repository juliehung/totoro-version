package io.dentall.totoro.business.vm.nhi;


public class NhiCircleVM {
    private String specificCode;
    private Integer cases = 0;
    private Integer points = 0;

    public NhiCircleVM incrementPoints(int points) {
        this.points = this.points + points;
        return this;
    }

    public NhiCircleVM incrementCase() {
        ++this.cases;
        return this;
    }

    public NhiCircleVM specificCode(String specificCode) {
        this.specificCode = specificCode;
        return this;
    }

    public String getSpecificCode() {
        return specificCode;
    }

    public void setSpecificCode(String specificCode) {
        this.specificCode = specificCode;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
