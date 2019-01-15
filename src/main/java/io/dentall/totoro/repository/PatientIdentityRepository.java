package io.dentall.totoro.repository;

import io.dentall.totoro.domain.PatientIdentity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientIdentityRepository extends JpaRepository<PatientIdentity, Long> {

}
