package io.dentall.totoro.service;

import io.dentall.totoro.config.Constants;
import io.dentall.totoro.domain.Authority;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Specialist;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.domain.enumeration.TotoroErrorKey;
import io.dentall.totoro.repository.AuthorityRepository;
import io.dentall.totoro.repository.SpecialistRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.dto.UserDTO;
import io.dentall.totoro.service.util.CacheUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.UserV2VM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing users, with the new UserV2VM object.
 */
@Service
@Transactional
public class UserServiceV2 {

    private final Logger log = LoggerFactory.getLogger(UserServiceV2.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final SpecialistRepository specialistRepository;

    private final CacheManager cacheManager;

    public UserServiceV2(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        SpecialistRepository specialistRepository,
        CacheManager cacheManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.specialistRepository = specialistRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<UserV2VM> resetPasswordByAdmin(String login) {
        log.debug("Reset user password");
        return userRepository.findOneByLogin(login)
            .map(user -> {
                user.setPassword(passwordEncoder.encode(login));
                user.getExtendUser().setFirstLogin(true);
                this.clearUserCaches(user);
                return user;
            })
            .map(UserDTO::new)
            .map(UserV2VM::new);
    }

    public User createUser(UserV2VM userV2VM, String password) {
        User user = new User();
        user.setLogin(userV2VM.getLogin().toLowerCase());
        user.setFirstName(userV2VM.getFirstName());
        user.setLastName(userV2VM.getLastName());
        user.setEmail(userV2VM.getEmail() == null ? null : userV2VM.getEmail().toLowerCase());
        user.setImageUrl(userV2VM.getImageUrl());

        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userV2VM.getAuthorities() != null) {
            Set<Authority> authorities = userV2VM.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }

        user.setExtendUser(createExtendUser(user, userV2VM));
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userV2VM user to update
     * @return updated user
     */
    public Optional<UserV2VM> updateUser(UserV2VM userV2VM) {
        return Optional.of(userRepository
            .findById(userV2VM.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                if (userV2VM.getLogin() != null) { user.setLogin(userV2VM.getLogin().toLowerCase()); }
                if (userV2VM.getFirstName() != null) { user.setFirstName(userV2VM.getFirstName()); }
                if (userV2VM.getLastName() != null) { user.setLastName(userV2VM.getLastName()); }
                if (userV2VM.getEmail() != null) { user.setEmail(userV2VM.getEmail().toLowerCase()); }
                if (userV2VM.getImageUrl() != null) { user.setImageUrl(userV2VM.getImageUrl()); }
                if (userV2VM.isActivated() != null) { user.setActivated(userV2VM.isActivated()); }
                if (userV2VM.getAuthorities() != null) {
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userV2VM.getAuthorities().stream()
                        .map(authorityRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(managedAuthorities::add);
                }
                if (userV2VM.getSpecialists() != null) {
                    user.setSpecialists(
                        userV2VM.getSpecialists().stream()
                            .filter(specialist -> specialist != null && specialist.getId() != null)
                            .map(Specialist::getId)
                            .map(specialistId -> specialistRepository.findById(specialistId)
                                    .orElseThrow(() -> new BadRequestAlertException("Not exist this specialist", "specialist", TotoroErrorKey.NOT_FOUND_DATA.toString()))
                            )
                            .collect(Collectors.toSet())
                    );
                }

                updateExtendUser(user.getExtendUser(), userV2VM);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new)
            .map(UserV2VM::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserV2VM> getAllManagedUsers(Pageable pageable) {
        return userRepository
            .findAllByLoginNot(pageable, Constants.ANONYMOUS_USER)
            .map(UserDTO::new)
            .map(UserV2VM::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private ExtendUser createExtendUser(User user, UserV2VM userV2VM) {
        ExtendUser extendUser = new ExtendUser().user(user);
        updateExtendUser(extendUser, userV2VM);

        return extendUser;
    }

    private void updateExtendUser(ExtendUser extendUser, UserV2VM userV2VM) {
        if (userV2VM != null) {
            if (userV2VM.isFirstLogin() != null) {
                extendUser.setFirstLogin(userV2VM.isFirstLogin());
            }

            if (userV2VM.getNationalId() != null) {
                extendUser.setNationalId(userV2VM.getNationalId());
            }
        }
    }

    private void clearUserCaches(User user) {
        CacheUtil.clearUserCaches(user, cacheManager);
    }
}
