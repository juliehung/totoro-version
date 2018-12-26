package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHICategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the NHICategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHICategoryRepository extends JpaRepository<NHICategory, Long> {

    Optional<NHICategory> findByNameIgnoreCase(String name);
}
