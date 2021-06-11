package io.dentall.totoro.repository;

import io.dentall.totoro.domain.DentauthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DentauthTokenRepository extends JpaRepository<DentauthToken, Long>, JpaSpecificationExecutor<DentauthToken> {

    Optional<DentauthToken> findByUsername(String userName);

    Optional<DentauthToken> findByToken(String token);
}
