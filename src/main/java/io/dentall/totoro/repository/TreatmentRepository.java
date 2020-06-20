package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.service.dto.table.TreatmentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


/**
 * Spring Data  repository for the Treatment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long>, JpaSpecificationExecutor<Treatment> {

    Collection<TreatmentTable> findByPatient_Id(Long id);

    String eagerRelationships = "select distinct treatment from Treatment treatment left join fetch treatment.treatmentPlans treatmentPlan " +
        "left join fetch treatmentPlan.treatmentTasks treatmentTask left join fetch treatmentTask.treatmentProcedures ";

    @Query(eagerRelationships + "where treatment.id = :id")
    Optional<Treatment> findWithEagerRelationshipsById(@Param("id") Long id);
}
