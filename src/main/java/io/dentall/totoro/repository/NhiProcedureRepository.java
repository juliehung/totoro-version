package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.service.dto.PlainDisposalInfoListDTO;
import io.dentall.totoro.service.dto.table.NhiProcedureTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the NhiProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiProcedureRepository extends JpaRepository<NhiProcedure, Long> {
    Optional<NhiProcedureTable> findNhiProcedureById(Long id);

    List<PlainDisposalInfoListDTO> findByCodeInOrderByCode(List<String> code);
}
