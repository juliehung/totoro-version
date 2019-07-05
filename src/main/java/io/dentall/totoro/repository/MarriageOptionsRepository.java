package io.dentall.totoro.repository;

import io.dentall.totoro.domain.MarriageOptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MarriageOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarriageOptionsRepository extends JpaRepository<MarriageOptions, Long> {

}
