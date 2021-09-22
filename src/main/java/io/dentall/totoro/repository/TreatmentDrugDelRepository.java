package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentDrugDel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TreatmentDrugDelRepository extends JpaRepository<TreatmentDrugDel, Long> {
    List<TreatmentDrugDel> findByPrescriptionIdAndIcCardEject(Long id, Boolean IcCardEject);
}
