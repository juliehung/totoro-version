package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Tooth;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tooth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToothRepository extends JpaRepository<Tooth, Long>, JpaSpecificationExecutor<Tooth> {

}
