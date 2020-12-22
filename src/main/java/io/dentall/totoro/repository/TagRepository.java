package io.dentall.totoro.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.dentall.totoro.domain.Tag;
import io.dentall.totoro.service.dto.TagDTO;
import io.dentall.totoro.service.dto.table.TagTable;


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

    @Query(value = "select new io.dentall.totoro.service.dto.TagDTO( "
            + " tag.id, "
            + " tag.type, "
            + " tag.name, "
            + " tag.modifiable, "
            + " tag.order, "
            + " patients.id "
            + ") "
            + "from Tag as tag left join tag.patients as patients "
            + "where patients.id in :patientIds")
    List<TagDTO> findByPatientIds(@Param(value = "patientIds") List<Long> patientIds);
}
