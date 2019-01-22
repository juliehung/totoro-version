package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NHIUnusalIncident;
import io.dentall.totoro.repository.NHIUnusalIncidentRepository;
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
 * REST controller for managing NHIUnusalIncident.
 */
@RestController
@RequestMapping("/api")
public class NHIUnusalIncidentResource {

    private final Logger log = LoggerFactory.getLogger(NHIUnusalIncidentResource.class);

    private static final String ENTITY_NAME = "nHIUnusalIncident";

    private final NHIUnusalIncidentRepository nHIUnusalIncidentRepository;

    public NHIUnusalIncidentResource(NHIUnusalIncidentRepository nHIUnusalIncidentRepository) {
        this.nHIUnusalIncidentRepository = nHIUnusalIncidentRepository;
    }

    /**
     * POST  /nhi-unusal-incidents : Create a new nHIUnusalIncident.
     *
     * @param nHIUnusalIncident the nHIUnusalIncident to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nHIUnusalIncident, or with status 400 (Bad Request) if the nHIUnusalIncident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-unusal-incidents")
    @Timed
    public ResponseEntity<NHIUnusalIncident> createNHIUnusalIncident(@Valid @RequestBody NHIUnusalIncident nHIUnusalIncident) throws URISyntaxException {
        log.debug("REST request to save NHIUnusalIncident : {}", nHIUnusalIncident);
        if (nHIUnusalIncident.getId() != null) {
            throw new BadRequestAlertException("A new nHIUnusalIncident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHIUnusalIncident result = nHIUnusalIncidentRepository.save(nHIUnusalIncident);
        return ResponseEntity.created(new URI("/api/nhi-unusal-incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-unusal-incidents : Updates an existing nHIUnusalIncident.
     *
     * @param nHIUnusalIncident the nHIUnusalIncident to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nHIUnusalIncident,
     * or with status 400 (Bad Request) if the nHIUnusalIncident is not valid,
     * or with status 500 (Internal Server Error) if the nHIUnusalIncident couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-unusal-incidents")
    @Timed
    public ResponseEntity<NHIUnusalIncident> updateNHIUnusalIncident(@Valid @RequestBody NHIUnusalIncident nHIUnusalIncident) throws URISyntaxException {
        log.debug("REST request to update NHIUnusalIncident : {}", nHIUnusalIncident);
        if (nHIUnusalIncident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHIUnusalIncident result = nHIUnusalIncidentRepository.save(nHIUnusalIncident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nHIUnusalIncident.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-unusal-incidents : get all the nHIUnusalIncidents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nHIUnusalIncidents in body
     */
    @GetMapping("/nhi-unusal-incidents")
    @Timed
    public List<NHIUnusalIncident> getAllNHIUnusalIncidents() {
        log.debug("REST request to get all NHIUnusalIncidents");
        return nHIUnusalIncidentRepository.findAll();
    }

    /**
     * GET  /nhi-unusal-incidents/:id : get the "id" nHIUnusalIncident.
     *
     * @param id the id of the nHIUnusalIncident to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nHIUnusalIncident, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-unusal-incidents/{id}")
    @Timed
    public ResponseEntity<NHIUnusalIncident> getNHIUnusalIncident(@PathVariable Long id) {
        log.debug("REST request to get NHIUnusalIncident : {}", id);
        Optional<NHIUnusalIncident> nHIUnusalIncident = nHIUnusalIncidentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nHIUnusalIncident);
    }

    /**
     * DELETE  /nhi-unusal-incidents/:id : delete the "id" nHIUnusalIncident.
     *
     * @param id the id of the nHIUnusalIncident to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-unusal-incidents/{id}")
    @Timed
    public ResponseEntity<Void> deleteNHIUnusalIncident(@PathVariable Long id) {
        log.debug("REST request to delete NHIUnusalIncident : {}", id);

        nHIUnusalIncidentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
