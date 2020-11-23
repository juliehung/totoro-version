package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ExtendUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ExtendUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtendUserRepository extends JpaRepository<ExtendUser, Long> {

    <T> Optional<T> findById(Long id, Class<T> type);

    @Query(
        nativeQuery = true,
        value = "select eu.* " +
            "from disposal d " +
            "left join appointment a on d.registration_id = a.registration_id " +
            "left join extend_user eu on a.doctor_user_id = eu.user_id " +
            "left join jhi_user ju on eu.user_id = ju.id " +
            "where a.patient_id = ? " +
            "order by a.expected_arrival_time desc " +
            "limit 1 "
    )
    ExtendUser findPatientLastDoctor(Long patientId);

    @Query(
        nativeQuery = true,
        value = "select eu.* " +
            "from disposal d " +
            "left join appointment a on d.registration_id = a.registration_id " +
            "left join extend_user eu on a.doctor_user_id = eu.user_id " +
            "left join jhi_user ju on eu.user_id = ju.id " +
            "where a.patient_id = ? " +
            "order by a.expected_arrival_time " +
            "limit 1 "
    )
    ExtendUser findPatientFirstDoctor(Long patientId);
}
