package io.dentall.totoro.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

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

    public static final Map<Integer, RegistrationType> intToTypeMap = new HashMap<>();
    static {
        for (RegistrationType type : RegistrationType.values()) {
            intToTypeMap.put(type.value, type);
        }
    }
}
