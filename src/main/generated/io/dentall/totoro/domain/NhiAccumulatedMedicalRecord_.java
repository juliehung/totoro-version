package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiAccumulatedMedicalRecord.class)
public abstract class NhiAccumulatedMedicalRecord_ {

	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, String> date;
	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, String> medicalInstitutionCode;
	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, String> medicalCategory;
	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, Patient> patient;
	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, String> seqNumber;
	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, Long> id;
	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, String> cardFillingNote;
	public static volatile SingularAttribute<NhiAccumulatedMedicalRecord, String> newbornMedicalTreatmentNote;

}

