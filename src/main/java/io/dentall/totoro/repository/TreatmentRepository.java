package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Treatment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Treatment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long>, JpaSpecificationExecutor<Treatment> {

}
