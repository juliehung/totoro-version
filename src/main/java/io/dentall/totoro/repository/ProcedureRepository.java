package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Procedure;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Procedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long>, JpaSpecificationExecutor<Procedure> {

    // https://stackoverflow.com/a/14567078
    @Query("select 1 from Procedure procedure where exists (select 1 from TreatmentProcedure treatmentProcedure where treatmentProcedure.procedure.id = :id)")
    Integer findAnyTreatmentProcedureByProcedureId(@Param("id") Long id);
}
