package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
public class TreatmentProcedureTestOnly {

    private static final long serialVersionUID = 1L;

    private Long id;

    private TreatmentProcedureStatus status;

    private Integer quantity;

    private Double total;

    private String note;

    private Instant completedDate;

    private Double price;

    private String nhiCategory;

    private String nhiDescription;

    private String nhiIcd10Cm;

    private NhiProcedure nhiProcedure;

    private TreatmentTask treatmentTask;

    private Procedure procedure;

    private Appointment appointment;

    private Set<Tooth> teeth = new HashSet<>();

    private Set<Todo> todos = null;

    private Disposal disposal;

    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

    private Instant createdDate;

    private ExtendUser doctor;

}
