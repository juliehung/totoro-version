package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.HomePageCoverSourceTable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HomePageCover.class)
public abstract class HomePageCover_ {

	public static volatile SingularAttribute<HomePageCover, Long> sourceId;
	public static volatile SingularAttribute<HomePageCover, HomePageCoverSourceTable> sourceTable;
	public static volatile SingularAttribute<HomePageCover, Long> patientId;

}

