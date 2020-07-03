package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the NhiExtendTreatmentProcedureRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendTreatmentProcedureRepository extends JpaRepository<NhiExtendTreatmentProcedure, Long> {
    Set<NhiExtendTreatmentProcedure> findNhiExtendTreatmentProcedureByTreatmentProcedure_Disposal_Id(@Param(value = "disposalId") Long disposalId);

    Optional<NhiExtendTreatmentProcedureTable> findNhiExtendTreatmentProcedureByTreatmentProcedure_Id(Long id);

    <T> Optional<T> findById(Long id, Class<T> type);
}
