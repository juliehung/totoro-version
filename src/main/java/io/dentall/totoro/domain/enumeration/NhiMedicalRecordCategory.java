package io.dentall.totoro.domain.enumeration;

public enum NhiMedicalRecordCategory {
    MEDICINE("1"), TX("3");

    // Although named as number but in db is varchar
    private String number;

    NhiMedicalRecordCategory(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return this.number;
    }
}
