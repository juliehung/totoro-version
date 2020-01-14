package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ImageRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImageRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRelationRepository extends JpaRepository<ImageRelation, Long>, JpaSpecificationExecutor<ImageRelation> {

}
