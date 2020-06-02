package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.service.dto.table.TreatmentDrugsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;


/**
 * Spring Data  repository for the TreatmentDrug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentDrugRepository extends JpaRepository<TreatmentDrug, Long>, JpaSpecificationExecutor<TreatmentDrug> {

    Set<TreatmentDrugsTable> findTreatmentDrugByPrescription_Id(Long id);

    boolean existsByDrugId(Long drugId);
}
