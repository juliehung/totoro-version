package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.service.dto.table.TreatmentTaskTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * Spring Data  repository for the TreatmentTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentTaskRepository extends JpaRepository<TreatmentTask, Long>, JpaSpecificationExecutor<TreatmentTask> {

    Collection<TreatmentTaskTable> findByTreatmentPlan_Id(Long id);
}
