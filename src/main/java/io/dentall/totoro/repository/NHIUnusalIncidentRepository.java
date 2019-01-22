package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHIUnusalIncident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NHIUnusalIncident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHIUnusalIncidentRepository extends JpaRepository<NHIUnusalIncident, Long> {

}
