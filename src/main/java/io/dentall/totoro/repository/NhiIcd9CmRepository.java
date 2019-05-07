package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiIcd9Cm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiIcd9Cm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiIcd9CmRepository extends JpaRepository<NhiIcd9Cm, Long> {

}
