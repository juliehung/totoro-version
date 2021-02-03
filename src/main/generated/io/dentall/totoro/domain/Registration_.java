package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Registration.class)
public abstract class Registration_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Registration, Boolean> noCard;
	public static volatile SingularAttribute<Registration, Instant> arrivalTime;
	public static volatile SingularAttribute<Registration, Boolean> onSite;
	public static volatile SingularAttribute<Registration, String> abnormalCode;
	public static volatile SingularAttribute<Registration, Appointment> appointment;
	public static volatile SingularAttribute<Registration, Long> id;
	public static volatile SingularAttribute<Registration, Accounting> accounting;
	public static volatile SingularAttribute<Registration, String> type;
	public static volatile SingularAttribute<Registration, Disposal> disposal;
	public static volatile SingularAttribute<Registration, RegistrationStatus> status;

}

