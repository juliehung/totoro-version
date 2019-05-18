package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiExtendTreatmentDrugRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendTreatmentDrugRepository extends JpaRepository<NhiExtendTreatmentDrug, Long> {

}
