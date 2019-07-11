package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMonthDeclarationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiMonthDeclarationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiMonthDeclarationDetailsRepository extends JpaRepository<NhiMonthDeclarationDetails, Long>, JpaSpecificationExecutor<NhiMonthDeclarationDetails> {

}
