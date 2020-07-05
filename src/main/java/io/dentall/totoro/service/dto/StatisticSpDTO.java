package io.dentall.totoro.service.dto;

public class StatisticSpDTO {
    private String createdBy;

    private String specificCode;

    private Integer point;

    public StatisticSpDTO(
        String createdBy,
        String specificCode,
        Integer point
    ) {
        this.createdBy = createdBy;
        this.specificCode = specificCode;
        this.point = point;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSpecificCode() {
        return specificCode;
    }

    public void setSpecificCode(String specificCode) {
        this.specificCode = specificCode;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
