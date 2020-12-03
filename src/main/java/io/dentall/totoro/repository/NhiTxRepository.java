package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiTx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhiTxRepository extends JpaRepository<NhiTx, Long> {
    NhiTx findTop1ByNhiCodeAndNhiMandarinIsNotNullOrderByIdDesc(String code);
}
