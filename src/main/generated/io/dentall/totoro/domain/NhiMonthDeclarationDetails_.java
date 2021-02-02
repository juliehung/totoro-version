package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.NhiMonthDeclarationType;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiMonthDeclarationDetails.class)
public abstract class NhiMonthDeclarationDetails_ {

	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> preventiveCaseTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> generalPointTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> professionalCaseTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> pointTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, NhiMonthDeclaration> nhiMonthDeclaration;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, NhiMonthDeclarationType> type;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> outPatientPoint;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Instant> uploadTime;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, String> localId;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, String> way;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> preventivePointTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, String> file;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, String> nhiId;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> caseTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> partialPointTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Long> id;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> professionalPointTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> generalCaseTotal;
	public static volatile SingularAttribute<NhiMonthDeclarationDetails, Integer> partialCaseTotal;

}

