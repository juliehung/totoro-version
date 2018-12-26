package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NHICategory;
import io.dentall.totoro.repository.NHICategoryRepository;
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
 * REST controller for managing NHICategory.
 */
@RestController
@RequestMapping("/api")
public class NHICategoryResource {

    private final Logger log = LoggerFactory.getLogger(NHICategoryResource.class);

    private static final String ENTITY_NAME = "nHICategory";

    private final NHICategoryRepository nHICategoryRepository;

    public NHICategoryResource(NHICategoryRepository nHICategoryRepository) {
        this.nHICategoryRepository = nHICategoryRepository;
    }

    /**
     * POST  /nhi-categories : Create a new nHICategory.
     *
     * @param nHICategory the nHICategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nHICategory, or with status 400 (Bad Request) if the nHICategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-categories")
    @Timed
    public ResponseEntity<NHICategory> createNHICategory(@Valid @RequestBody NHICategory nHICategory) throws URISyntaxException {
        log.debug("REST request to save NHICategory : {}", nHICategory);
        if (nHICategory.getId() != null) {
            throw new BadRequestAlertException("A new nHICategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHICategory result = nHICategoryRepository.save(nHICategory);
        return ResponseEntity.created(new URI("/api/nhi-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-categories : Updates an existing nHICategory.
     *
     * @param nHICategory the nHICategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nHICategory,
     * or with status 400 (Bad Request) if the nHICategory is not valid,
     * or with status 500 (Internal Server Error) if the nHICategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-categories")
    @Timed
    public ResponseEntity<NHICategory> updateNHICategory(@Valid @RequestBody NHICategory nHICategory) throws URISyntaxException {
        log.debug("REST request to update NHICategory : {}", nHICategory);
        if (nHICategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHICategory result = nHICategoryRepository.save(nHICategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nHICategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-categories : get all the nHICategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nHICategories in body
     */
    @GetMapping("/nhi-categories")
    @Timed
    public List<NHICategory> getAllNHICategories() {
        log.debug("REST request to get all NHICategories");
        return nHICategoryRepository.findAll();
    }

    /**
     * GET  /nhi-categories/:id : get the "id" nHICategory.
     *
     * @param id the id of the nHICategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nHICategory, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-categories/{id}")
    @Timed
    public ResponseEntity<NHICategory> getNHICategory(@PathVariable Long id) {
        log.debug("REST request to get NHICategory : {}", id);
        Optional<NHICategory> nHICategory = nHICategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nHICategory);
    }

    /**
     * DELETE  /nhi-categories/:id : delete the "id" nHICategory.
     *
     * @param id the id of the nHICategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteNHICategory(@PathVariable Long id) {
        log.debug("REST request to delete NHICategory : {}", id);

        nHICategoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
