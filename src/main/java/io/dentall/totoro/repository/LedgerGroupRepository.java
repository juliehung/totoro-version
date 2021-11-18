package io.dentall.totoro.repository;

import io.dentall.totoro.domain.LedgerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerGroupRepository extends JpaRepository<LedgerGroup, Long> {

    List<LedgerGroup> findByPatientId(Long patientId);

}
