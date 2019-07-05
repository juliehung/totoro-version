package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.RelationshipOptions;
import io.dentall.totoro.repository.RelationshipOptionsRepository;
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
 * REST controller for managing RelationshipOptions.
 */
@RestController
@RequestMapping("/api")
public class RelationshipOptionsResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipOptionsResource.class);

    private static final String ENTITY_NAME = "relationshipOptions";

    private final RelationshipOptionsRepository relationshipOptionsRepository;

    public RelationshipOptionsResource(RelationshipOptionsRepository relationshipOptionsRepository) {
        this.relationshipOptionsRepository = relationshipOptionsRepository;
    }

    /**
     * POST  /relationship-options : Create a new relationshipOptions.
     *
     * @param relationshipOptions the relationshipOptions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relationshipOptions, or with status 400 (Bad Request) if the relationshipOptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/relationship-options")
    @Timed
    public ResponseEntity<RelationshipOptions> createRelationshipOptions(@RequestBody RelationshipOptions relationshipOptions) throws URISyntaxException {
        log.debug("REST request to save RelationshipOptions : {}", relationshipOptions);
        if (relationshipOptions.getId() != null) {
            throw new BadRequestAlertException("A new relationshipOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelationshipOptions result = relationshipOptionsRepository.save(relationshipOptions);
        return ResponseEntity.created(new URI("/api/relationship-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /relationship-options : Updates an existing relationshipOptions.
     *
     * @param relationshipOptions the relationshipOptions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated relationshipOptions,
     * or with status 400 (Bad Request) if the relationshipOptions is not valid,
     * or with status 500 (Internal Server Error) if the relationshipOptions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/relationship-options")
    @Timed
    public ResponseEntity<RelationshipOptions> updateRelationshipOptions(@RequestBody RelationshipOptions relationshipOptions) throws URISyntaxException {
        log.debug("REST request to update RelationshipOptions : {}", relationshipOptions);
        if (relationshipOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RelationshipOptions result = relationshipOptionsRepository.save(relationshipOptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, relationshipOptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /relationship-options : get all the relationshipOptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of relationshipOptions in body
     */
    @GetMapping("/relationship-options")
    @Timed
    public List<RelationshipOptions> getAllRelationshipOptions() {
        log.debug("REST request to get all RelationshipOptions");
        return relationshipOptionsRepository.findAll();
    }

    /**
     * GET  /relationship-options/:id : get the "id" relationshipOptions.
     *
     * @param id the id of the relationshipOptions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the relationshipOptions, or with status 404 (Not Found)
     */
    @GetMapping("/relationship-options/{id}")
    @Timed
    public ResponseEntity<RelationshipOptions> getRelationshipOptions(@PathVariable Long id) {
        log.debug("REST request to get RelationshipOptions : {}", id);
        Optional<RelationshipOptions> relationshipOptions = relationshipOptionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(relationshipOptions);
    }

    /**
     * DELETE  /relationship-options/:id : delete the "id" relationshipOptions.
     *
     * @param id the id of the relationshipOptions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/relationship-options/{id}")
    @Timed
    public ResponseEntity<Void> deleteRelationshipOptions(@PathVariable Long id) {
        log.debug("REST request to delete RelationshipOptions : {}", id);

        relationshipOptionsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
