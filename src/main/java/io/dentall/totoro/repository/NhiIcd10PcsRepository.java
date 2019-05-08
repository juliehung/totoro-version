package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiIcd10Pcs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiIcd10Pcs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiIcd10PcsRepository extends JpaRepository<NhiIcd10Pcs, Long> {

}
