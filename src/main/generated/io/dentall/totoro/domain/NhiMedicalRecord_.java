package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiMedicalRecord.class)
public abstract class NhiMedicalRecord_ {

	public static volatile SingularAttribute<NhiMedicalRecord, String> date;
	public static volatile SingularAttribute<NhiMedicalRecord, String> nhiCode;
	public static volatile SingularAttribute<NhiMedicalRecord, String> note;
	public static volatile SingularAttribute<NhiMedicalRecord, String> total;
	public static volatile SingularAttribute<NhiMedicalRecord, String> nhiCategory;
	public static volatile SingularAttribute<NhiMedicalRecord, String> part;
	public static volatile SingularAttribute<NhiMedicalRecord, String> usage;
	public static volatile SingularAttribute<NhiMedicalRecord, String> days;
	public static volatile SingularAttribute<NhiMedicalRecord, Long> id;
	public static volatile SingularAttribute<NhiMedicalRecord, NhiExtendPatient> nhiExtendPatient;

}

