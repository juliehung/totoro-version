package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "select distinct patient from Patient patient left join fetch patient.parents left join fetch patient.spouse1S left join fetch patient.tags",
        countQuery = "select count(distinct patient) from Patient patient")
    Page<Patient> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct patient from Patient patient left join fetch patient.parents left join fetch patient.spouse1S left join fetch patient.tags")
    List<Patient> findAllWithEagerRelationships();

    @Query("select patient from Patient patient left join fetch patient.parents left join fetch patient.spouse1S left join fetch patient.tags where patient.id =:id")
    Optional<Patient> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select patient from Patient patient where patient.name like %:keyword% or (patient.birth is not null and to_char(patient.birth, 'yyyyMMdd') like %:keyword%)")
    List<Patient> findByKeywordContaining(@Param("keyword") String keyword);
}
