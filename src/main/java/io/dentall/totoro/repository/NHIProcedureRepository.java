package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHIProcedure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the NHIProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHIProcedureRepository extends JpaRepository<NHIProcedure, Long> {

}
