package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiExtendDisposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the NhiExtendDisposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiExtendDisposalRepository extends JpaRepository<NhiExtendDisposal, Long> {

    List<NhiExtendDisposal> findByDate(LocalDate date);
}
