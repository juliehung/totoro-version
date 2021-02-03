package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TagType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public abstract class Tag_ {

	public static volatile SetAttribute<Tag, Patient> patients;
	public static volatile SingularAttribute<Tag, String> name;
	public static volatile SingularAttribute<Tag, Long> id;
	public static volatile SingularAttribute<Tag, TagType> type;
	public static volatile SingularAttribute<Tag, Boolean> modifiable;
	public static volatile SingularAttribute<Tag, Integer> order;

}

