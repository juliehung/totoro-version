package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;


/**
 * 2021.12.02
 * 改使用 {@link PatientDocumentRepository}
 * <p>
 * Spring Data  repository for the ImageRelation entity.
 */
@SuppressWarnings("unused")
@Deprecated
@Repository
public interface ImageRelationRepository extends JpaRepository<ImageRelation, Long>, JpaSpecificationExecutor<ImageRelation> {

    @Deprecated
    @EntityGraph(attributePaths = "image")
    @Query("SELECT imageRelation FROM ImageRelation imageRelation WHERE imageRelation.domain = :domain AND imageRelation.domainId = :domainId")
    Stream<ImageRelation> findDistinctImageByDomainAndDomainId(@Param("domain") ImageRelationDomain domain, @Param("domainId") Long domainId);

    @Deprecated
    Page<ImageRelation> findByDomainAndImage_Patient_IdOrderByDomainIdDesc(ImageRelationDomain domain, Long patientId, Pageable pageable);

    List<ImageRelation> findImageRelationsByDomainAndDomainIdAndImage_IdAndImage_Patient_Id(ImageRelationDomain domain, Long domainId, Long imageId, Long patientId);
}
