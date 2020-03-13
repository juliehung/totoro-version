package io.dentall.totoro.web.rest;
import io.dentall.totoro.service.HomePageCoverServiceV1;
import io.dentall.totoro.domain.HomePageCover;
import io.dentall.totoro.service.HomePageCoverQueryService;
import io.dentall.totoro.service.dto.HomePageCoverCriteria;
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
 * REST controller for managing HomePageCover.
 */
@RestController
@RequestMapping("/api")
public class HomePageCoverResource {

    private final Logger log = LoggerFactory.getLogger(HomePageCoverResource.class);

    private static final String ENTITY_NAME = "homePageCover";

    private final HomePageCoverServiceV1 homePageCoverBusinessService;

    private final HomePageCoverQueryService homePageCoverQueryService;

    public HomePageCoverResource(
        HomePageCoverServiceV1 homePageCoverBusinessService,
        HomePageCoverQueryService homePageCoverQueryService
    ) {
        this.homePageCoverBusinessService = homePageCoverBusinessService;
        this.homePageCoverQueryService = homePageCoverQueryService;
    }

    /**
     * POST  /home-page-covers : Create a new homePageCover.
     *
     * @param homePageCover the homePageCover to create
     * @return the ResponseEntity with status 201 (Created) and with body the new homePageCover, or with status 400 (Bad Request) if the homePageCover has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/home-page-covers")
    public ResponseEntity<HomePageCover> createHomePageCover(@RequestBody HomePageCover homePageCover) throws URISyntaxException {
        log.debug("REST request to save HomePageCover : {}", homePageCover);
        if (homePageCover.getPatientId() == null) {
            throw new BadRequestAlertException("A new homePageCover must have an patient id", ENTITY_NAME, "idexists");
        }
        HomePageCover result = homePageCoverBusinessService.save(homePageCover);
        return ResponseEntity.created(new URI("/api/home-page-covers/" + result.getPatientId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getPatientId().toString()))
            .body(result);
    }

    /**
     * PUT  /home-page-covers : Updates an existing homePageCover.
     *
     * @param homePageCover the homePageCover to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated homePageCover,
     * or with status 400 (Bad Request) if the homePageCover is not valid,
     * or with status 500 (Internal Server Error) if the homePageCover couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/home-page-covers")
    public ResponseEntity<HomePageCover> updateHomePageCover(@RequestBody HomePageCover homePageCover) throws URISyntaxException {
        log.debug("REST request to update HomePageCover : {}", homePageCover);
        if (homePageCover.getPatientId() == null) {
            throw new BadRequestAlertException("Invalid patient id", ENTITY_NAME, "idnull");
        }
        HomePageCover result = homePageCoverBusinessService.update(homePageCover);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, homePageCover.getPatientId().toString()))
            .body(result);
    }

    /**
     * GET  /home-page-covers : get all the homePageCovers.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of homePageCovers in body
     */
    @GetMapping("/home-page-covers")
    public ResponseEntity<List<HomePageCover>> getAllHomePageCovers(HomePageCoverCriteria criteria) {
        log.debug("REST request to get HomePageCovers by criteria: {}", criteria);
        List<HomePageCover> entityList = homePageCoverQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /home-page-covers/count : count all the homePageCovers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/home-page-covers/count")
    public ResponseEntity<Long> countHomePageCovers(HomePageCoverCriteria criteria) {
        log.debug("REST request to count HomePageCovers by criteria: {}", criteria);
        return ResponseEntity.ok().body(homePageCoverQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /home-page-covers/:id : get the "id" homePageCover.
     *
     * @param id the id of the homePageCover to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the homePageCover, or with status 404 (Not Found)
     */
    @GetMapping("/home-page-covers/{id}")
    public ResponseEntity<HomePageCover> getHomePageCover(@PathVariable Long id) {
        log.debug("REST request to get HomePageCover : {}", id);
        Optional<HomePageCover> homePageCover = homePageCoverBusinessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(homePageCover);
    }

    /**
     * DELETE  /home-page-covers/:id : delete the "id" homePageCover.
     *
     * @param id the id of the homePageCover to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/home-page-covers/{id}")
    public ResponseEntity<Void> deleteHomePageCover(@PathVariable Long id) {
        log.debug("REST request to delete HomePageCover : {}", id);
        homePageCoverBusinessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
