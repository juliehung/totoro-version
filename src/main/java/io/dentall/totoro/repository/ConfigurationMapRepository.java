package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ConfigurationMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the ConfigurationMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurationMapRepository extends JpaRepository<ConfigurationMap, Long>, JpaSpecificationExecutor<ConfigurationMap> {

    Optional<ConfigurationMap> findByConfigKey(String key);
}
