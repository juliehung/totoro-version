package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.SourceType;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Esign.class)
public abstract class Esign_ {

	public static volatile SingularAttribute<Esign, Instant> createdDate;
	public static volatile SingularAttribute<Esign, Long> patientId;
	public static volatile SingularAttribute<Esign, String> createdBy;
	public static volatile SingularAttribute<Esign, SourceType> sourceType;
	public static volatile SingularAttribute<Esign, Long> id;
	public static volatile SingularAttribute<Esign, byte[]> lob;
	public static volatile SingularAttribute<Esign, String> lobContentType;

}

