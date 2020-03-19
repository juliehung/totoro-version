package io.dentall.totoro.web.rest;

import io.dentall.totoro.domain.UserDayOff;
import io.dentall.totoro.service.UserDayOffQueryService;
import io.dentall.totoro.service.UserDayOffService;
import io.dentall.totoro.service.dto.UserDayOffCriteria;
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
 * REST controller for managing {@link io.dentall.totoro.domain.UserDayOff}.
 */
@RestController
@RequestMapping("/api")
public class UserDayOffResource {

    private final Logger log = LoggerFactory.getLogger(UserDayOffResource.class);

    private static final String ENTITY_NAME = "userDayOff";

    private final UserDayOffService userDayOffService;

    private final UserDayOffQueryService userDayOffQueryService;

    public UserDayOffResource(UserDayOffService userDayOffService, UserDayOffQueryService userDayOffQueryService) {
        this.userDayOffService = userDayOffService;
        this.userDayOffQueryService = userDayOffQueryService;
    }

    /**
     * {@code POST  /user-day-offs} : Create a new userDayOff.
     *
     * @param userDayOff the userDayOff to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDayOff, or with status {@code 400 (Bad Request)} if the userDayOff has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-day-offs")
    public ResponseEntity<UserDayOff> createUserDayOff(@RequestBody UserDayOff userDayOff) throws URISyntaxException {
        log.debug("REST request to save UserDayOff : {}", userDayOff);
        if (userDayOff.getId() != null) {
            throw new BadRequestAlertException("A new userDayOff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDayOff result = userDayOffService.save(userDayOff);
        return ResponseEntity.created(new URI("/api/user-day-offs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-day-offs} : Updates an existing userDayOff.
     *
     * @param userDayOff the userDayOff to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDayOff,
     * or with status {@code 400 (Bad Request)} if the userDayOff is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDayOff couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-day-offs")
    public ResponseEntity<UserDayOff> updateUserDayOff(@RequestBody UserDayOff userDayOff) throws URISyntaxException {
        log.debug("REST request to update UserDayOff : {}", userDayOff);
        if (userDayOff.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDayOff result = userDayOffService.save(userDayOff);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDayOff.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-day-offs} : get all the userDayOffs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDayOffs in body.
     */
    @GetMapping("/user-day-offs")
    public ResponseEntity<List<UserDayOff>> getAllUserDayOffs(UserDayOffCriteria criteria) {
        log.debug("REST request to get UserDayOffs by criteria: {}", criteria);
        List<UserDayOff> entityList = userDayOffQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /user-day-offs/count} : count all the userDayOffs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-day-offs/count")
    public ResponseEntity<Long> countUserDayOffs(UserDayOffCriteria criteria) {
        log.debug("REST request to count UserDayOffs by criteria: {}", criteria);
        return ResponseEntity.ok().body(userDayOffQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-day-offs/:id} : get the "id" userDayOff.
     *
     * @param id the id of the userDayOff to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDayOff, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-day-offs/{id}")
    public ResponseEntity<UserDayOff> getUserDayOff(@PathVariable Long id) {
        log.debug("REST request to get UserDayOff : {}", id);
        Optional<UserDayOff> userDayOff = userDayOffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDayOff);
    }

    /**
     * {@code DELETE  /user-day-offs/:id} : delete the "id" userDayOff.
     *
     * @param id the id of the userDayOff to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-day-offs/{id}")
    public ResponseEntity<Void> deleteUserDayOff(@PathVariable Long id) {
        log.debug("REST request to delete UserDayOff : {}", id);
        userDayOffService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
