package io.dentall.totoro.domain.enumeration;

/**
 * The PrescriptionMode enumeration.
 */
public enum PrescriptionMode {
    SELF("01"), DELIVERY("02"), NONE("");

    private final String value;

    PrescriptionMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
