package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentProcedureDel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TreatmentProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentProcedureDelRepository extends JpaRepository<TreatmentProcedureDel, Long> {
    List<TreatmentProcedureDel> findByDisposalIdAndIcCardEject(Long id, Boolean IcCardEject);
}
