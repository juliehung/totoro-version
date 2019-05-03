package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NHIExtendTreatmentDrug;
import io.dentall.totoro.service.NHIExtendTreatmentDrugService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NHIExtendTreatmentDrug.
 */
@RestController
@RequestMapping("/api")
public class NHIExtendTreatmentDrugResource {

    private final Logger log = LoggerFactory.getLogger(NHIExtendTreatmentDrugResource.class);

    private static final String ENTITY_NAME = "nhiExtendTreatmentDrug";

    private final NHIExtendTreatmentDrugService nhiExtendTreatmentDrugService;

    public NHIExtendTreatmentDrugResource(NHIExtendTreatmentDrugService nhiExtendTreatmentDrugService) {
        this.nhiExtendTreatmentDrugService = nhiExtendTreatmentDrugService;
    }

    /**
     * POST  /nhi-extend-treatment-drugs : Create a new nhiExtendTreatmentDrug.
     *
     * @param nhiExtendTreatmentDrug the nhiExtendTreatmentDrug to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiExtendTreatmentDrug, or with status 400 (Bad Request) if the nhiExtendTreatmentDrug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-extend-treatment-drugs")
    @Timed
    public ResponseEntity<NHIExtendTreatmentDrug> createNHIExtendTreatmentDrug(@Valid @RequestBody NHIExtendTreatmentDrug nhiExtendTreatmentDrug) throws URISyntaxException {
        log.debug("REST request to save NHIExtendTreatmentDrug : {}", nhiExtendTreatmentDrug);
        if (nhiExtendTreatmentDrug.getId() != null) {
            throw new BadRequestAlertException("A new nhiExtendTreatmentDrug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHIExtendTreatmentDrug result = nhiExtendTreatmentDrugService.save(nhiExtendTreatmentDrug);
        return ResponseEntity.created(new URI("/api/nhi-extend-treatment-drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-extend-treatment-drugs : Updates an existing nhiExtendTreatmentDrug.
     *
     * @param nhiExtendTreatmentDrug the nhiExtendTreatmentDrug to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiExtendTreatmentDrug,
     * or with status 400 (Bad Request) if the nhiExtendTreatmentDrug is not valid,
     * or with status 500 (Internal Server Error) if the nhiExtendTreatmentDrug couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-extend-treatment-drugs")
    @Timed
    public ResponseEntity<NHIExtendTreatmentDrug> updateNHIExtendTreatmentDrug(@Valid @RequestBody NHIExtendTreatmentDrug nhiExtendTreatmentDrug) throws URISyntaxException {
        log.debug("REST request to update NHIExtendTreatmentDrug : {}", nhiExtendTreatmentDrug);
        if (nhiExtendTreatmentDrug.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHIExtendTreatmentDrug result = nhiExtendTreatmentDrugService.update(nhiExtendTreatmentDrug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiExtendTreatmentDrug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-extend-treatment-drugs : get all the nhiExtendTreatmentDrug.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhiExtendTreatmentDrug in body
     */
    @GetMapping("/nhi-extend-treatment-drugs")
    @Timed
    public ResponseEntity<List<NHIExtendTreatmentDrug>> getAllNHIExtendTreatmentDrugs(Pageable pageable) {
        log.debug("REST request to get a page of NHIExtendTreatmentDrug");
        Page<NHIExtendTreatmentDrug> page = nhiExtendTreatmentDrugService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhi-extend-treatment-drugs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhi-extend-treatment-drugs/:id : get the "id" nhiExtendTreatmentDrug.
     *
     * @param id the id of the nhiExtendTreatmentDrug to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiExtendTreatmentDrug, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-extend-treatment-drugs/{id}")
    @Timed
    public ResponseEntity<NHIExtendTreatmentDrug> getNHIExtendTreatmentDrug(@PathVariable Long id) {
        log.debug("REST request to get NHIExtendTreatmentDrug : {}", id);
        Optional<NHIExtendTreatmentDrug> nhiExtendTreatmentDrug = nhiExtendTreatmentDrugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiExtendTreatmentDrug);
    }

    /**
     * DELETE  /nhi-extend-treatment-drugs/:id : delete the "id" nhiExtendTreatmentDrug.
     *
     * @param id the id of the nhiExtendTreatmentDrug to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-extend-treatment-drugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteNHIExtendTreatmentDrug(@PathVariable Long id) {
        log.debug("REST request to delete NHIExtendTreatmentDrug : {}", id);

        nhiExtendTreatmentDrugService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
