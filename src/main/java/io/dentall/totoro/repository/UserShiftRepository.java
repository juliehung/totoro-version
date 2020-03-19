package io.dentall.totoro.repository;

import io.dentall.totoro.domain.UserShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserShift entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserShiftRepository extends JpaRepository<UserShift, Long>, JpaSpecificationExecutor<UserShift> {
}
