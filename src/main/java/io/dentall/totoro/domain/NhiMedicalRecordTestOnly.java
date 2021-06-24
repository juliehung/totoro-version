package io.dentall.totoro.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NhiMedicalRecordTestOnly  {

    private Long id;

    private String date;

    private String nhiCategory;

    private String nhiCode;

    private String part;

    private String usage;

    private String total;

    private String note;

    private String days;

    private NhiExtendPatient nhiExtendPatient;

}
