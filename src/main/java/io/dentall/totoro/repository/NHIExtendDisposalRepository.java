package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NHIExtendDisposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NHIExtendDisposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NHIExtendDisposalRepository extends JpaRepository<NHIExtendDisposal, Long> {

}
