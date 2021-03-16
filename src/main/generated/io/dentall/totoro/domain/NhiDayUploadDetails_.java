package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.NhiDayUploadDetailType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiDayUploadDetails.class)
public abstract class NhiDayUploadDetails_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SetAttribute<NhiDayUploadDetails, NhiExtendDisposal> nhiExtendDisposals;
	public static volatile SingularAttribute<NhiDayUploadDetails, NhiDayUpload> nhiDayUpload;
	public static volatile SingularAttribute<NhiDayUploadDetails, Long> id;
	public static volatile SingularAttribute<NhiDayUploadDetails, NhiDayUploadDetailType> type;

}

