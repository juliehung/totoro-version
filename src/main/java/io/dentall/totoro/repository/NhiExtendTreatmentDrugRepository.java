package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentDrugTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the NhiExtendTreatmentDrugRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendTreatmentDrugRepository extends JpaRepository<NhiExtendTreatmentDrug, Long> {

    Optional<NhiExtendTreatmentDrugTable> findNhiExtendTreatmentDrugByTreatmentDrug_Id(Long id);

}
