package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiProcedure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiProcedureRepository extends JpaRepository<NhiProcedure, Long> {

}
