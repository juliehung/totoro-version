package io.dentall.totoro.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ledger.class)
public abstract class Ledger_ {

	public static volatile SingularAttribute<Ledger, Instant> date;
	public static volatile SingularAttribute<Ledger, String> note;
	public static volatile SingularAttribute<Ledger, Double> amount;
	public static volatile SingularAttribute<Ledger, Double> charge;
	public static volatile SingularAttribute<Ledger, Long> gid;
	public static volatile SingularAttribute<Ledger, Double> arrears;
	public static volatile SingularAttribute<Ledger, Long> patientId;
	public static volatile SingularAttribute<Ledger, Instant> lastModifiedDate;
	public static volatile SingularAttribute<Ledger, String> displayName;
	public static volatile SingularAttribute<Ledger, String> lastModifiedBy;
	public static volatile SingularAttribute<Ledger, String> type;
	public static volatile SingularAttribute<Ledger, String> doctor;
	public static volatile SingularAttribute<Ledger, Instant> createdDate;
	public static volatile SingularAttribute<Ledger, String> projectCode;
	public static volatile SingularAttribute<Ledger, String> createdBy;
	public static volatile SingularAttribute<Ledger, Long> id;
	public static volatile SingularAttribute<Ledger, Boolean> includeStampTax;
	public static volatile SingularAttribute<Ledger, Instant> printTime;
	public static volatile SingularAttribute<Ledger, TreatmentPlan> treatmentPlan;

}

