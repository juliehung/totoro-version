package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExtendUser.class)
public abstract class ExtendUser_ {

	public static volatile SingularAttribute<ExtendUser, String> gmail;
	public static volatile SetAttribute<ExtendUser, Appointment> appointments;
	public static volatile SingularAttribute<ExtendUser, Boolean> firstLogin;
	public static volatile SingularAttribute<ExtendUser, byte[]> avatar;
	public static volatile SetAttribute<ExtendUser, Treatment> treatments;
	public static volatile SingularAttribute<ExtendUser, String> avatarContentType;
	public static volatile SetAttribute<ExtendUser, Patient> firstPatients;
	public static volatile SetAttribute<ExtendUser, TreatmentTask> treatmentTasks;
	public static volatile SingularAttribute<ExtendUser, String> calendarId;
	public static volatile SingularAttribute<ExtendUser, String> nationalId;
	public static volatile SetAttribute<ExtendUser, Calendar> calendars;
	public static volatile SetAttribute<ExtendUser, Procedure> procedures;
	public static volatile SetAttribute<ExtendUser, TreatmentProcedure> treatmentProcedures;
	public static volatile SetAttribute<ExtendUser, Patient> lastPatients;
	public static volatile SingularAttribute<ExtendUser, Long> id;
	public static volatile SingularAttribute<ExtendUser, User> user;

}

