package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.service.dto.table.ProcedureTypeTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ProcedureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedureTypeRepository extends JpaRepository<ProcedureType, Long>, JpaSpecificationExecutor<ProcedureType> {
    Optional<ProcedureTypeTable> findProcedureTypeById(Long id);
}
