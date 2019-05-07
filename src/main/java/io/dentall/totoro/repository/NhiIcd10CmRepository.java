package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiIcd10Cm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiIcd10Cm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiIcd10CmRepository extends JpaRepository<NhiIcd10Cm, Long> {

}
