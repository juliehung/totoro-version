package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TreatmentTask.class)
public abstract class TreatmentTask_ extends io.dentall.totoro.domain.AbstractDoctorAndAuditingEntity_ {

	public static volatile SingularAttribute<TreatmentTask, String> note;
	public static volatile SingularAttribute<TreatmentTask, String> name;
	public static volatile SetAttribute<TreatmentTask, TreatmentProcedure> treatmentProcedures;
	public static volatile SingularAttribute<TreatmentTask, Long> id;
	public static volatile SingularAttribute<TreatmentTask, TreatmentPlan> treatmentPlan;

}

