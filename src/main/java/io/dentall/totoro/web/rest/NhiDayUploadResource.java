package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiDayUpload;
import io.dentall.totoro.service.NhiDayUploadService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
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
 * REST controller for managing NhiDayUpload.
 */
@RestController
@RequestMapping("/api")
public class NhiDayUploadResource {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadResource.class);

    private static final String ENTITY_NAME = "nhiDayUpload";

    private final NhiDayUploadService nhiDayUploadService;

    public NhiDayUploadResource(NhiDayUploadService nhiDayUploadService) {
        this.nhiDayUploadService = nhiDayUploadService;
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
     * @return the ResponseEntity with status 200 (OK) and the list of nhiDayUploads in body
     */
    @GetMapping("/nhi-day-uploads")
    @Timed
    public List<NhiDayUpload> getAllNhiDayUploads() {
        log.debug("REST request to get all NhiDayUploads");
        return nhiDayUploadService.findAll();
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
