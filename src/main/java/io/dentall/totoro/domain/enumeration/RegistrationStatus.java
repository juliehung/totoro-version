package io.dentall.totoro.domain.enumeration;

/**
 * The RegistrationStatus enumeration.
 */
public enum RegistrationStatus {
    PENDING(0), FINISHED(1), IN_PROGRESS(2);

    private final int value;

    RegistrationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
