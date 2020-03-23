package io.dentall.totoro.service.dto;

import javax.validation.constraints.NotBlank;

/**
 * A DTO representing a SmsCharge.
 */
public class SmsChargeDTO {

    @NotBlank
    private String clinic;

    private double charge;

    private int segments;

    // staff
    // vendor

    public SmsChargeDTO() {
        // Empty constructor needed for Jackson.
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    @Override
    public String toString() {
        return "SmsChargeDTO{" +
            "clinic='" + clinic + '\'' +
            ", charge='" + charge + '\'' +
            ", segments='" + segments + '\'' +
            "}";
    }
}
