package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMonthDeclaration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the NhiMonthDeclaration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiMonthDeclarationRepository extends JpaRepository<NhiMonthDeclaration, Long> {

    Optional<NhiMonthDeclaration> findByYearMonth(String ym);
}
