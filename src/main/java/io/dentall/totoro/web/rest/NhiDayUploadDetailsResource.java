package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiDayUploadDetails;
import io.dentall.totoro.service.NhiDayUploadDetailsService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.NhiDayUploadDetailsCriteria;
import io.dentall.totoro.service.NhiDayUploadDetailsQueryService;
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

/**
 * REST controller for managing NhiDayUploadDetails.
 */
@RestController
@RequestMapping("/api")
public class NhiDayUploadDetailsResource {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadDetailsResource.class);

    private static final String ENTITY_NAME = "nhiDayUploadDetails";

    private final NhiDayUploadDetailsService nhiDayUploadDetailsService;

    private final NhiDayUploadDetailsQueryService nhiDayUploadDetailsQueryService;

    public NhiDayUploadDetailsResource(NhiDayUploadDetailsService nhiDayUploadDetailsService, NhiDayUploadDetailsQueryService nhiDayUploadDetailsQueryService) {
        this.nhiDayUploadDetailsService = nhiDayUploadDetailsService;
        this.nhiDayUploadDetailsQueryService = nhiDayUploadDetailsQueryService;
    }

    /**
     * POST  /nhi-day-upload-details : Create a new nhiDayUploadDetails.
     *
     * @param nhiDayUploadDetails the nhiDayUploadDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiDayUploadDetails, or with status 400 (Bad Request) if the nhiDayUploadDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-day-upload-details")
    @Timed
    public ResponseEntity<NhiDayUploadDetails> createNhiDayUploadDetails(@Valid @RequestBody NhiDayUploadDetails nhiDayUploadDetails) throws URISyntaxException {
        log.debug("REST request to save NhiDayUploadDetails : {}", nhiDayUploadDetails);
        if (nhiDayUploadDetails.getId() != null) {
            throw new BadRequestAlertException("A new nhiDayUploadDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiDayUploadDetails result = nhiDayUploadDetailsService.save(nhiDayUploadDetails);
        return ResponseEntity.created(new URI("/api/nhi-day-upload-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-day-upload-details : Updates an existing nhiDayUploadDetails.
     *
     * @param nhiDayUploadDetails the nhiDayUploadDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiDayUploadDetails,
     * or with status 400 (Bad Request) if the nhiDayUploadDetails is not valid,
     * or with status 500 (Internal Server Error) if the nhiDayUploadDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-day-upload-details")
    @Timed
    public ResponseEntity<NhiDayUploadDetails> updateNhiDayUploadDetails(@Valid @RequestBody NhiDayUploadDetails nhiDayUploadDetails) throws URISyntaxException {
        log.debug("REST request to update NhiDayUploadDetails : {}", nhiDayUploadDetails);
        if (nhiDayUploadDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiDayUploadDetails result = nhiDayUploadDetailsService.update(nhiDayUploadDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiDayUploadDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-day-upload-details : get all the nhiDayUploadDetails.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of nhiDayUploadDetails in body
     */
    @GetMapping("/nhi-day-upload-details")
    @Timed
    public ResponseEntity<List<NhiDayUploadDetails>> getAllNhiDayUploadDetails(NhiDayUploadDetailsCriteria criteria) {
        log.debug("REST request to get NhiDayUploadDetails by criteria: {}", criteria);
        List<NhiDayUploadDetails> entityList = nhiDayUploadDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /nhi-day-upload-details/count : count all the nhiDayUploadDetails.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/nhi-day-upload-details/count")
    @Timed
    public ResponseEntity<Long> countNhiDayUploadDetails(NhiDayUploadDetailsCriteria criteria) {
        log.debug("REST request to count NhiDayUploadDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhiDayUploadDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /nhi-day-upload-details/:id : get the "id" nhiDayUploadDetails.
     *
     * @param id the id of the nhiDayUploadDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiDayUploadDetails, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-day-upload-details/{id}")
    @Timed
    public ResponseEntity<NhiDayUploadDetails> getNhiDayUploadDetails(@PathVariable Long id) {
        log.debug("REST request to get NhiDayUploadDetails : {}", id);
        Optional<NhiDayUploadDetails> nhiDayUploadDetails = nhiDayUploadDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiDayUploadDetails);
    }

    /**
     * DELETE  /nhi-day-upload-details/:id : delete the "id" nhiDayUploadDetails.
     *
     * @param id the id of the nhiDayUploadDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-day-upload-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiDayUploadDetails(@PathVariable Long id) {
        log.debug("REST request to delete NhiDayUploadDetails : {}", id);
        nhiDayUploadDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
