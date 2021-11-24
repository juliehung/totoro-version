package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.config.Constants;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.security.AuthoritiesConstants;
import io.dentall.totoro.service.MailService;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.dto.UserDTO;
import io.dentall.totoro.service.mapper.UserMapper;
import io.dentall.totoro.web.rest.errors.*;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.web.rest.vm.ManagedUserVM;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;

    private ApplicationContext applicationContext;

    private final UserMapper userMapper;

    public UserResource(
        UserService userService,
        UserRepository userRepository,
        MailService mailService,
        ApplicationContext applicationContext,
        UserMapper userMapper
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.applicationContext = applicationContext;
        this.userMapper = userMapper;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        if (managedUserVM.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(managedUserVM, managedUserVM.getPassword());
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", userDTO.getLogin()));
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Transactional
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) throws Exception {
        List<UserDTO> result = new ArrayList<>();
        ImageGcsBusinessService imageGcsBusinessService = applicationContext.getBean(ImageGcsBusinessService.class);

        Page<User> page = userService.getAllManagedUsersWithExtendUser(pageable);
        for (User user : page.getContent()) {
            UserDTO userDTO = userMapper.userToUserDTO(user);

            userService.transformLocalAvatarToGcsAvatar(user, imageGcsBusinessService);
            userService.appendImageUrlToUserDTO(user, userDTO, imageGcsBusinessService);

            result.add(userDTO);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Transactional
    public UserDTO getUser(@PathVariable String login) throws Exception {
        log.debug("REST request to get User : {}", login);
        ImageGcsBusinessService imageGcsBusinessService = applicationContext.getBean(ImageGcsBusinessService.class);

        User user = userService.getUserWithAuthoritiesByLogin(login)
            .orElseThrow(() -> new BadRequestAlertException("Can not found user by id", "USER", "notfound"));
        user.getExtendUser();
        UserDTO userDTO = userMapper.userToUserDTO(user);

        userService.transformLocalAvatarToGcsAvatar(user, imageGcsBusinessService);
        userService.appendImageUrlToUserDTO(user, userDTO, imageGcsBusinessService);

        return userDTO;
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "userManagement.deleted", login)).build();
    }

    /**
     * Post /users/reset-password : Admin reset the password of the user to login
     *
     * @param   login the username
     * @throws InternalServerErrorException 400 (Bad Request) if the login is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping("/users/{login:" + Constants.LOGIN_REGEX + "}/reset-password")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") OR hasRole(\"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<User> resetUserPassword(@PathVariable String login){
        return ResponseUtil.wrapOrNotFound(userService.resetPasswordbyAdmin(login));
    }
}
