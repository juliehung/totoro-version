package io.dentall.totoro.web.rest;

import io.dentall.totoro.domain.UserShift;
import io.dentall.totoro.service.UserShiftQueryService;
import io.dentall.totoro.service.UserShiftService;
import io.dentall.totoro.service.dto.UserShiftCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.dentall.totoro.domain.UserShift}.
 */
@RestController
@RequestMapping("/api")
public class UserShiftResource {

    private final Logger log = LoggerFactory.getLogger(UserShiftResource.class);

    private static final String ENTITY_NAME = "userShift";

    private final UserShiftService userShiftService;

    private final UserShiftQueryService userShiftQueryService;

    public UserShiftResource(UserShiftService userShiftService, UserShiftQueryService userShiftQueryService) {
        this.userShiftService = userShiftService;
        this.userShiftQueryService = userShiftQueryService;
    }

    /**
     * {@code POST  /user-shifts} : Create a new userShift.
     *
     * @param userShift the userShift to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userShift, or with status {@code 400 (Bad Request)} if the userShift has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-shifts")
    public ResponseEntity<UserShift> createUserShift(@RequestBody UserShift userShift) throws URISyntaxException {
        log.debug("REST request to save UserShift : {}", userShift);
        if (userShift.getId() != null) {
            throw new BadRequestAlertException("A new userShift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserShift result = userShiftService.save(userShift);
        return ResponseEntity.created(new URI("/api/user-shifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-shifts} : Updates an existing userShift.
     *
     * @param userShift the userShift to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userShift,
     * or with status {@code 400 (Bad Request)} if the userShift is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userShift couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-shifts")
    public ResponseEntity<UserShift> updateUserShift(@RequestBody UserShift userShift) throws URISyntaxException {
        log.debug("REST request to update UserShift : {}", userShift);
        if (userShift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserShift result = userShiftService.save(userShift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userShift.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-shifts} : get all the userShifts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userShifts in body.
     */
    @GetMapping("/user-shifts")
    public ResponseEntity<List<UserShift>> getAllUserShifts(UserShiftCriteria criteria) {
        log.debug("REST request to get UserShifts by criteria: {}", criteria);
        List<UserShift> entityList = userShiftQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /user-shifts/count} : count all the userShifts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-shifts/count")
    public ResponseEntity<Long> countUserShifts(UserShiftCriteria criteria) {
        log.debug("REST request to count UserShifts by criteria: {}", criteria);
        return ResponseEntity.ok().body(userShiftQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-shifts/:id} : get the "id" userShift.
     *
     * @param id the id of the userShift to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userShift, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-shifts/{id}")
    public ResponseEntity<UserShift> getUserShift(@PathVariable Long id) {
        log.debug("REST request to get UserShift : {}", id);
        Optional<UserShift> userShift = userShiftService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userShift);
    }

    /**
     * {@code DELETE  /user-shifts/:id} : delete the "id" userShift.
     *
     * @param id the id of the userShift to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-shifts/{id}")
    public ResponseEntity<Void> deleteUserShift(@PathVariable Long id) {
        log.debug("REST request to delete UserShift : {}", id);
        userShiftService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
