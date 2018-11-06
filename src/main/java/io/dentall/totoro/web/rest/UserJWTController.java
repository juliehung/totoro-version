package io.dentall.totoro.web.rest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.security.jwt.JWTFilter;
import io.dentall.totoro.security.jwt.TokenProvider;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.web.rest.vm.LoginVM;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<JWTTokenFirstLogin> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        Optional<Boolean> firstLogin = userService.getUserWithAuthorities().map(user -> user.getExtendUser().isFirstLogin());
        return new ResponseEntity<>(new JWTTokenFirstLogin(jwt, firstLogin.orElse(true)), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

    static class JWTTokenFirstLogin {

        private JWTToken jwtToken;

        private boolean firstLogin;

        JWTTokenFirstLogin(String idToken, boolean firstLogin) {
            this.jwtToken = new JWTToken(idToken);
            this.firstLogin = firstLogin;
        }

        @JsonUnwrapped
        JWTToken getJWTToken() {
            return jwtToken;
        }

        void setJWTToken(String idToken) {
            this.jwtToken = new JWTToken(idToken);
        }

        @JsonProperty("first_login")
        boolean getFirstLogin() {
            return firstLogin;
        }

        void setFirstLogin(boolean firstLogin) {
            this.firstLogin = firstLogin;
        }
    }

}
