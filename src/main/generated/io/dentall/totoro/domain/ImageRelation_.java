package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ImageRelation.class)
public abstract class ImageRelation_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<ImageRelation, Image> image;
	public static volatile SingularAttribute<ImageRelation, ImageRelationDomain> domain;
	public static volatile SingularAttribute<ImageRelation, Long> id;
	public static volatile SingularAttribute<ImageRelation, Long> domainId;

}

