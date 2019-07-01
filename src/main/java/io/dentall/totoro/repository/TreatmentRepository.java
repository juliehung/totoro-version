package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Treatment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Treatment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long>, JpaSpecificationExecutor<Treatment> {

    String eagerRelationships = "select distinct treatment from Treatment treatment left join fetch treatment.treatmentPlans treatmentPlan " +
        "left join fetch treatmentPlan.treatmentTasks treatmentTask left join fetch treatmentTask.treatmentProcedures ";

    @Query(eagerRelationships + "where treatment.id = :id")
    Optional<Treatment> findWithEagerRelationshipsById(@Param("id") Long id);
}
