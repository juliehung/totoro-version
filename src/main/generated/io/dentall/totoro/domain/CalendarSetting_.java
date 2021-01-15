package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TimeInterval;
import io.dentall.totoro.domain.enumeration.WeekDay;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CalendarSetting.class)
public abstract class CalendarSetting_ {

	public static volatile SingularAttribute<CalendarSetting, LocalDate> endDate;
	public static volatile SingularAttribute<CalendarSetting, WeekDay> weekday;
	public static volatile SingularAttribute<CalendarSetting, TimeInterval> timeInterval;
	public static volatile SingularAttribute<CalendarSetting, String> startTime;
	public static volatile SingularAttribute<CalendarSetting, Long> id;
	public static volatile SingularAttribute<CalendarSetting, String> endTime;
	public static volatile SingularAttribute<CalendarSetting, LocalDate> startDate;

}

