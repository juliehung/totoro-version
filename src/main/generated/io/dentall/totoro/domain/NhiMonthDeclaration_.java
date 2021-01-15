package io.dentall.totoro.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiMonthDeclaration.class)
public abstract class NhiMonthDeclaration_ {

	public static volatile SingularAttribute<NhiMonthDeclaration, String> institution;
	public static volatile SingularAttribute<NhiMonthDeclaration, String> yearMonth;
	public static volatile SetAttribute<NhiMonthDeclaration, NhiMonthDeclarationDetails> nhiMonthDeclarationDetails;
	public static volatile SingularAttribute<NhiMonthDeclaration, Long> id;

}

