package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Image.class)
public abstract class Image_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Image, String> fileName;
	public static volatile SingularAttribute<Image, Patient> patient;
	public static volatile SingularAttribute<Image, String> filePath;
	public static volatile SingularAttribute<Image, Long> id;

}

