package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiDayUpload;
import io.dentall.totoro.service.NhiDayUploadService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.NhiDayUploadCriteria;
import io.dentall.totoro.service.NhiDayUploadQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NhiDayUpload.
 */
@RestController
@RequestMapping("/api")
public class NhiDayUploadResource {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadResource.class);

    private static final String ENTITY_NAME = "nhiDayUpload";

    private final NhiDayUploadService nhiDayUploadService;

    private final NhiDayUploadQueryService nhiDayUploadQueryService;

    public NhiDayUploadResource(NhiDayUploadService nhiDayUploadService, NhiDayUploadQueryService nhiDayUploadQueryService) {
        this.nhiDayUploadService = nhiDayUploadService;
        this.nhiDayUploadQueryService = nhiDayUploadQueryService;
    }

    /**
     * POST  /nhi-day-uploads : Create a new nhiDayUpload.
     *
     * @param nhiDayUpload the nhiDayUpload to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiDayUpload, or with status 400 (Bad Request) if the nhiDayUpload has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-day-uploads")
    @Timed
    public ResponseEntity<NhiDayUpload> createNhiDayUpload(@Valid @RequestBody NhiDayUpload nhiDayUpload) throws URISyntaxException {
        log.debug("REST request to save NhiDayUpload : {}", nhiDayUpload);
        if (nhiDayUpload.getId() != null) {
            throw new BadRequestAlertException("A new nhiDayUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiDayUpload result = nhiDayUploadService.save(nhiDayUpload);
        return ResponseEntity.created(new URI("/api/nhi-day-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-day-uploads : Updates an existing nhiDayUpload.
     *
     * @param nhiDayUpload the nhiDayUpload to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiDayUpload,
     * or with status 400 (Bad Request) if the nhiDayUpload is not valid,
     * or with status 500 (Internal Server Error) if the nhiDayUpload couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-day-uploads")
    @Timed
    public ResponseEntity<NhiDayUpload> updateNhiDayUpload(@Valid @RequestBody NhiDayUpload nhiDayUpload) throws URISyntaxException {
        log.debug("REST request to update NhiDayUpload : {}", nhiDayUpload);
        if (nhiDayUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiDayUpload result = nhiDayUploadService.update(nhiDayUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiDayUpload.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-day-uploads : get all the nhiDayUploads.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of nhiDayUploads in body
     */
    @GetMapping("/nhi-day-uploads")
    @Timed
    public ResponseEntity<List<NhiDayUpload>> getAllNhiDayUploads(NhiDayUploadCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NhiDayUploads by criteria: {}", criteria);
        Page<NhiDayUpload> page = nhiDayUploadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhi-day-uploads");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /nhi-day-uploads/count : count all the nhiDayUploads.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/nhi-day-uploads/count")
    @Timed
    public ResponseEntity<Long> countNhiDayUploads(NhiDayUploadCriteria criteria) {
        log.debug("REST request to count NhiDayUploads by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhiDayUploadQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /nhi-day-uploads/:id : get the "id" nhiDayUpload.
     *
     * @param id the id of the nhiDayUpload to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiDayUpload, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-day-uploads/{id}")
    @Timed
    public ResponseEntity<NhiDayUpload> getNhiDayUpload(@PathVariable Long id) {
        log.debug("REST request to get NhiDayUpload : {}", id);
        Optional<NhiDayUpload> nhiDayUpload = nhiDayUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiDayUpload);
    }

    /**
     * DELETE  /nhi-day-uploads/:id : delete the "id" nhiDayUpload.
     *
     * @param id the id of the nhiDayUpload to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-day-uploads/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiDayUpload(@PathVariable Long id) {
        log.debug("REST request to delete NhiDayUpload : {}", id);
        nhiDayUploadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
