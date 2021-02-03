package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PatientIdentity.class)
public abstract class PatientIdentity_ {

	public static volatile SingularAttribute<PatientIdentity, String> code;
	public static volatile SingularAttribute<PatientIdentity, Boolean> freeBurden;
	public static volatile SingularAttribute<PatientIdentity, String> name;
	public static volatile SingularAttribute<PatientIdentity, Long> id;
	public static volatile SingularAttribute<PatientIdentity, Boolean> enabled;

}

