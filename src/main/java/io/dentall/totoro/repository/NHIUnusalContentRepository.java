package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHIUnusalContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NHIUnusalContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHIUnusalContentRepository extends JpaRepository<NHIUnusalContent, Long> {

}
