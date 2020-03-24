package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ConfigurationMap;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConfigurationMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurationMapRepository extends JpaRepository<ConfigurationMap, Long>, JpaSpecificationExecutor<ConfigurationMap> {
}
