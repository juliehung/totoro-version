package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Procedure.class)
public abstract class Procedure_ {

	public static volatile SingularAttribute<Procedure, ExtendUser> doctor;
	public static volatile SingularAttribute<Procedure, ProcedureType> procedureType;
	public static volatile SingularAttribute<Procedure, Double> price;
	public static volatile SingularAttribute<Procedure, Long> id;
	public static volatile SingularAttribute<Procedure, String> content;

}

