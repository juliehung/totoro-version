package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.NhiExtendDisposal;

import java.io.Serializable;

public class NhiExtendDisposalVM extends NhiExtendDisposal implements Serializable {

    @JsonProperty
    private String name;

    @JsonProperty
    private String doctor;

    public NhiExtendDisposalVM(NhiExtendDisposal nhiExtendDisposal) {
        name = nhiExtendDisposal.getDisposal().getRegistration().getAppointment().getPatient().getName();
        doctor = nhiExtendDisposal.getDisposal().getCreatedBy();

        setId(nhiExtendDisposal.getId());
        setDisposal(nhiExtendDisposal.getDisposal());
        setA11(nhiExtendDisposal.getA11());
        setA12(nhiExtendDisposal.getA12());
        setA13(nhiExtendDisposal.getA13());
        setA14(nhiExtendDisposal.getA14());
        setA15(nhiExtendDisposal.getA15());
        setA16(nhiExtendDisposal.getA16());
        setA17(nhiExtendDisposal.getA17());
        setA18(nhiExtendDisposal.getA18());
        setA19(nhiExtendDisposal.getA19());
        setA22(nhiExtendDisposal.getA22());
        setA23(nhiExtendDisposal.getA23());
        setA25(nhiExtendDisposal.getA25());
        setA26(nhiExtendDisposal.getA26());
        setA27(nhiExtendDisposal.getA27());
        setA31(nhiExtendDisposal.getA31());
        setA32(nhiExtendDisposal.getA32());
        setA41(nhiExtendDisposal.getA41());
        setA42(nhiExtendDisposal.getA42());
        setA43(nhiExtendDisposal.getA43());
        setA44(nhiExtendDisposal.getA44());
        setA54(nhiExtendDisposal.getA54());
        setUploadStatus(nhiExtendDisposal.getUploadStatus());
        setExaminationCode(nhiExtendDisposal.getExaminationCode());
        setExaminationPoint(nhiExtendDisposal.getExaminationPoint());
        setNhiExtendTreatmentProcedures(nhiExtendDisposal.getNhiExtendTreatmentProcedures());
        setNhiExtendTreatmentDrugs(nhiExtendDisposal.getNhiExtendTreatmentDrugs());
        setNhiDayUploadDetails(nhiExtendDisposal.getNhiDayUploadDetails());
        setPatientIdentity(nhiExtendDisposal.getPatientIdentity());
        setSerialNumber(nhiExtendDisposal.getSerialNumber());
    }
}
