package io.dentall.totoro.repository;

import io.dentall.totoro.domain.UserDayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserDayOff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDayOffRepository extends JpaRepository<UserDayOff, Long>, JpaSpecificationExecutor<UserDayOff> {
}
