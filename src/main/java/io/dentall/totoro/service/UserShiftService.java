package io.dentall.totoro.service;

import io.dentall.totoro.domain.UserShift;
import io.dentall.totoro.repository.UserShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserShift}.
 */
@Service
@Transactional
public class UserShiftService {

    private final Logger log = LoggerFactory.getLogger(UserShiftService.class);

    private final UserShiftRepository userShiftRepository;

    public UserShiftService(UserShiftRepository userShiftRepository) {
        this.userShiftRepository = userShiftRepository;
    }

    /**
     * Save a userShift.
     *
     * @param userShift the entity to save.
     * @return the persisted entity.
     */
    public UserShift save(UserShift userShift) {
        log.debug("Request to save UserShift : {}", userShift);
        return userShiftRepository.save(userShift);
    }

    /**
     * Get all the userShifts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserShift> findAll() {
        log.debug("Request to get all UserShifts");
        return userShiftRepository.findAll();
    }

    /**
     * Get one userShift by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserShift> findOne(Long id) {
        log.debug("Request to get UserShift : {}", id);
        return userShiftRepository.findById(id);
    }

    /**
     * Delete the userShift by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserShift : {}", id);
        userShiftRepository.deleteById(id);
    }
}
