package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.service.AppointmentService;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.web.rest.vm.SameTreatmentVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * Spring Data  repository for the Disposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisposalRepository extends JpaRepository<Disposal, Long>, JpaSpecificationExecutor<Disposal> {

    Optional<DisposalTable> findByTreatmentProcedures_Id(Long treatmentProcedureId);

    List<SameTreatmentVM> findByRegistration_Appointment_Patient_IdAndDateTimeBetween(Long patientId, Instant begin, Instant end);

    Page<DisposalTable> findDisposalByRegistration_Appointment_Patient_Id(Long patientId, Pageable pageable);

    Optional<DisposalTable> findDisposalById(Long id);

    Optional<AppointmentService.AppointmentRegistrationDisposal> findByRegistration_Id(Long id);

    @Query("select disposal from Disposal disposal left join fetch disposal.prescription prescription " +
        "left join fetch prescription.treatmentDrugs left join fetch disposal.todo " +
        "where disposal.id = :id")
    Optional<Disposal> findWithEagerRelationshipsById(@Param("id") Long id);

    @Query("select disposal from Disposal disposal left join fetch disposal.todo " +
        "where disposal.createdDate between :start and :end and disposal.status = 'PERMANENT'")
    Stream<Disposal> findWithTodoByCreatedDateBetween(@Param("start") Instant start, @Param("end") Instant end);

    @Query(
        nativeQuery = true,
        value =
        "with patient_doctor_filter as ( " +
        "    select p.national_id as patientNid, " +
        "           eu.national_id as doctorNid " +
        "    from disposal d " +
        "    left join appointment a on d.registration_id = a.registration_id " +
        "    left join patient p on p.id = a.patient_id " +
        "    left join extend_user eu on a.doctor_user_id = eu.user_id " +
        "    where d.id = :disposalId " +
        ") " +
        "select netp.a71 as a71, " +
        "       netp.a73 as a73, " +
        "       netp.a74 as a74, " +
        "       netp.a75 as a75 " +
        "from disposal d2 " +
        "    left join treatment_procedure tp on d2.id = tp.disposal_id " +
        "    left join nhi_extend_disposal ned on d2.id = ned.disposal_id " +
        "    left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
        "    right join patient_doctor_filter pdf on pdf.patientNid = ned.a12 and pdf.doctorNid = ned.a15 " +
        "where a73 = :code " +
        "and trim(a18) <> '' " +
        "order by netp.a71 desc"
    )
    List<NhiExtendTreatmentProcedureTable> findDoctorOperationForPatientWithOnceWholeLifeLimitation(
        @Param("disposalId") Long disposalId,
        @Param("code") String code
    );

    Optional<DisposalTable> findFirstByRegistration_Appointment_Patient_IdOrderByDateTime(Long id);

    Optional<DisposalTable> findFirstByRegistration_Appointment_Patient_IdOrderByDateTimeDesc(Long id);
}
