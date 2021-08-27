package io.dentall.totoro.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiProcedure.class)
public abstract class NhiProcedure_ {

	public static volatile SingularAttribute<NhiProcedure, String> englishName;
	public static volatile SingularAttribute<NhiProcedure, String> code;
	public static volatile SingularAttribute<NhiProcedure, Long> defaultIcd10CmId;
	public static volatile SingularAttribute<NhiProcedure, Instant> effectiveTime;
	public static volatile SingularAttribute<NhiProcedure, String> description;
	public static volatile SingularAttribute<NhiProcedure, String> fdi;
	public static volatile SingularAttribute<NhiProcedure, NhiProcedureType> nhiProcedureType;
	public static volatile SingularAttribute<NhiProcedure, NhiIcd9Cm> nhiIcd9Cm;
	public static volatile SingularAttribute<NhiProcedure, String> chiefComplaint;
	public static volatile SingularAttribute<NhiProcedure, Integer> point;
	public static volatile SingularAttribute<NhiProcedure, Instant> expirationTime;
	public static volatile SingularAttribute<NhiProcedure, String> name;
	public static volatile SingularAttribute<NhiProcedure, String> exclude;
	public static volatile SingularAttribute<NhiProcedure, Long> id;
	public static volatile SingularAttribute<NhiProcedure, String> specificCode;
	public static volatile SetAttribute<NhiProcedure, NhiIcd10Pcs> nhiIcd10Pcs;

}

