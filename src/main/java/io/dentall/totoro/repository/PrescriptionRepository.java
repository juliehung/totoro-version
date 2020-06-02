package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.service.dto.table.PrescriptionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Prescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long>, JpaSpecificationExecutor<Prescription> {
    Optional<PrescriptionTable> findPrescriptionById(Long id);
}
