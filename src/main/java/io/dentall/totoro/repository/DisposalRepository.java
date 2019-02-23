package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Disposal;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Disposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisposalRepository extends JpaRepository<Disposal, Long>, JpaSpecificationExecutor<Disposal> {

    @Query("select disposal from Disposal disposal left join fetch disposal.treatmentProcedures treatmentProcedure " +
        "left join fetch treatmentProcedure.teeth left join fetch disposal.prescription prescription " +
        "left join fetch prescription.treatmentDrugs left join fetch disposal.todo todo " +
        "left join fetch todo.treatmentProcedures todoTreatmentProcedure left join fetch todoTreatmentProcedure.teeth " +
        "where disposal.id = :id")
    Optional<Disposal> findWithEagerRelationshipsById(@Param("id") Long id);
}
