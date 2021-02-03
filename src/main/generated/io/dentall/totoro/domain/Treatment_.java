package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TreatmentType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Treatment.class)
public abstract class Treatment_ extends io.dentall.totoro.domain.AbstractDoctorAndAuditingEntity_ {

	public static volatile SingularAttribute<Treatment, String> note;
	public static volatile SetAttribute<Treatment, TreatmentPlan> treatmentPlans;
	public static volatile SingularAttribute<Treatment, String> goal;
	public static volatile SingularAttribute<Treatment, Patient> patient;
	public static volatile SingularAttribute<Treatment, String> name;
	public static volatile SingularAttribute<Treatment, Long> id;
	public static volatile SingularAttribute<Treatment, String> finding;
	public static volatile SingularAttribute<Treatment, TreatmentType> type;
	public static volatile SingularAttribute<Treatment, String> chiefComplaint;

}

