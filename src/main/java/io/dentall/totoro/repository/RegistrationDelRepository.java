package io.dentall.totoro.repository;

import io.dentall.totoro.domain.RegistrationDel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegistrationDel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationDelRepository extends JpaRepository<RegistrationDel, Long>, JpaSpecificationExecutor<RegistrationDel> {

}
