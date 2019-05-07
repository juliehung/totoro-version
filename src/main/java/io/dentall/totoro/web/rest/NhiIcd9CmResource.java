package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiIcd9Cm;
import io.dentall.totoro.repository.NhiIcd9CmRepository;
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
 * REST controller for managing NhiIcd9Cm.
 */
@RestController
@RequestMapping("/api")
public class NhiIcd9CmResource {

    private final Logger log = LoggerFactory.getLogger(NhiIcd9CmResource.class);

    private static final String ENTITY_NAME = "nhiIcd9Cm";

    private final NhiIcd9CmRepository nhiIcd9CmRepository;

    public NhiIcd9CmResource(NhiIcd9CmRepository nhiIcd9CmRepository) {
        this.nhiIcd9CmRepository = nhiIcd9CmRepository;
    }

    /**
     * POST  /nhi-icd-9-cms : Create a new nhiIcd9Cm.
     *
     * @param nhiIcd9Cm the nhiIcd9Cm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiIcd9Cm, or with status 400 (Bad Request) if the nhiIcd9Cm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-icd-9-cms")
    @Timed
    public ResponseEntity<NhiIcd9Cm> createNhiIcd9Cm(@Valid @RequestBody NhiIcd9Cm nhiIcd9Cm) throws URISyntaxException {
        log.debug("REST request to save NhiIcd9Cm : {}", nhiIcd9Cm);
        if (nhiIcd9Cm.getId() != null) {
            throw new BadRequestAlertException("A new nhiIcd9Cm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiIcd9Cm result = nhiIcd9CmRepository.save(nhiIcd9Cm);
        return ResponseEntity.created(new URI("/api/nhi-icd-9-cms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-icd-9-cms : Updates an existing nhiIcd9Cm.
     *
     * @param nhiIcd9Cm the nhiIcd9Cm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiIcd9Cm,
     * or with status 400 (Bad Request) if the nhiIcd9Cm is not valid,
     * or with status 500 (Internal Server Error) if the nhiIcd9Cm couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-icd-9-cms")
    @Timed
    public ResponseEntity<NhiIcd9Cm> updateNhiIcd9Cm(@Valid @RequestBody NhiIcd9Cm nhiIcd9Cm) throws URISyntaxException {
        log.debug("REST request to update NhiIcd9Cm : {}", nhiIcd9Cm);
        if (nhiIcd9Cm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiIcd9Cm result = nhiIcd9CmRepository.save(nhiIcd9Cm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiIcd9Cm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-icd-9-cms : get all the nhiIcd9Cms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiIcd9Cms in body
     */
    @GetMapping("/nhi-icd-9-cms")
    @Timed
    public List<NhiIcd9Cm> getAllNhiIcd9Cms() {
        log.debug("REST request to get all NhiIcd9Cms");
        return nhiIcd9CmRepository.findAll();
    }

    /**
     * GET  /nhi-icd-9-cms/:id : get the "id" nhiIcd9Cm.
     *
     * @param id the id of the nhiIcd9Cm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiIcd9Cm, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-icd-9-cms/{id}")
    @Timed
    public ResponseEntity<NhiIcd9Cm> getNhiIcd9Cm(@PathVariable Long id) {
        log.debug("REST request to get NhiIcd9Cm : {}", id);
        Optional<NhiIcd9Cm> nhiIcd9Cm = nhiIcd9CmRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nhiIcd9Cm);
    }

    /**
     * DELETE  /nhi-icd-9-cms/:id : delete the "id" nhiIcd9Cm.
     *
     * @param id the id of the nhiIcd9Cm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-icd-9-cms/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiIcd9Cm(@PathVariable Long id) {
        log.debug("REST request to delete NhiIcd9Cm : {}", id);

        nhiIcd9CmRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
