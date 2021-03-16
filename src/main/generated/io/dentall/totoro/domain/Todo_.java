package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TodoStatus;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Todo.class)
public abstract class Todo_ extends io.dentall.totoro.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Todo, String> note;
	public static volatile SingularAttribute<Todo, Patient> patient;
	public static volatile SetAttribute<Todo, TreatmentProcedure> treatmentProcedures;
	public static volatile SingularAttribute<Todo, Integer> requiredTreatmentTime;
	public static volatile SingularAttribute<Todo, Long> id;
	public static volatile SingularAttribute<Todo, LocalDate> expectedDate;
	public static volatile SingularAttribute<Todo, Disposal> disposal;
	public static volatile SingularAttribute<Todo, TodoStatus> status;

}

