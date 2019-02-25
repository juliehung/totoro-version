package io.dentall.totoro.service;

import io.dentall.totoro.domain.Calendar;
import io.dentall.totoro.repository.CalendarRepository;
import io.dentall.totoro.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Calendar.
 */
@Service
@Transactional
public class CalendarService {

    private final Logger log = LoggerFactory.getLogger(CalendarService.class);

    private final CalendarRepository calendarRepository;

    private final UserRepository userRepository;

    public CalendarService(CalendarRepository calendarRepository, UserRepository userRepository) {
        this.calendarRepository = calendarRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a calendar.
     *
     * @param calendar the entity to save
     * @return the persisted entity
     */
    public Calendar save(Calendar calendar) {
        log.debug("Request to save Calendar : {}", calendar);
        return calendarRepository.save(calendar);
    }

    /**
     * Get all the calendars.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Calendar> findAll() {
        log.debug("Request to get all Calendars");
        return calendarRepository.findAll();
    }


    /**
     * Get one calendar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Calendar> findOne(Long id) {
        log.debug("Request to get Calendar : {}", id);
        return calendarRepository.findById(id);
    }

    /**
     * Delete the calendar by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Calendar : {}", id);
        calendarRepository.deleteById(id);
    }

    /**
     * Update the calendar by id.
     *
     * @param updateCalendar the update entity
     */
    public Calendar update(Calendar updateCalendar) {
        log.debug("Request to update Calendar : {}", updateCalendar);

        return calendarRepository
            .findById(updateCalendar.getId())
            .map(calendar -> {
                if (updateCalendar.getDate() != null) {
                    calendar.setDate((updateCalendar.getDate()));
                }

                if (updateCalendar.getTimeInterval() != null) {
                    calendar.setTimeInterval((updateCalendar.getTimeInterval()));
                }

                if (updateCalendar.getTimeType() != null) {
                    calendar.setTimeType((updateCalendar.getTimeType()));
                }

                if (updateCalendar.getStartTime() != null) {
                    calendar.setStartTime((updateCalendar.getStartTime()));
                }

                if (updateCalendar.getEndTime() != null) {
                    calendar.setEndTime((updateCalendar.getEndTime()));
                }

                if (updateCalendar.getDoctor() != null && updateCalendar.getDoctor().getId() != null) {
                    userRepository.findById(updateCalendar.getDoctor().getId()).ifPresent(user -> calendar.setDoctor(user.getExtendUser()));
                }

                return calendar;
            })
            .get();
    }
}
