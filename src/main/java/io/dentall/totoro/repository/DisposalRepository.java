package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.service.dto.table.DisposalTable;
import org.springframework.data.jpa.repository.*;
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

    List<DisposalTable> findDisposalByPatient_Id(Long patientId);

    Optional<DisposalTable> findDisposalById(Long id);

    @Query("select disposal from Disposal disposal left join fetch disposal.prescription prescription " +
        "left join fetch prescription.treatmentDrugs left join fetch disposal.todo " +
        "where disposal.id = :id")
    Optional<Disposal> findWithEagerRelationshipsById(@Param("id") Long id);

    @Query("select disposal from Disposal disposal left join fetch disposal.todo " +
        "where disposal.createdDate between :start and :end and disposal.status = 'PERMANENT'")
    Stream<Disposal> findWithTodoByCreatedDateBetween(@Param("start") Instant start, @Param("end") Instant end);
}
