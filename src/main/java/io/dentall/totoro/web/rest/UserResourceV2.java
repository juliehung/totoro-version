package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.config.Constants;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.security.AuthoritiesConstants;
import io.dentall.totoro.service.UserServiceV2;
import io.dentall.totoro.service.dto.UserDTO;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.errors.EmailAlreadyUsedException;
import io.dentall.totoro.web.rest.errors.InternalServerErrorException;
import io.dentall.totoro.web.rest.errors.LoginAlreadyUsedException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.AvatarVM;
import io.dentall.totoro.web.rest.vm.ManagedUserV2VM;
import io.dentall.totoro.web.rest.vm.UserV2VM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResourceV2 {

    private final Logger log = LoggerFactory.getLogger(UserResourceV2.class);

    private final UserServiceV2 userServiceV2;

    private final UserRepository userRepository;

    public UserResourceV2(UserServiceV2 userServiceV2, UserRepository userRepository) {
        this.userServiceV2 = userServiceV2;
        this.userRepository = userRepository;
    }

    /**
     * POST  /v2/users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserV2VM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/v2/users")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<UserV2VM> createUser(@Valid @RequestBody ManagedUserV2VM managedUserV2VM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserV2VM);

        if (managedUserV2VM.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserV2VM.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(managedUserV2VM.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userServiceV2.createUser(managedUserV2VM, managedUserV2VM.getPassword());
            UserV2VM newUserVm = new UserV2VM(new UserDTO(newUser));
            return ResponseEntity.created(new URI("/api/v2/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
                .body(newUserVm);
        }
    }

    /**
     * PUT /v2/users : Updates an existing User.
     *
     * @param userV2VM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/v2/users")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<UserV2VM> updateUser(@Valid @RequestBody UserV2VM userV2VM) {
        log.debug("REST request to update User V2: {}", userV2VM);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userV2VM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userV2VM.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userV2VM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userV2VM.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserV2VM> updatedUser = userServiceV2.updateUser(userV2VM);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", userV2VM.getLogin()));
    }

    /**
     * GET /v2/users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/v2/users")
    @Timed
    public ResponseEntity<List<UserV2VM>> getAllUsers(Pageable pageable) {
        final Page<UserV2VM> page = userServiceV2.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/v2/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/v2/users/authorities")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public List<String> getAuthorities() {
        return userServiceV2.getAuthorities();
    }

    /**
     * GET /v2/users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/v2/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserV2VM> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userServiceV2.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new)
                .map(UserV2VM::new)
        );
    }

    /**
     * GET /v2/users/:id/avatar : get the user's avatar.
     *
     * @return AvatarVM object body
     */
    @GetMapping("/v2/users/{id}/avatar")
    @Timed
    public ResponseEntity<AvatarVM> getAvatar(@PathVariable Long id) {
        Optional<AvatarVM> avatar = userServiceV2.getUserWithAuthorities(id)
            .map(User::getExtendUser)
            .map(AvatarVM::new);
        return ResponseUtil.wrapOrNotFound(avatar);
    }

    /**
     * DELETE /v2/users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/v2/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userServiceV2.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "userManagement.deleted", login)).build();
    }

    /**
     * Post /v2/users/{login}/reset-password : Admin reset the password of the user to login
     *
     * @param   login the username
     * @throws InternalServerErrorException 400 (Bad Request) if the login is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping("/v2/users/{login:" + Constants.LOGIN_REGEX + "}/reset-password")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<UserV2VM> resetUserPassword(@PathVariable String login){
        return ResponseUtil.wrapOrNotFound(userServiceV2.resetPasswordByAdmin(login));
    }
}
