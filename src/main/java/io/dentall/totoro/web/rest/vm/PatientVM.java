package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.Blood;
import io.dentall.totoro.domain.enumeration.Gender;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientVM {
    private Long id;

    private String displayName;

    private String name;

    private String phone;

    private Gender gender;

    private LocalDate birth;

    private String nationalId;

    private String medicalId;

    private String address;

    private String email;

    private Blood blood;

    private String cardId;

    private String vip;

    private String emergencyName;

    private String emergencyPhone;

    private String emergencyAddress;

    private String emergencyRelationship;

    private Instant deleteDate;

    private LocalDate scaling;

    private String lineId;

    private String fbId;

    private String note;

    private String clinicNote;

    private Instant writeIcTime;

    private String mainNoticeChannel;

    private String career;

    private String marriage;

    private Boolean newPatient;

    private String teethGraphPermanentSwitch;

    private String introducer;

    private String caseManager;

    private Boolean vipPatient;

    private Boolean disabled;

    private List<Tag> tags = new ArrayList();

    private PatientIdentity patientIdentity;

    private NhiExtendPatient nhiExtendPatient;

    private LocalDate dueDate;

    private String customizedDisease;

    private String customizedBloodDisease;

    private String customizedAllergy;

    private String customizedOther;

    // NhiExtendPatient
    private String cardNumber;

    private String cardAnnotation;

    private String cardValidDate;

    private String cardIssueDate;

    private String nhiIdentity;

    private Integer availableTimes;

}
