package io.dentall.totoro.service;

import io.dentall.totoro.domain.DentauthToken;
import io.dentall.totoro.repository.DentauthTokenRepository;
import io.dentall.totoro.security.SecurityUtils;
import io.dentall.totoro.security.jwt.TokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class DentauthService {

    private final DentauthTokenRepository dentauthTokenRepository;

    private final TokenProvider tokenProvider;

    public DentauthService(DentauthTokenRepository dentauthTokenRepository,
                           TokenProvider tokenProvider) {
        this.dentauthTokenRepository = dentauthTokenRepository;
        this.tokenProvider = tokenProvider;
    }

    public DentauthToken obtainToken() {
        return obtainToken(false);
    }

    public DentauthToken obtainToken(boolean forceCreate) {
        String username = SecurityUtils.getCurrentUserLogin().get();

        return findByUsername(username)
            .map(result -> {
                if (forceCreate) {
                    String token = tokenProvider.createDentauthToken(username);
                    result.setToken(token);
                }
                return result;
            })
            .orElseGet(() -> createToken(username).get());
    }

    public Optional<DentauthToken> findByUsername(String username) {
        return dentauthTokenRepository.findByUsername(username);
    }

    public Optional<DentauthToken> findByToken(String token) {
        return dentauthTokenRepository.findByToken(token);
    }

    private Optional<DentauthToken> createToken(String username) {
        String token = tokenProvider.createDentauthToken(username);
        DentauthToken dentauthToken = new DentauthToken();
        dentauthToken.setToken(token);
        dentauthToken.setUsername(username);
        dentauthTokenRepository.save(dentauthToken);

        return findByUsername(username);
    }

    public DentauthToken revokeToken() {
        String username = SecurityUtils.getCurrentUserLogin().get();
        Optional<DentauthToken> dentauthTokenOptional = findByUsername(username);

        if (dentauthTokenOptional.isPresent()) {
            DentauthToken dentauthToken = dentauthTokenOptional.get();
            dentauthTokenRepository.deleteById(dentauthToken.getId());
            return dentauthToken;
        } else {
            return null;
        }
    }

}
