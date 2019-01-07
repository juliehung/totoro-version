package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Accounting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Accounting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountingRepository extends JpaRepository<Accounting, Long> {

}
