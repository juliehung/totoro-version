package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;


/**
 * Spring Data  repository for the ImageRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRelationRepository extends JpaRepository<ImageRelation, Long>, JpaSpecificationExecutor<ImageRelation> {

    @Query("SELECT imageRelation FROM ImageRelation imageRelation WHERE imageRelation.domain = :domain AND imageRelation.domainId = :domainId")
    Stream<ImageRelation> findDistinctImageByDomainAndDomainId(@Param("domain") ImageRelationDomain domain, @Param("domainId") Long domainId);
}
