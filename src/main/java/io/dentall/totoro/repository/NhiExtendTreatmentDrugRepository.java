package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentDrugTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the NhiExtendTreatmentDrugRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendTreatmentDrugRepository extends JpaRepository<NhiExtendTreatmentDrug, Long> {

    Set<NhiExtendTreatmentDrugTable> findNhiExtendTreatmentDrugsByTreatmentDrug_Prescription_Disposal_Id(@Param(value = "disposalId") Long disposalId);

    Optional<NhiExtendTreatmentDrugTable> findNhiExtendTreatmentDrugByTreatmentDrug_Id(Long id);

}
