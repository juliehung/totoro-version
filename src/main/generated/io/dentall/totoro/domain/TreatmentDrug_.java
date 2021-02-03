package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TreatmentDrug.class)
public abstract class TreatmentDrug_ {

	public static volatile SingularAttribute<TreatmentDrug, Double> totalAmount;
	public static volatile SingularAttribute<TreatmentDrug, Double> quantity;
	public static volatile SingularAttribute<TreatmentDrug, Prescription> prescription;
	public static volatile SingularAttribute<TreatmentDrug, Long> id;
	public static volatile SingularAttribute<TreatmentDrug, Integer> day;
	public static volatile SingularAttribute<TreatmentDrug, String> way;
	public static volatile SingularAttribute<TreatmentDrug, NhiExtendTreatmentDrug> nhiExtendTreatmentDrug;
	public static volatile SingularAttribute<TreatmentDrug, String> frequency;
	public static volatile SingularAttribute<TreatmentDrug, Drug> drug;

}

