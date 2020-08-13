package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Calendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> { }
