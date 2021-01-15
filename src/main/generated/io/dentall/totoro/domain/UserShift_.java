package io.dentall.totoro.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserShift.class)
public abstract class UserShift_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<UserShift, Instant> fromDate;
	public static volatile SingularAttribute<UserShift, Instant> toDate;
	public static volatile SingularAttribute<UserShift, Long> id;
	public static volatile SingularAttribute<UserShift, Long> userId;

}

