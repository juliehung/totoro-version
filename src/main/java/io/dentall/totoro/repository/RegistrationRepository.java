package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Registration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;


/**
 * Spring Data  repository for the Registration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByArrivalTimeBetweenOrderByArrivalTimeAsc(Instant start, Instant end);

    default void detach(EntityManager entityManager, Registration registration) {
        entityManager.detach(registration);
    }
}
