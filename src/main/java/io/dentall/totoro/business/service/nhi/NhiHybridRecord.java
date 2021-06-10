package io.dentall.totoro.business.service.nhi;

public interface NhiHybridRecord {
    String getRecordSource();

    Long getDisposalId();

    Long getDoctorId();

    String getRecordDateTime();

    String getCode();

    String getTooth();

    String getSurface();
}
