package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.Blood;
import io.dentall.totoro.domain.enumeration.Gender;
import java.time.Instant;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Patient.class)
public abstract class Patient_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Patient, String> career;
	public static volatile SingularAttribute<Patient, Boolean> newPatient;
	public static volatile SingularAttribute<Patient, String> introducer;
	public static volatile SingularAttribute<Patient, String> caseManager;
	public static volatile SingularAttribute<Patient, LocalDate> dueDate;
	public static volatile SingularAttribute<Patient, String> customizedBloodDisease;
	public static volatile SingularAttribute<Patient, String> avatarContentType;
	public static volatile SetAttribute<Patient, Patient> children;
	public static volatile SingularAttribute<Patient, String> emergencyRelationship;
	public static volatile SetAttribute<Patient, Tooth> teeth;
	public static volatile SingularAttribute<Patient, Long> id;
	public static volatile SingularAttribute<Patient, String> vip;
	public static volatile SetAttribute<Patient, Appointment> appointments;
	public static volatile SingularAttribute<Patient, LocalDate> birth;
	public static volatile SingularAttribute<Patient, String> clinicNote;
	public static volatile SetAttribute<Patient, Tag> tags;
	public static volatile SingularAttribute<Patient, PatientIdentity> patientIdentity;
	public static volatile SingularAttribute<Patient, String> phone;
	public static volatile SingularAttribute<Patient, String> cardId;
	public static volatile SingularAttribute<Patient, String> name;
	public static volatile SingularAttribute<Patient, String> medicalId;
	public static volatile SingularAttribute<Patient, String> customizedAllergy;
	public static volatile SingularAttribute<Patient, NhiExtendPatient> nhiExtendPatient;
	public static volatile SetAttribute<Patient, Patient> parents;
	public static volatile SingularAttribute<Patient, String> note;
	public static volatile SingularAttribute<Patient, Questionnaire> questionnaire;
	public static volatile SingularAttribute<Patient, String> customizedDisease;
	public static volatile SingularAttribute<Patient, Gender> gender;
	public static volatile SingularAttribute<Patient, String> displayName;
	public static volatile SetAttribute<Patient, Patient> spouse2S;
	public static volatile SingularAttribute<Patient, Boolean> vipPatient;
	public static volatile SingularAttribute<Patient, String> marriage;
	public static volatile SingularAttribute<Patient, String> fbId;
	public static volatile SingularAttribute<Patient, Instant> writeIcTime;
	public static volatile SingularAttribute<Patient, Boolean> disabled;
	public static volatile SingularAttribute<Patient, String> email;
	public static volatile SingularAttribute<Patient, String> teethGraphPermanentSwitch;
	public static volatile SingularAttribute<Patient, Instant> deleteDate;
	public static volatile SingularAttribute<Patient, LocalDate> scaling;
	public static volatile SingularAttribute<Patient, String> address;
	public static volatile SingularAttribute<Patient, String> customizedOther;
	public static volatile SetAttribute<Patient, Patient> spouse1S;
	public static volatile SingularAttribute<Patient, String> emergencyAddress;
	public static volatile SingularAttribute<Patient, String> lineId;
	public static volatile SingularAttribute<Patient, ExtendUser> firstDoctor;
	public static volatile SingularAttribute<Patient, byte[]> avatar;
	public static volatile SingularAttribute<Patient, Blood> blood;
	public static volatile SingularAttribute<Patient, ExtendUser> lastDoctor;
	public static volatile SingularAttribute<Patient, String> emergencyName;
	public static volatile SingularAttribute<Patient, String> nationalId;
	public static volatile SingularAttribute<Patient, String> mainNoticeChannel;
	public static volatile SetAttribute<Patient, Todo> todos;
	public static volatile SingularAttribute<Patient, String> emergencyPhone;

}

