package io.dentall.totoro.domain.enumeration;

/**
 * The RegistrationType enumeration.
 */
public enum RegistrationType {
    OWN_EXPENSE(0), NHI(1), NHI_NO_CARD(2);

    private final int value;

    RegistrationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
