package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiMonthDeclarationDetails;
import io.dentall.totoro.service.NhiMonthDeclarationDetailsService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.NhiMonthDeclarationDetailsCriteria;
import io.dentall.totoro.service.NhiMonthDeclarationDetailsQueryService;
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
 * REST controller for managing NhiMonthDeclarationDetails.
 */
@RestController
@RequestMapping("/api")
public class NhiMonthDeclarationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(NhiMonthDeclarationDetailsResource.class);

    private static final String ENTITY_NAME = "nhiMonthDeclarationDetails";

    private final NhiMonthDeclarationDetailsService nhiMonthDeclarationDetailsService;

    private final NhiMonthDeclarationDetailsQueryService nhiMonthDeclarationDetailsQueryService;

    public NhiMonthDeclarationDetailsResource(NhiMonthDeclarationDetailsService nhiMonthDeclarationDetailsService, NhiMonthDeclarationDetailsQueryService nhiMonthDeclarationDetailsQueryService) {
        this.nhiMonthDeclarationDetailsService = nhiMonthDeclarationDetailsService;
        this.nhiMonthDeclarationDetailsQueryService = nhiMonthDeclarationDetailsQueryService;
    }

    /**
     * POST  /nhi-month-declaration-details : Create a new nhiMonthDeclarationDetails.
     *
     * @param nhiMonthDeclarationDetails the nhiMonthDeclarationDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiMonthDeclarationDetails, or with status 400 (Bad Request) if the nhiMonthDeclarationDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-month-declaration-details")
    @Timed
    public ResponseEntity<NhiMonthDeclarationDetails> createNhiMonthDeclarationDetails(@RequestBody NhiMonthDeclarationDetails nhiMonthDeclarationDetails) throws URISyntaxException {
        log.debug("REST request to save NhiMonthDeclarationDetails : {}", nhiMonthDeclarationDetails);
        if (nhiMonthDeclarationDetails.getId() != null) {
            throw new BadRequestAlertException("A new nhiMonthDeclarationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiMonthDeclarationDetails result = nhiMonthDeclarationDetailsService.save(nhiMonthDeclarationDetails);
        return ResponseEntity.created(new URI("/api/nhi-month-declaration-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-month-declaration-details : Updates an existing nhiMonthDeclarationDetails.
     *
     * @param nhiMonthDeclarationDetails the nhiMonthDeclarationDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiMonthDeclarationDetails,
     * or with status 400 (Bad Request) if the nhiMonthDeclarationDetails is not valid,
     * or with status 500 (Internal Server Error) if the nhiMonthDeclarationDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-month-declaration-details")
    @Timed
    public ResponseEntity<NhiMonthDeclarationDetails> updateNhiMonthDeclarationDetails(@RequestBody NhiMonthDeclarationDetails nhiMonthDeclarationDetails) throws URISyntaxException {
        log.debug("REST request to update NhiMonthDeclarationDetails : {}", nhiMonthDeclarationDetails);
        if (nhiMonthDeclarationDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiMonthDeclarationDetails result = nhiMonthDeclarationDetailsService.save(nhiMonthDeclarationDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiMonthDeclarationDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-month-declaration-details : get all the nhiMonthDeclarationDetails.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of nhiMonthDeclarationDetails in body
     */
    @GetMapping("/nhi-month-declaration-details")
    @Timed
    public ResponseEntity<List<NhiMonthDeclarationDetails>> getAllNhiMonthDeclarationDetails(NhiMonthDeclarationDetailsCriteria criteria) {
        log.debug("REST request to get NhiMonthDeclarationDetails by criteria: {}", criteria);
        List<NhiMonthDeclarationDetails> entityList = nhiMonthDeclarationDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /nhi-month-declaration-details/count : count all the nhiMonthDeclarationDetails.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/nhi-month-declaration-details/count")
    @Timed
    public ResponseEntity<Long> countNhiMonthDeclarationDetails(NhiMonthDeclarationDetailsCriteria criteria) {
        log.debug("REST request to count NhiMonthDeclarationDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhiMonthDeclarationDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /nhi-month-declaration-details/:id : get the "id" nhiMonthDeclarationDetails.
     *
     * @param id the id of the nhiMonthDeclarationDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiMonthDeclarationDetails, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-month-declaration-details/{id}")
    @Timed
    public ResponseEntity<NhiMonthDeclarationDetails> getNhiMonthDeclarationDetails(@PathVariable Long id) {
        log.debug("REST request to get NhiMonthDeclarationDetails : {}", id);
        Optional<NhiMonthDeclarationDetails> nhiMonthDeclarationDetails = nhiMonthDeclarationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiMonthDeclarationDetails);
    }

    /**
     * DELETE  /nhi-month-declaration-details/:id : delete the "id" nhiMonthDeclarationDetails.
     *
     * @param id the id of the nhiMonthDeclarationDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-month-declaration-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiMonthDeclarationDetails(@PathVariable Long id) {
        log.debug("REST request to delete NhiMonthDeclarationDetails : {}", id);
        nhiMonthDeclarationDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
