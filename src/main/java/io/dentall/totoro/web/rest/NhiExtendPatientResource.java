package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiExtendPatient;
import io.dentall.totoro.service.NhiExtendPatientService;
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
 * REST controller for managing NhiExtendPatient.
 */
@RestController
@RequestMapping("/api")
public class NhiExtendPatientResource {

    private final Logger log = LoggerFactory.getLogger(NhiExtendPatientResource.class);

    private static final String ENTITY_NAME = "nhiExtendPatient";

    private final NhiExtendPatientService nhiExtendPatientService;

    public NhiExtendPatientResource(NhiExtendPatientService nhiExtendPatientService) {
        this.nhiExtendPatientService = nhiExtendPatientService;
    }

    /**
     * POST  /nhi-extend-patients : Create a new nhiExtendPatient.
     *
     * @param nhiExtendPatient the nhiExtendPatient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiExtendPatient, or with status 400 (Bad Request) if the nhiExtendPatient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-extend-patients")
    @Timed
    public ResponseEntity<NhiExtendPatient> createNhiExtendPatient(@RequestBody NhiExtendPatient nhiExtendPatient) throws URISyntaxException {
        log.debug("REST request to save NhiExtendPatient : {}", nhiExtendPatient);
        if (nhiExtendPatient.getId() != null) {
            throw new BadRequestAlertException("A new nhiExtendPatient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiExtendPatient result = nhiExtendPatientService.save(nhiExtendPatient);
        return ResponseEntity.created(new URI("/api/nhi-extend-patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-extend-patients : Updates an existing nhiExtendPatient.
     *
     * @param nhiExtendPatient the nhiExtendPatient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiExtendPatient,
     * or with status 400 (Bad Request) if the nhiExtendPatient is not valid,
     * or with status 500 (Internal Server Error) if the nhiExtendPatient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-extend-patients")
    @Timed
    public ResponseEntity<NhiExtendPatient> updateNhiExtendPatient(@RequestBody NhiExtendPatient nhiExtendPatient) throws URISyntaxException {
        log.debug("REST request to update NhiExtendPatient : {}", nhiExtendPatient);
        if (nhiExtendPatient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiExtendPatient result = nhiExtendPatientService.update(nhiExtendPatient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiExtendPatient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-extend-patients : get all the nhiExtendPatients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiExtendPatients in body
     */
    @GetMapping("/nhi-extend-patients")
    @Timed
    public List<NhiExtendPatient> getAllNhiExtendPatients() {
        log.debug("REST request to get all NhiExtendPatients");
        return nhiExtendPatientService.findAll();
    }

    /**
     * GET  /nhi-extend-patients/:id : get the "id" nhiExtendPatient.
     *
     * @param id the id of the nhiExtendPatient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiExtendPatient, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-extend-patients/{id}")
    @Timed
    public ResponseEntity<NhiExtendPatient> getNhiExtendPatient(@PathVariable Long id) {
        log.debug("REST request to get NhiExtendPatient : {}", id);
        Optional<NhiExtendPatient> nhiExtendPatient = nhiExtendPatientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiExtendPatient);
    }

    /**
     * DELETE  /nhi-extend-patients/:id : delete the "id" nhiExtendPatient.
     *
     * @param id the id of the nhiExtendPatient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-extend-patients/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiExtendPatient(@PathVariable Long id) {
        log.debug("REST request to delete NhiExtendPatient : {}", id);
        nhiExtendPatientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
