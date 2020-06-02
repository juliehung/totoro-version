package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiDayUploadDetailsNhiExtendDisposal;
import io.dentall.totoro.domain.NhiDayUploadDetailsNhiExtendDisposalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NhiDayUploadDetailsNhiExtendDisposalRepository extends JpaRepository<NhiDayUploadDetailsNhiExtendDisposal, NhiDayUploadDetailsNhiExtendDisposalId> {

    Set<NhiDayUploadDetailsNhiExtendDisposal> findById_NhiDayUploadDetailsId(Long id);

    Set<NhiDayUploadDetailsNhiExtendDisposal> findById_NhiExtendDisposalsId(Long id);

}
