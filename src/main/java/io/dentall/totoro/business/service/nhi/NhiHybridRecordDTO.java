package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.service.util.DateTimeUtil;

import java.time.LocalDate;

public class NhiHybridRecordDTO {

    private String recordSource;

    private Long disposalId;

    private Long doctorId;

    private LocalDate recordDateTime;

    private String code;

    private String tooth;

    private String surface;

    public NhiHybridRecordDTO() {}

    public NhiHybridRecordDTO(
        String recordSource,
        Long disposalId,
        Long doctorId,
        String recordDateTimeString,
        String code,
        String tooth,
        String surface
    ) {
        this.recordSource = recordSource;
        this.disposalId = disposalId;
        this.doctorId = doctorId;
        this.recordDateTime = DateTimeUtil.transformROCDateToLocalDate(recordDateTimeString);
        this.code = code;
        this.tooth = tooth;
        this.surface = surface;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(String recordSource) {
        this.recordSource = recordSource;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDateTime(LocalDate recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }
}
