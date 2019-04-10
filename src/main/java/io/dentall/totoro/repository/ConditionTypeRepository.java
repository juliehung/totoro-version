package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ConditionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConditionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionTypeRepository extends JpaRepository<ConditionType, Long>, JpaSpecificationExecutor<ConditionType> {

}
