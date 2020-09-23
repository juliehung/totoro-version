package io.dentall.totoro.web.rest.vm;

public interface NhiIndexOdVM {

    Long getDid();

    Integer getTotalPat();

    Integer getDistinctTotalPat();

    Integer getTotalTooth();

    Integer getTotalSurface();

    Double getSurfaceTooth_Rate();

    Double getToothPeopleRate();

    Double getSurfacePeopleRate();

    String getSerialNumber();

}
