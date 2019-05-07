package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiProcedureType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiProcedureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiProcedureTypeRepository extends JpaRepository<NhiProcedureType, Long> {

}
