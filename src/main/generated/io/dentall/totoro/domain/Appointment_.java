package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Appointment.class)
public abstract class Appointment_ extends io.dentall.totoro.domain.AbstractDoctorAndAuditingEntity_ {

	public static volatile SingularAttribute<Appointment, String> note;
	public static volatile SingularAttribute<Appointment, Boolean> contacted;
	public static volatile SingularAttribute<Appointment, String> subject;
	public static volatile SingularAttribute<Appointment, Integer> colorId;
	public static volatile SingularAttribute<Appointment, Integer> requiredTreatmentTime;
	public static volatile SingularAttribute<Appointment, Boolean> baseFloor;
	public static volatile SingularAttribute<Appointment, Boolean> microscope;
	public static volatile SingularAttribute<Appointment, Instant> expectedArrivalTime;
	public static volatile SingularAttribute<Appointment, Boolean> archived;
	public static volatile SingularAttribute<Appointment, Patient> patient;
	public static volatile SetAttribute<Appointment, TreatmentProcedure> treatmentProcedures;
	public static volatile SingularAttribute<Appointment, Boolean> disabled;
	public static volatile SingularAttribute<Appointment, Registration> registration;
	public static volatile SingularAttribute<Appointment, Long> id;
	public static volatile SingularAttribute<Appointment, Boolean> firstVisit;
	public static volatile SingularAttribute<Appointment, AppointmentStatus> status;

}

