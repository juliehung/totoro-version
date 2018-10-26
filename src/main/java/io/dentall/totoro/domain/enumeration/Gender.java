package io.dentall.totoro.domain.enumeration;

/**
 * The Gender enumeration.
 */
public enum Gender {
    OTHER(-1), MALE(0), FEMALE(1);

    private final int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
