package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TreatmentDrug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentDrugRepository extends JpaRepository<TreatmentDrug, Long>, JpaSpecificationExecutor<TreatmentDrug> {

    boolean existsByDrugId(Long drugId);
}
