package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHIProcedureType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NHIProcedureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHIProcedureTypeRepository extends JpaRepository<NHIProcedureType, Long> {

}
