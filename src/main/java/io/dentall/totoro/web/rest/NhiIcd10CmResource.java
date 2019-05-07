package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiIcd10Cm;
import io.dentall.totoro.repository.NhiIcd10CmRepository;
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
 * REST controller for managing NhiIcd10Cm.
 */
@RestController
@RequestMapping("/api")
public class NhiIcd10CmResource {

    private final Logger log = LoggerFactory.getLogger(NhiIcd10CmResource.class);

    private static final String ENTITY_NAME = "nhiIcd10Cm";

    private final NhiIcd10CmRepository nhiIcd10CmRepository;

    public NhiIcd10CmResource(NhiIcd10CmRepository nhiIcd10CmRepository) {
        this.nhiIcd10CmRepository = nhiIcd10CmRepository;
    }

    /**
     * POST  /nhi-icd-10-cms : Create a new nhiIcd10Cm.
     *
     * @param nhiIcd10Cm the nhiIcd10Cm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiIcd10Cm, or with status 400 (Bad Request) if the nhiIcd10Cm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-icd-10-cms")
    @Timed
    public ResponseEntity<NhiIcd10Cm> createNhiIcd10Cm(@Valid @RequestBody NhiIcd10Cm nhiIcd10Cm) throws URISyntaxException {
        log.debug("REST request to save NhiIcd10Cm : {}", nhiIcd10Cm);
        if (nhiIcd10Cm.getId() != null) {
            throw new BadRequestAlertException("A new nhiIcd10Cm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiIcd10Cm result = nhiIcd10CmRepository.save(nhiIcd10Cm);
        return ResponseEntity.created(new URI("/api/nhi-icd-10-cms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-icd-10-cms : Updates an existing nhiIcd10Cm.
     *
     * @param nhiIcd10Cm the nhiIcd10Cm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiIcd10Cm,
     * or with status 400 (Bad Request) if the nhiIcd10Cm is not valid,
     * or with status 500 (Internal Server Error) if the nhiIcd10Cm couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-icd-10-cms")
    @Timed
    public ResponseEntity<NhiIcd10Cm> updateNhiIcd10Cm(@Valid @RequestBody NhiIcd10Cm nhiIcd10Cm) throws URISyntaxException {
        log.debug("REST request to update NhiIcd10Cm : {}", nhiIcd10Cm);
        if (nhiIcd10Cm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiIcd10Cm result = nhiIcd10CmRepository.save(nhiIcd10Cm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiIcd10Cm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-icd-10-cms : get all the nhiIcd10Cms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiIcd10Cms in body
     */
    @GetMapping("/nhi-icd-10-cms")
    @Timed
    public List<NhiIcd10Cm> getAllNhiIcd10Cms() {
        log.debug("REST request to get all NhiIcd10Cms");
        return nhiIcd10CmRepository.findAll();
    }

    /**
     * GET  /nhi-icd-10-cms/:id : get the "id" nhiIcd10Cm.
     *
     * @param id the id of the nhiIcd10Cm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiIcd10Cm, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-icd-10-cms/{id}")
    @Timed
    public ResponseEntity<NhiIcd10Cm> getNhiIcd10Cm(@PathVariable Long id) {
        log.debug("REST request to get NhiIcd10Cm : {}", id);
        Optional<NhiIcd10Cm> nhiIcd10Cm = nhiIcd10CmRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nhiIcd10Cm);
    }

    /**
     * DELETE  /nhi-icd-10-cms/:id : delete the "id" nhiIcd10Cm.
     *
     * @param id the id of the nhiIcd10Cm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-icd-10-cms/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiIcd10Cm(@PathVariable Long id) {
        log.debug("REST request to delete NhiIcd10Cm : {}", id);

        nhiIcd10CmRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
