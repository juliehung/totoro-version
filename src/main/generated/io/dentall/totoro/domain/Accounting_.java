package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.AccountingOtherDealStatus;
import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Accounting.class)
public abstract class Accounting_ {

	public static volatile SingularAttribute<Accounting, BigDecimal> otherDealPrice;
	public static volatile SingularAttribute<Accounting, Double> other;
	public static volatile SingularAttribute<Accounting, Double> partialBurden;
	public static volatile SingularAttribute<Accounting, Double> ownExpense;
	public static volatile SingularAttribute<Accounting, Double> registrationFee;
	public static volatile SingularAttribute<Accounting, Double> discount;
	public static volatile SingularAttribute<Accounting, String> staff;
	public static volatile SingularAttribute<Accounting, Double> withdrawal;
	public static volatile SingularAttribute<Accounting, Instant> transactionTime;
	public static volatile SingularAttribute<Accounting, String> patientIdentity;
	public static volatile SingularAttribute<Accounting, Boolean> copaymentExemption;
	public static volatile SingularAttribute<Accounting, String> otherDealComment;
	public static volatile SingularAttribute<Accounting, Double> deposit;
	public static volatile SingularAttribute<Accounting, String> discountReason;
	public static volatile SingularAttribute<Accounting, Registration> registration;
	public static volatile SingularAttribute<Accounting, Long> id;
	public static volatile SingularAttribute<Accounting, Hospital> hospital;
	public static volatile SingularAttribute<Accounting, AccountingOtherDealStatus> otherDealStatus;

}

