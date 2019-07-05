package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.MarriageOptions;
import io.dentall.totoro.repository.MarriageOptionsRepository;
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
 * REST controller for managing MarriageOptions.
 */
@RestController
@RequestMapping("/api")
public class MarriageOptionsResource {

    private final Logger log = LoggerFactory.getLogger(MarriageOptionsResource.class);

    private static final String ENTITY_NAME = "marriageOptions";

    private final MarriageOptionsRepository marriageOptionsRepository;

    public MarriageOptionsResource(MarriageOptionsRepository marriageOptionsRepository) {
        this.marriageOptionsRepository = marriageOptionsRepository;
    }

    /**
     * POST  /marriage-options : Create a new marriageOptions.
     *
     * @param marriageOptions the marriageOptions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marriageOptions, or with status 400 (Bad Request) if the marriageOptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marriage-options")
    @Timed
    public ResponseEntity<MarriageOptions> createMarriageOptions(@RequestBody MarriageOptions marriageOptions) throws URISyntaxException {
        log.debug("REST request to save MarriageOptions : {}", marriageOptions);
        if (marriageOptions.getId() != null) {
            throw new BadRequestAlertException("A new marriageOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarriageOptions result = marriageOptionsRepository.save(marriageOptions);
        return ResponseEntity.created(new URI("/api/marriage-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marriage-options : Updates an existing marriageOptions.
     *
     * @param marriageOptions the marriageOptions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marriageOptions,
     * or with status 400 (Bad Request) if the marriageOptions is not valid,
     * or with status 500 (Internal Server Error) if the marriageOptions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marriage-options")
    @Timed
    public ResponseEntity<MarriageOptions> updateMarriageOptions(@RequestBody MarriageOptions marriageOptions) throws URISyntaxException {
        log.debug("REST request to update MarriageOptions : {}", marriageOptions);
        if (marriageOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MarriageOptions result = marriageOptionsRepository.save(marriageOptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marriageOptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marriage-options : get all the marriageOptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marriageOptions in body
     */
    @GetMapping("/marriage-options")
    @Timed
    public List<MarriageOptions> getAllMarriageOptions() {
        log.debug("REST request to get all MarriageOptions");
        return marriageOptionsRepository.findAll();
    }

    /**
     * GET  /marriage-options/:id : get the "id" marriageOptions.
     *
     * @param id the id of the marriageOptions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marriageOptions, or with status 404 (Not Found)
     */
    @GetMapping("/marriage-options/{id}")
    @Timed
    public ResponseEntity<MarriageOptions> getMarriageOptions(@PathVariable Long id) {
        log.debug("REST request to get MarriageOptions : {}", id);
        Optional<MarriageOptions> marriageOptions = marriageOptionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(marriageOptions);
    }

    /**
     * DELETE  /marriage-options/:id : delete the "id" marriageOptions.
     *
     * @param id the id of the marriageOptions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marriage-options/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarriageOptions(@PathVariable Long id) {
        log.debug("REST request to delete MarriageOptions : {}", id);

        marriageOptionsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
