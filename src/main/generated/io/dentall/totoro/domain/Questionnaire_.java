package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Questionnaire.class)
public abstract class Questionnaire_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Questionnaire, String> otherInTreatment;
	public static volatile SingularAttribute<Questionnaire, Integer> smokeNumberADay;
	public static volatile SingularAttribute<Questionnaire, String> drugName;
	public static volatile SingularAttribute<Questionnaire, Integer> glycemicPC;
	public static volatile SingularAttribute<Questionnaire, Long> id;
	public static volatile SingularAttribute<Questionnaire, Integer> glycemicAC;
	public static volatile SingularAttribute<Questionnaire, Boolean> drug;

}

