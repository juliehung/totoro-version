package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.DisposalRevisitInterval;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Disposal.class)
public abstract class Disposal_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Disposal, Instant> dateTime;
	public static volatile SingularAttribute<Disposal, Boolean> revisitWillNotHappen;
	public static volatile SingularAttribute<Disposal, Instant> dateTimeEnd;
	public static volatile SingularAttribute<Disposal, String> chiefComplaint;
	public static volatile SingularAttribute<Disposal, String> revisitContent;
	public static volatile SingularAttribute<Disposal, Todo> todo;
	public static volatile SingularAttribute<Disposal, Double> total;
	public static volatile SingularAttribute<Disposal, DisposalRevisitInterval> revisitInterval;
	public static volatile SingularAttribute<Disposal, Integer> revisitTreatmentTime;
	public static volatile SingularAttribute<Disposal, Prescription> prescription;
	public static volatile SingularAttribute<Disposal, String> createdBy;
	public static volatile SetAttribute<Disposal, NhiExtendDisposal> nhiExtendDisposals;
	public static volatile SetAttribute<Disposal, TreatmentProcedure> treatmentProcedures;
	public static volatile SingularAttribute<Disposal, Registration> registration;
	public static volatile SetAttribute<Disposal, Tooth> teeth;
	public static volatile SingularAttribute<Disposal, Long> id;
	public static volatile SingularAttribute<Disposal, String> revisitComment;
	public static volatile SingularAttribute<Disposal, DisposalStatus> status;

}

