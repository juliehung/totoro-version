package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.service.dto.PlainDisposalInfoDTO;
import io.dentall.totoro.service.dto.PlainDisposalInfoListDTO;
import io.dentall.totoro.service.dto.table.TreatmentProcedureTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(
        countQuery =
            "select count(*)" +
                "from treatment_procedure tp " +
                "left join nhi_procedure np on tp.nhi_procedure_id = np.id " +
                "left join disposal d on tp.disposal_id = d.id " +
                "left join registration r on d.registration_id = r.id " +
                "left join appointment a on a.registration_id = r.id " +
                "left join patient p on a.patient_id = p.id " +
                "left join jhi_user ju on a.doctor_user_id = ju.id " +
                "where r.arrival_time is not null " +
                "and tp.nhi_procedure_id is not null " +
                "and np.id = :id " +
                "and r.arrival_time between :begin and :end ",
        nativeQuery = true,
        value =
            "select r.arrival_time as arrivalTime, " +
                "ju.first_name as doctorName, " +
                "p.id as patientId, " +
                "p.name as patientName, " +
                "p.birth as birth, " +
                "np.code as infoContent, " +
                "p.phone as phone, " +
                "p.note as note " +
                "from treatment_procedure tp " +
                "left join nhi_procedure np on tp.nhi_procedure_id = np.id " +
                "left join disposal d on tp.disposal_id = d.id " +
                "left join registration r on d.registration_id = r.id " +
                "left join appointment a on a.registration_id = r.id " +
                "left join patient p on a.patient_id = p.id " +
                "left join jhi_user ju on a.doctor_user_id = ju.id " +
                "where r.arrival_time is not null " +
                "and tp.nhi_procedure_id is not null " +
                "and np.id = :id " +
                "and ju.id in (:doctorIds) " +
                "and r.arrival_time between :begin and :end " +
                "order by ju.id, r.arrival_time "
    )
    Page<PlainDisposalInfoDTO> findPlainDisposalNhiTx(
        @Param(value="begin") Instant beginTime,
        @Param(value="end") Instant endTime,
        @Param(value="doctorIds") List<Long> doctorIds,
        @Param(value="id") Long nhiProcedureId,
        Pageable page);

    @Query(
        countQuery =
            "select count(*)" +
                "from treatment_procedure tp " +
                "left join procedure np on tp.procedure_id = np.id " +
                "left join disposal d on tp.disposal_id = d.id " +
                "left join registration r on d.registration_id = r.id " +
                "left join appointment a on a.registration_id = r.id " +
                "left join patient p on a.patient_id = p.id " +
                "left join jhi_user ju on a.doctor_user_id = ju.id " +
                "where r.arrival_time is not null " +
                "and tp.procedure_id is not null " +
                "and np.id = :id " +
                "and r.arrival_time between :begin and :end ",
        nativeQuery = true,
        value =
            "select r.arrival_time as arrivalTime, " +
                "ju.first_name as doctorName, " +
                "p.id as patientId, " +
                "p.name as patientName, " +
                "p.birth as birth, " +
                "np.content as infoContent, " +
                "p.phone as phone, " +
                "p.note as note " +
                "from treatment_procedure tp " +
                "left join procedure np on tp.procedure_id = np.id " +
                "left join disposal d on tp.disposal_id = d.id " +
                "left join registration r on d.registration_id = r.id " +
                "left join appointment a on a.registration_id = r.id " +
                "left join patient p on a.patient_id = p.id " +
                "left join jhi_user ju on a.doctor_user_id = ju.id " +
                "where r.arrival_time is not null " +
                "and tp.procedure_id is not null " +
                "and np.id = :id " +
                "and ju.id in (:doctorIds) " +
                "and r.arrival_time between :begin and :end " +
                "order by ju.id, r.arrival_time "
    )
    Page<PlainDisposalInfoDTO> findPlainDisposalNoneNhiTx(
        @Param(value="begin") Instant beginTime,
        @Param(value="end") Instant endTime,
        @Param(value="doctorIds") List<Long> doctorIds,
        @Param(value="id") Long nhiProcedureId,
        Pageable page);
}
