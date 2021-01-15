package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TreatmentPlan.class)
public abstract class TreatmentPlan_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<TreatmentPlan, Treatment> treatment;
	public static volatile SetAttribute<TreatmentPlan, TreatmentTask> treatmentTasks;
	public static volatile SingularAttribute<TreatmentPlan, String> name;
	public static volatile SingularAttribute<TreatmentPlan, Long> id;
	public static volatile SingularAttribute<TreatmentPlan, Boolean> activated;

}

