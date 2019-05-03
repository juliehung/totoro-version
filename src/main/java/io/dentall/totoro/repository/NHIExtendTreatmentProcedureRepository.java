package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHIExtendTreatmentProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NHIExtendTreatmentProcedureRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHIExtendTreatmentProcedureRepository extends JpaRepository<NHIExtendTreatmentProcedure, Long> {

}
