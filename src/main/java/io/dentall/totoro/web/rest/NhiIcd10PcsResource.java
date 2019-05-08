package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiIcd10Pcs;
import io.dentall.totoro.repository.NhiIcd10PcsRepository;
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
 * REST controller for managing NhiIcd10Pcs.
 */
@RestController
@RequestMapping("/api")
public class NhiIcd10PcsResource {

    private final Logger log = LoggerFactory.getLogger(NhiIcd10PcsResource.class);

    private static final String ENTITY_NAME = "nhiIcd10Pcs";

    private final NhiIcd10PcsRepository nhiIcd10PcsRepository;

    public NhiIcd10PcsResource(NhiIcd10PcsRepository nhiIcd10PcsRepository) {
        this.nhiIcd10PcsRepository = nhiIcd10PcsRepository;
    }

    /**
     * POST  /nhi-icd-10-pcs : Create a new nhiIcd10Pcs.
     *
     * @param nhiIcd10Pcs the nhiIcd10Pcs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiIcd10Pcs, or with status 400 (Bad Request) if the nhiIcd10Pcs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-icd-10-pcs")
    @Timed
    public ResponseEntity<NhiIcd10Pcs> createNhiIcd10Pcs(@Valid @RequestBody NhiIcd10Pcs nhiIcd10Pcs) throws URISyntaxException {
        log.debug("REST request to save NhiIcd10Pcs : {}", nhiIcd10Pcs);
        if (nhiIcd10Pcs.getId() != null) {
            throw new BadRequestAlertException("A new nhiIcd10Pcs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiIcd10Pcs result = nhiIcd10PcsRepository.save(nhiIcd10Pcs);
        return ResponseEntity.created(new URI("/api/nhi-icd-10-pcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-icd-10-pcs : Updates an existing nhiIcd10Pcs.
     *
     * @param nhiIcd10Pcs the nhiIcd10Pcs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiIcd10Pcs,
     * or with status 400 (Bad Request) if the nhiIcd10Pcs is not valid,
     * or with status 500 (Internal Server Error) if the nhiIcd10Pcs couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-icd-10-pcs")
    @Timed
    public ResponseEntity<NhiIcd10Pcs> updateNhiIcd10Pcs(@Valid @RequestBody NhiIcd10Pcs nhiIcd10Pcs) throws URISyntaxException {
        log.debug("REST request to update NhiIcd10Pcs : {}", nhiIcd10Pcs);
        if (nhiIcd10Pcs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiIcd10Pcs result = nhiIcd10PcsRepository.save(nhiIcd10Pcs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiIcd10Pcs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-icd-10-pcs : get all the nhiIcd10Pcs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiIcd10Pcs in body
     */
    @GetMapping("/nhi-icd-10-pcs")
    @Timed
    public List<NhiIcd10Pcs> getAllNhiIcd10Pcs() {
        log.debug("REST request to get all NhiIcd10Pcs");
        return nhiIcd10PcsRepository.findAll();
    }

    /**
     * GET  /nhi-icd-10-pcs/:id : get the "id" nhiIcd10Pcs.
     *
     * @param id the id of the nhiIcd10Pcs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiIcd10Pcs, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-icd-10-pcs/{id}")
    @Timed
    public ResponseEntity<NhiIcd10Pcs> getNhiIcd10Pcs(@PathVariable Long id) {
        log.debug("REST request to get NhiIcd10Pcs : {}", id);
        Optional<NhiIcd10Pcs> nhiIcd10Pcs = nhiIcd10PcsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nhiIcd10Pcs);
    }

    /**
     * DELETE  /nhi-icd-10-pcs/:id : delete the "id" nhiIcd10Pcs.
     *
     * @param id the id of the nhiIcd10Pcs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-icd-10-pcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiIcd10Pcs(@PathVariable Long id) {
        log.debug("REST request to delete NhiIcd10Pcs : {}", id);

        nhiIcd10PcsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
