package io.dentall.totoro.business.service.nhi;

public enum NhiSpecialCode {
    P1("P1"),
    P2("P2"),
    P3("P3"),
    P4("P4"),
    P5("P5"),
    P6("P6"),
    P7("P7"),
    P8("P8"),
    OTHER("None")
    ;

    private String value;

    NhiSpecialCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;

    }

    public String toString() {
        return this.value;
    }
}
