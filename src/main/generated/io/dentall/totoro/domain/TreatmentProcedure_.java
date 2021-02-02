package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TreatmentProcedure.class)
public abstract class TreatmentProcedure_ extends io.dentall.totoro.domain.AbstractDoctorAndAuditingEntity_ {

	public static volatile SingularAttribute<TreatmentProcedure, String> note;
	public static volatile SingularAttribute<TreatmentProcedure, String> nhiIcd10Cm;
	public static volatile SingularAttribute<TreatmentProcedure, Integer> quantity;
	public static volatile SingularAttribute<TreatmentProcedure, NhiProcedure> nhiProcedure;
	public static volatile SingularAttribute<TreatmentProcedure, String> nhiCategory;
	public static volatile SingularAttribute<TreatmentProcedure, NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedure;
	public static volatile SingularAttribute<TreatmentProcedure, Appointment> appointment;
	public static volatile SingularAttribute<TreatmentProcedure, Procedure> procedure;
	public static volatile SingularAttribute<TreatmentProcedure, Instant> completedDate;
	public static volatile SingularAttribute<TreatmentProcedure, String> nhiDescription;
	public static volatile SingularAttribute<TreatmentProcedure, Disposal> disposal;
	public static volatile SingularAttribute<TreatmentProcedure, Double> total;
	public static volatile SingularAttribute<TreatmentProcedure, Instant> createdDate;
	public static volatile SingularAttribute<TreatmentProcedure, Double> price;
	public static volatile SetAttribute<TreatmentProcedure, Tooth> teeth;
	public static volatile SingularAttribute<TreatmentProcedure, Long> id;
	public static volatile SetAttribute<TreatmentProcedure, Todo> todos;
	public static volatile SingularAttribute<TreatmentProcedure, TreatmentTask> treatmentTask;
	public static volatile SingularAttribute<TreatmentProcedure, TreatmentProcedureStatus> status;

}

