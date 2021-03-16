package io.dentall.totoro.domain;

import java.time.Instant;
import java.util.Map;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DocNp.class)
public abstract class DocNp_ {

	public static volatile SingularAttribute<DocNp, Instant> createdDate;
	public static volatile SingularAttribute<DocNp, Long> patientId;
	public static volatile SingularAttribute<DocNp, String> createdBy;
	public static volatile SingularAttribute<DocNp, Map<java.lang.String,java.lang.Object>> patient;
	public static volatile SingularAttribute<DocNp, Long> esignId;
	public static volatile SingularAttribute<DocNp, Long> id;

}

