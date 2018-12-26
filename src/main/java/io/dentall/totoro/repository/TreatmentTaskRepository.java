package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentTask;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TreatmentTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentTaskRepository extends JpaRepository<TreatmentTask, Long> {

}
