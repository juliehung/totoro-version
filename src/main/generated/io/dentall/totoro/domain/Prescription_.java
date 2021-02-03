package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.PrescriptionMode;
import io.dentall.totoro.domain.enumeration.PrescriptionStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Prescription.class)
public abstract class Prescription_ {

	public static volatile SingularAttribute<Prescription, PrescriptionMode> mode;
	public static volatile SingularAttribute<Prescription, Boolean> clinicAdministration;
	public static volatile SetAttribute<Prescription, TreatmentDrug> treatmentDrugs;
	public static volatile SingularAttribute<Prescription, Boolean> pain;
	public static volatile SingularAttribute<Prescription, Boolean> takenAll;
	public static volatile SingularAttribute<Prescription, Long> id;
	public static volatile SingularAttribute<Prescription, Boolean> antiInflammatoryDrug;
	public static volatile SingularAttribute<Prescription, Disposal> disposal;
	public static volatile SingularAttribute<Prescription, PrescriptionStatus> status;

}

