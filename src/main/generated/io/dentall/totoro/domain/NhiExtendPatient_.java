package io.dentall.totoro.domain;

import java.util.Map;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiExtendPatient.class)
public abstract class NhiExtendPatient_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<NhiExtendPatient, String> cardAnnotation;
	public static volatile SingularAttribute<NhiExtendPatient, String> nhiIdentity;
	public static volatile SingularAttribute<NhiExtendPatient, String> scaling;
	public static volatile SingularAttribute<NhiExtendPatient, String> cardIssueDate;
	public static volatile SingularAttribute<NhiExtendPatient, Map<java.lang.String,java.lang.Object>> lifetime;
	public static volatile SingularAttribute<NhiExtendPatient, String> fluoride;
	public static volatile SetAttribute<NhiExtendPatient, NhiMedicalRecord> nhiMedicalRecords;
	public static volatile SingularAttribute<NhiExtendPatient, Integer> availableTimes;
	public static volatile SingularAttribute<NhiExtendPatient, Patient> patient;
	public static volatile SingularAttribute<NhiExtendPatient, String> perio;
	public static volatile SingularAttribute<NhiExtendPatient, Long> id;
	public static volatile SingularAttribute<NhiExtendPatient, String> cardValidDate;
	public static volatile SingularAttribute<NhiExtendPatient, String> cardNumber;

}

