package io.dentall.totoro.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiDayUpload.class)
public abstract class NhiDayUpload_ {

	public static volatile SingularAttribute<NhiDayUpload, LocalDate> date;
	public static volatile SetAttribute<NhiDayUpload, NhiDayUploadDetails> nhiDayUploadDetails;
	public static volatile SingularAttribute<NhiDayUpload, Long> id;

}

