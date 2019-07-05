package io.dentall.totoro.repository;

import io.dentall.totoro.domain.CareerOptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CareerOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerOptionsRepository extends JpaRepository<CareerOptions, Long> {

}
