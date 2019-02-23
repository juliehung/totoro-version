package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentProcedure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the TreatmentProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentProcedureRepository extends JpaRepository<TreatmentProcedure, Long>, JpaSpecificationExecutor<TreatmentProcedure> {

    @EntityGraph(attributePaths = "teeth")
    Optional<TreatmentProcedure> findOneWithTeethById(Long id);
}
