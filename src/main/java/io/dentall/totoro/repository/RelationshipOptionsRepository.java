package io.dentall.totoro.repository;

import io.dentall.totoro.domain.RelationshipOptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RelationshipOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelationshipOptionsRepository extends JpaRepository<RelationshipOptions, Long> {

}
