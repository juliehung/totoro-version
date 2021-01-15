package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TimeInterval;
import io.dentall.totoro.domain.enumeration.TimeType;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Calendar.class)
public abstract class Calendar_ {

	public static volatile SingularAttribute<Calendar, ExtendUser> doctor;
	public static volatile SingularAttribute<Calendar, String> duration;
	public static volatile SingularAttribute<Calendar, String> note;
	public static volatile SingularAttribute<Calendar, String> dayOffCron;
	public static volatile SingularAttribute<Calendar, Instant> start;
	public static volatile SingularAttribute<Calendar, TimeType> timeType;
	public static volatile SingularAttribute<Calendar, TimeInterval> timeInterval;
	public static volatile SingularAttribute<Calendar, Instant> end;
	public static volatile SingularAttribute<Calendar, Long> id;

}

