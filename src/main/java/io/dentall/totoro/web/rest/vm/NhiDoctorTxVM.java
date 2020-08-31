package io.dentall.totoro.web.rest.vm;

public interface NhiDoctorTxVM {
    Long getDid();

    String getNhiTxCode();

    String getNhiTxName();

    Integer getNhiTxPoint();

    Integer getTotalNumber();

    Integer getTotalPoint();

    String getSerialNumber();
}
