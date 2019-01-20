package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.CalendarSetting;
import io.dentall.totoro.service.CalendarSettingService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.CalendarSettingCriteria;
import io.dentall.totoro.service.CalendarSettingQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CalendarSetting.
 */
@RestController
@RequestMapping("/api")
public class CalendarSettingResource {

    private final Logger log = LoggerFactory.getLogger(CalendarSettingResource.class);

    private static final String ENTITY_NAME = "calendarSetting";

    private final CalendarSettingService calendarSettingService;

    private final CalendarSettingQueryService calendarSettingQueryService;

    public CalendarSettingResource(CalendarSettingService calendarSettingService, CalendarSettingQueryService calendarSettingQueryService) {
        this.calendarSettingService = calendarSettingService;
        this.calendarSettingQueryService = calendarSettingQueryService;
    }

    /**
     * POST  /calendar-settings : Create a new calendarSetting.
     *
     * @param calendarSetting the calendarSetting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendarSetting, or with status 400 (Bad Request) if the calendarSetting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendar-settings")
    @Timed
    public ResponseEntity<CalendarSetting> createCalendarSetting(@Valid @RequestBody CalendarSetting calendarSetting) throws URISyntaxException {
        log.debug("REST request to save CalendarSetting : {}", calendarSetting);
        if (calendarSetting.getId() != null) {
            throw new BadRequestAlertException("A new calendarSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalendarSetting result = calendarSettingService.save(calendarSetting);
        return ResponseEntity.created(new URI("/api/calendar-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendar-settings : Updates an existing calendarSetting.
     *
     * @param calendarSetting the calendarSetting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calendarSetting,
     * or with status 400 (Bad Request) if the calendarSetting is not valid,
     * or with status 500 (Internal Server Error) if the calendarSetting couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calendar-settings")
    @Timed
    public ResponseEntity<CalendarSetting> updateCalendarSetting(@Valid @RequestBody CalendarSetting calendarSetting) throws URISyntaxException {
        log.debug("REST request to update CalendarSetting : {}", calendarSetting);
        if (calendarSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalendarSetting result = calendarSettingService.save(calendarSetting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calendarSetting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendar-settings : get all the calendarSettings.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of calendarSettings in body
     */
    @GetMapping("/calendar-settings")
    @Timed
    public ResponseEntity<List<CalendarSetting>> getAllCalendarSettings(CalendarSettingCriteria criteria) {
        log.debug("REST request to get CalendarSettings by criteria: {}", criteria);
        List<CalendarSetting> entityList = calendarSettingQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /calendar-settings/count : count all the calendarSettings.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/calendar-settings/count")
    @Timed
    public ResponseEntity<Long> countCalendarSettings(CalendarSettingCriteria criteria) {
        log.debug("REST request to count CalendarSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(calendarSettingQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /calendar-settings/:id : get the "id" calendarSetting.
     *
     * @param id the id of the calendarSetting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calendarSetting, or with status 404 (Not Found)
     */
    @GetMapping("/calendar-settings/{id}")
    @Timed
    public ResponseEntity<CalendarSetting> getCalendarSetting(@PathVariable Long id) {
        log.debug("REST request to get CalendarSetting : {}", id);
        Optional<CalendarSetting> calendarSetting = calendarSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarSetting);
    }

    /**
     * DELETE  /calendar-settings/:id : delete the "id" calendarSetting.
     *
     * @param id the id of the calendarSetting to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calendar-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalendarSetting(@PathVariable Long id) {
        log.debug("REST request to delete CalendarSetting : {}", id);
        calendarSettingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /calendar-settings/settings : Create a list of calendarSetting.
     *
     * @param calendarSettings the calendarSetting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendarSetting
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendar-settings/settings")
    @Timed
    public ResponseEntity<List<CalendarSetting>> createCalendarSettingList(@Valid @RequestBody List<CalendarSetting> calendarSettings) throws URISyntaxException {
        log.debug("REST request to save CalendarSetting list : {}", calendarSettings);
        return ResponseEntity
            .created(new URI("/api/calendar-settings/settings"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "settings"))
            .body(calendarSettings.stream().map(calendarSettingService::save).collect(Collectors.toList()));
    }
}
