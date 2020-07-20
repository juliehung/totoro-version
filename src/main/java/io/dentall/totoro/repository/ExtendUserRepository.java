package io.dentall.totoro.repository;

import io.dentall.totoro.domain.ExtendUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ExtendUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtendUserRepository extends JpaRepository<ExtendUser, Long> {

    <T> Optional<T> findById(Long id, Class<T> type);
}
