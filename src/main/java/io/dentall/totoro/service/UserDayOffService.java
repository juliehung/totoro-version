package io.dentall.totoro.service;

import io.dentall.totoro.domain.UserDayOff;
import io.dentall.totoro.repository.UserDayOffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserDayOff}.
 */
@Service
@Transactional
public class UserDayOffService {

    private final Logger log = LoggerFactory.getLogger(UserDayOffService.class);

    private final UserDayOffRepository userDayOffRepository;

    public UserDayOffService(UserDayOffRepository userDayOffRepository) {
        this.userDayOffRepository = userDayOffRepository;
    }

    /**
     * Save a userDayOff.
     *
     * @param userDayOff the entity to save.
     * @return the persisted entity.
     */
    public UserDayOff save(UserDayOff userDayOff) {
        log.debug("Request to save UserDayOff : {}", userDayOff);
        return userDayOffRepository.save(userDayOff);
    }

    /**
     * Get all the userDayOffs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserDayOff> findAll() {
        log.debug("Request to get all UserDayOffs");
        return userDayOffRepository.findAll();
    }

    /**
     * Get one userDayOff by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserDayOff> findOne(Long id) {
        log.debug("Request to get UserDayOff : {}", id);
        return userDayOffRepository.findById(id);
    }

    /**
     * Delete the userDayOff by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserDayOff : {}", id);
        userDayOffRepository.deleteById(id);
    }
}
