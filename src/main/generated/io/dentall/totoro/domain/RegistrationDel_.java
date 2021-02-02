package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RegistrationDel.class)
public abstract class RegistrationDel_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<RegistrationDel, Boolean> noCard;
	public static volatile SingularAttribute<RegistrationDel, Instant> arrivalTime;
	public static volatile SingularAttribute<RegistrationDel, Long> appointmentId;
	public static volatile SingularAttribute<RegistrationDel, Long> accountingId;
	public static volatile SingularAttribute<RegistrationDel, Boolean> onSite;
	public static volatile SingularAttribute<RegistrationDel, String> abnormalCode;
	public static volatile SingularAttribute<RegistrationDel, Long> id;
	public static volatile SingularAttribute<RegistrationDel, String> type;
	public static volatile SingularAttribute<RegistrationDel, RegistrationStatus> status;

}

