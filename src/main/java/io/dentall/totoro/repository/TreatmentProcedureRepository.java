package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;


/**
 * Spring Data  repository for the TreatmentProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentProcedureRepository extends JpaRepository<TreatmentProcedure, Long>, JpaSpecificationExecutor<TreatmentProcedure> {
    @Query(
        nativeQuery = true,
        value =
        "select treatment_procedure.created_date " +
        "from treatment, treatment_plan, treatment_task, treatment_procedure " +
        "where treatment.patient_id =:patientId " +
        "and treatment_plan.treatment_id = treatment.id " +
        "and treatment_task.treatment_plan_id = treatment_plan.id " +
        "and treatment_procedure.treatment_task_id = treatment_task.id " +
        "order by completed_date limit 1")
    Instant findPatientFirstProcedure(@Param(value = "patientId") Long id);
}
