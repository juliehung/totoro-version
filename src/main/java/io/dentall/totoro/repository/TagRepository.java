package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Tag;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


/**
 * Spring Data  repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(
        nativeQuery = true,
        value = "select * from patient_tag left join tag on patient_tag.tags_id = tag.id where patients_id = :patientId"
    )
    Set<Tag> findByPatientId(@Param(value = "patientId") Long patientId);
}
