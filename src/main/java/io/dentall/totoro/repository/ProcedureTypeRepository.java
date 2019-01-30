package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ProcedureType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProcedureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedureTypeRepository extends JpaRepository<ProcedureType, Long> {

}
