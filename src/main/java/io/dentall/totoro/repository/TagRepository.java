package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Tag;
import io.dentall.totoro.service.dto.table.TagTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;


/**
 * Spring Data  repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Collection<TagTable> findByPatientsId(Long id);

    @Query(
        nativeQuery = true,
        value = "select * from patient_tag left join tag on patient_tag.tags_id = tag.id where patients_id = :patientId"
    )
    Set<Tag> findByPatientId(@Param(value = "patientId") Long patientId);
}
