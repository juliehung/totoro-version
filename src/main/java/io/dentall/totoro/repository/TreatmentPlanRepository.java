package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.service.dto.table.TreatmentPlanTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * Spring Data  repository for the TreatmentPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long>, JpaSpecificationExecutor<TreatmentPlan> {

    Collection<TreatmentPlanTable> findByTreatment_Id(Long id);
}
