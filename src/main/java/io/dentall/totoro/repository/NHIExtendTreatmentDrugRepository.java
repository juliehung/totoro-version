package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHIExtendTreatmentDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NHIExtendTreatmentDrugRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHIExtendTreatmentDrugRepository extends JpaRepository<NHIExtendTreatmentDrug, Long> {

}
