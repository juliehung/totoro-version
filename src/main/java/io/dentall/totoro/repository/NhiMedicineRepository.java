package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhiMedicineRepository extends JpaRepository<NhiMedicine, Long> {
    NhiMedicine findTop1ByMedicineCodeAndMedicineMandarinIsNotNullOrderByIdDesc(String code);
}
