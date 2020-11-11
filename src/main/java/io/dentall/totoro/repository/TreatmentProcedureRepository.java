package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.service.dto.table.TreatmentProcedureTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the TreatmentProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentProcedureRepository extends JpaRepository<TreatmentProcedure, Long>, JpaSpecificationExecutor<TreatmentProcedure> {

    List<TreatmentProcedureTable> findTop6ByDisposal_Registration_Appointment_Patient_IdOrderByDisposal_DateTimeDesc(Long patientId);

    List<TreatmentProcedureTable> findByDisposal_Registration_Appointment_Patient_IdAndDisposal_DateTimeBetweenOrderByDisposal_DateTimeDesc(Long id, Instant begin, Instant end);

    Set<TreatmentProcedureTable> findTreatmentProceduresByDisposal_Id(Long id);

    <T> Collection<T> findByDisposal_Id(Long id, Class<T> type);

    <T> Optional<T> findById(Long id, Class<T> type);

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
