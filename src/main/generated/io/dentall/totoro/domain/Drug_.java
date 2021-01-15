package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Drug.class)
public abstract class Drug_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Drug, String> nhiCode;
	public static volatile SingularAttribute<Drug, String> unit;
	public static volatile SingularAttribute<Drug, Double> quantity;
	public static volatile SingularAttribute<Drug, Double> price;
	public static volatile SingularAttribute<Drug, String> name;
	public static volatile SingularAttribute<Drug, String> chineseName;
	public static volatile SingularAttribute<Drug, String> warning;
	public static volatile SingularAttribute<Drug, Integer> days;
	public static volatile SingularAttribute<Drug, Long> id;
	public static volatile SingularAttribute<Drug, String> way;
	public static volatile SingularAttribute<Drug, String> frequency;
	public static volatile SingularAttribute<Drug, Integer> order;

}

