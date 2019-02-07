package io.dentall.totoro.repository;

import io.dentall.totoro.domain.FindingType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FindingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FindingTypeRepository extends JpaRepository<FindingType, Long>, JpaSpecificationExecutor<FindingType> {

}
