package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.service.AppointmentService;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.web.rest.vm.SameTreatmentVM;
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

    List<DisposalTable> findDisposalByRegistration_Appointment_Patient_Id(Long patientId, Pageable pageable);

    Optional<DisposalTable> findDisposalById(Long id);

    Optional<AppointmentService.AppointmentRegistrationDisposal> findByRegistration_Id(Long id);

    @Query("select disposal from Disposal disposal left join fetch disposal.prescription prescription " +
        "left join fetch prescription.treatmentDrugs left join fetch disposal.todo " +
        "where disposal.id = :id")
    Optional<Disposal> findWithEagerRelationshipsById(@Param("id") Long id);

    @Query("select disposal from Disposal disposal left join fetch disposal.todo " +
        "where disposal.createdDate between :start and :end and disposal.status = 'PERMANENT'")
    Stream<Disposal> findWithTodoByCreatedDateBetween(@Param("start") Instant start, @Param("end") Instant end);
}
