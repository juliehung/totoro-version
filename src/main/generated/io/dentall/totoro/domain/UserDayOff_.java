package io.dentall.totoro.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserDayOff.class)
public abstract class UserDayOff_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<UserDayOff, Instant> fromDate;
	public static volatile SingularAttribute<UserDayOff, Instant> toDate;
	public static volatile SingularAttribute<UserDayOff, Long> id;
	public static volatile SingularAttribute<UserDayOff, Long> userId;

}

