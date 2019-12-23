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
                if (updateCalendar.getStart() != null) {
                    calendar.setStart((updateCalendar.getStart()));
                }

                if (updateCalendar.getEnd() != null) {
                    calendar.setEnd((updateCalendar.getEnd()));
                }

                if (updateCalendar.getTimeInterval() != null) {
                    calendar.setTimeInterval((updateCalendar.getTimeInterval()));
                }

                if (updateCalendar.getTimeType() != null) {
                    calendar.setTimeType((updateCalendar.getTimeType()));
                }

                if (updateCalendar.getNote() != null) {
                    calendar.setNote((updateCalendar.getNote()));
                }

                if (updateCalendar.getDoctor() != null && updateCalendar.getDoctor().getId() != null) {
                    userRepository.findById(updateCalendar.getDoctor().getId()).ifPresent(user -> calendar.setDoctor(user.getExtendUser()));
                }

                if (updateCalendar.getDoctor() == null) {
                    calendar.setDoctor(null);
                }

                if (updateCalendar.getDayOffCron() != null) {
                    calendar.setDayOffCron((updateCalendar.getDayOffCron()));
                }

                if (updateCalendar.getDuration() != null) {
                    calendar.setDuration((updateCalendar.getDuration()));
                }

                return calendar;
            })
            .get();
    }
}
