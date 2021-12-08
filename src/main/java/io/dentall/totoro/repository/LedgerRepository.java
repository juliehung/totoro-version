package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Ledger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Ledger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>, JpaSpecificationExecutor<Ledger> {
    List<Ledger> findByLedgerGroup_Id(Long gid);
}
