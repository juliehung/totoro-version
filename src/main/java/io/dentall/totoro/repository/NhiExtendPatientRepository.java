package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendPatient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiExtendPatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendPatientRepository extends JpaRepository<NhiExtendPatient, Long> {

}
