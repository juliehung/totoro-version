package io.dentall.totoro.repository;

import io.dentall.totoro.domain.HomePageCover;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HomePageCover entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HomePageCoverRepository extends JpaRepository<HomePageCover, Long>, JpaSpecificationExecutor<HomePageCover> {

}
