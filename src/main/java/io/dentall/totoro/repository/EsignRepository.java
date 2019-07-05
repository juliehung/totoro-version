package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Esign;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Esign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EsignRepository extends JpaRepository<Esign, Long>, JpaSpecificationExecutor<Esign> {

}
