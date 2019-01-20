package io.dentall.totoro.service;

import io.dentall.totoro.domain.CalendarSetting;
import io.dentall.totoro.repository.CalendarSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing CalendarSetting.
 */
@Service
@Transactional
public class CalendarSettingService {

    private final Logger log = LoggerFactory.getLogger(CalendarSettingService.class);

    private final CalendarSettingRepository calendarSettingRepository;

    public CalendarSettingService(CalendarSettingRepository calendarSettingRepository) {
        this.calendarSettingRepository = calendarSettingRepository;
    }

    /**
     * Save a calendarSetting.
     *
     * @param calendarSetting the entity to save
     * @return the persisted entity
     */
    public CalendarSetting save(CalendarSetting calendarSetting) {
        log.debug("Request to save CalendarSetting : {}", calendarSetting);
        return calendarSettingRepository.save(calendarSetting);
    }

    /**
     * Get all the calendarSettings.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CalendarSetting> findAll() {
        log.debug("Request to get all CalendarSettings");
        return calendarSettingRepository.findAll();
    }


    /**
     * Get one calendarSetting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CalendarSetting> findOne(Long id) {
        log.debug("Request to get CalendarSetting : {}", id);
        return calendarSettingRepository.findById(id);
    }

    /**
     * Delete the calendarSetting by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CalendarSetting : {}", id);
        calendarSettingRepository.deleteById(id);
    }
}
