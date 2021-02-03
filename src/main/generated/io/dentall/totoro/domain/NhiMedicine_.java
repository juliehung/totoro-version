package io.dentall.totoro.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiMedicine.class)
public abstract class NhiMedicine_ {

	public static volatile SingularAttribute<NhiMedicine, String> medicineCode;
	public static volatile SingularAttribute<NhiMedicine, LocalDate> updateDate;
	public static volatile SingularAttribute<NhiMedicine, Long> id;
	public static volatile SingularAttribute<NhiMedicine, String> medicineMandarin;
	public static volatile SingularAttribute<NhiMedicine, Long> version;

}

