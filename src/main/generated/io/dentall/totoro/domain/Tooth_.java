package io.dentall.totoro.domain;

import java.util.Map;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tooth.class)
public abstract class Tooth_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Tooth, Map<java.lang.String,java.lang.Object>> metadata;
	public static volatile SingularAttribute<Tooth, String> surface;
	public static volatile SingularAttribute<Tooth, Patient> patient;
	public static volatile SingularAttribute<Tooth, Long> id;
	public static volatile SingularAttribute<Tooth, String> position;
	public static volatile SingularAttribute<Tooth, TreatmentProcedure> treatmentProcedure;
	public static volatile SingularAttribute<Tooth, Disposal> disposal;
	public static volatile SingularAttribute<Tooth, String> status;

}

