package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiExtendTreatmentProcedureRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendTreatmentProcedureRepository extends JpaRepository<NhiExtendTreatmentProcedure, Long> {

}
