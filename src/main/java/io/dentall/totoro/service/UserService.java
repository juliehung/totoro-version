package io.dentall.totoro.service;

import io.dentall.totoro.config.Constants;
import io.dentall.totoro.domain.Authority;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.AuthorityRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.security.AuthoritiesConstants;
import io.dentall.totoro.security.SecurityUtils;
import io.dentall.totoro.service.dto.DoctorVM;
import io.dentall.totoro.service.dto.UserDTO;
import io.dentall.totoro.service.mapper.UserDomainMapper;
import io.dentall.totoro.service.util.RandomUtil;
import io.dentall.totoro.web.rest.errors.EmailAlreadyUsedException;
import io.dentall.totoro.web.rest.errors.InvalidPasswordException;
import io.dentall.totoro.web.rest.errors.LoginAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
            });
    }

    public Optional<User> resetPasswordbyAdmin(String login) {
        log.debug("Reset user password without reset key {}");
        return userRepository.findOneByLogin(login)
            .map(user -> {
                user.setPassword(passwordEncoder.encode(login));
                user.getExtendUser().setFirstLogin(true);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                return user;
            });
    }

    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new LoginAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        newUser.setExtendUser(createExtendUser(newUser, userDTO.getExtendUser()));
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    public User createUser(UserDTO userDTO) {
        return createUser(userDTO, userDTO.getLogin());
    }

    public User createUser(UserDTO userDTO, String password) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail() == null ? null : userDTO.getEmail().toLowerCase());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }

        user.setExtendUser(createExtendUser(user, userDTO.getExtendUser()));
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email id of user
     * @param langKey   language key
     * @param imageUrl  image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email == null ? null : email.toLowerCase());
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                if (userDTO.getLogin() != null) {
                    user.setLogin(userDTO.getLogin().toLowerCase());
                }

                if (userDTO.getFirstName() != null) {
                    user.setFirstName(userDTO.getFirstName());
                }

                if (userDTO.getLastName() != null) {
                    user.setLastName(userDTO.getLastName());
                }

                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }

                if (userDTO.getImageUrl() != null) {
                    user.setImageUrl(userDTO.getImageUrl());
                }

                if (userDTO.isActivated() != null) {
                    user.setActivated(userDTO.isActivated());
                }

                if (userDTO.getLangKey() != null) {
                    user.setLangKey(userDTO.getLangKey());
                }

                if (userDTO.getAuthorities() != null) {
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                        .map(authorityRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(managedAuthorities::add);
                }

                updateExtendUser(user.getExtendUser(), userDTO.getExtendUser());
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    /**
     * Update extendUser
     *
     * @param login   login of user
     * @param userDTO user to update
     */
    public void updateExtendUser(String login, UserDTO userDTO) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            updateExtendUser(user.getExtendUser(), userDTO.getExtendUser());
        });
    }

    /**
     * Update gmail and calendarId for a user
     *
     * @param gmail      gmail of user
     * @param calendarId id of google calendar
     */
    public void updateExtendUser(String gmail, String calendarId) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .map(User::getExtendUser)
            .ifPresent(extendUser -> {
                extendUser.setGmail(gmail);
                extendUser.setCalendarId(calendarId);
                log.debug("Changed Information for ExtendUser: {}", extendUser);
            });
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                user.getExtendUser().setFirstLogin(false);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<DoctorVM> getAllDoctors(Pageable pageable) {
        return userRepository.findByAuthorities_NameIn(pageable, Arrays.asList(AuthoritiesConstants.DOCTOR)).map(UserDomainMapper.INSTANCE::mapToDoctorVM);
    }

    @Transactional(readOnly = true)
    public Optional<DoctorVM> getDoctor(String doctorId) {
        return userRepository.findByLoginAndAuthorities_NameIn(doctorId, Arrays.asList(AuthoritiesConstants.DOCTOR)).map(UserDomainMapper.INSTANCE::mapToDoctorVM);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
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
            });
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private ExtendUser createExtendUser(User user, ExtendUser ExtendUserDTO) {
        ExtendUser extendUser = new ExtendUser().user(user);
        updateExtendUser(extendUser, ExtendUserDTO);

        return extendUser;
    }

    private void updateExtendUser(ExtendUser extendUser, ExtendUser updateExtendUser) {
        if (updateExtendUser != null) {
            if (updateExtendUser.isFirstLogin() != null) {
                extendUser.setFirstLogin(updateExtendUser.isFirstLogin());
            }

            if (updateExtendUser.getGmail() != null) {
                extendUser.setGmail(updateExtendUser.getGmail());
            }

            if (updateExtendUser.getCalendarId() != null) {
                extendUser.setCalendarId(updateExtendUser.getCalendarId());
            }

            if (updateExtendUser.getNationalId() != null) {
                extendUser.setNationalId(updateExtendUser.getNationalId());
            }

            log.debug("Changed Information for ExtendUser: {}", extendUser);
        }
    }
}
