package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.SettingType;
import java.util.Map;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Setting.class)
public abstract class Setting_ {

	public static volatile SingularAttribute<Setting, Map<java.lang.String,java.lang.Object>> preferences;
	public static volatile SingularAttribute<Setting, Long> id;
	public static volatile SingularAttribute<Setting, SettingType> type;

}

