package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Procedure;
import io.dentall.totoro.service.dto.table.ProcedureTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Procedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long>, JpaSpecificationExecutor<Procedure> {

    Optional<ProcedureTable> findProcedureById(Long id);

    // https://stackoverflow.com/a/14567078
    @Query("select 1 from Procedure procedure where exists (select 1 from TreatmentProcedure treatmentProcedure where treatmentProcedure.procedure.id = :id)")
    Integer findAnyTreatmentProcedureByProcedureId(@Param("id") Long id);
}
