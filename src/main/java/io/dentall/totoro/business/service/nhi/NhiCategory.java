package io.dentall.totoro.business.service.nhi;

public enum NhiCategory {
    _01("01"),
    _02("02"),
    _03("03"),
    _04("04"),
    _05("05"),
    _06("06"),
    _07("07"),
    _08("08"),
    AA("AA"),
    AB("AB"),
    AC("AC"),
    AD("AD"),
    AE("AE"),
    AF("AF"),
    AG("AG"),
    AH("AH"),
    AI("AI"),
    BA("BA"),
    BB("BB"),
    BC("BC"),
    BD("BD"),
    BE("BE"),
    BF("BF"),
    CA("CA"),
    DA("DA"),
    DB("DB"),
    DC("DC"),
    ZA("ZA"),
    ZB("ZB"),
    ;

    private String value;

    NhiCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
