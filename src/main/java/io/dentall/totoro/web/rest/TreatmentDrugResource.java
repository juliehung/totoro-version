package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.service.TreatmentDrugQueryService;
import io.dentall.totoro.service.TreatmentDrugService;
import io.dentall.totoro.service.dto.TreatmentDrugCriteria;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TreatmentDrug.
 */
@RestController
@RequestMapping("/api")
public class TreatmentDrugResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentDrugResource.class);

    private static final String ENTITY_NAME = "treatmentDrug";

    private final TreatmentDrugService treatmentDrugService;

    private final TreatmentDrugQueryService treatmentDrugQueryService;

    public TreatmentDrugResource(TreatmentDrugService treatmentDrugService, TreatmentDrugQueryService treatmentDrugQueryService) {
        this.treatmentDrugService = treatmentDrugService;
        this.treatmentDrugQueryService = treatmentDrugQueryService;
    }

    /**
     * POST  /treatment-drugs : Create a new treatmentDrug.
     *
     * @param treatmentDrug the treatmentDrug to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatmentDrug, or with status 400 (Bad Request) if the treatmentDrug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatment-drugs")
    @Timed
    public ResponseEntity<TreatmentDrug> createTreatmentDrug(@RequestBody TreatmentDrug treatmentDrug) throws URISyntaxException {
        log.debug("REST request to save TreatmentDrug : {}", treatmentDrug);
        if (treatmentDrug.getId() != null) {
            throw new BadRequestAlertException("A new treatmentDrug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TreatmentDrug result = treatmentDrugService.save(treatmentDrug);
        log.debug("REST request to save TreatmentDrug result : {}", result);
        return ResponseEntity.created(new URI("/api/treatment-drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatment-drugs : Updates an existing treatmentDrug.
     *
     * @param treatmentDrug the treatmentDrug to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatmentDrug,
     * or with status 400 (Bad Request) if the treatmentDrug is not valid,
     * or with status 500 (Internal Server Error) if the treatmentDrug couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatment-drugs")
    @Timed
    public ResponseEntity<TreatmentDrug> updateTreatmentDrug(@RequestBody TreatmentDrug treatmentDrug) throws URISyntaxException {
        log.debug("REST request to update TreatmentDrug : {}", treatmentDrug);
        if (treatmentDrug.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TreatmentDrug result = treatmentDrugService.update(treatmentDrug);
        log.debug("REST request to update TreatmentDrug result : {}", result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatmentDrug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatment-drugs : get all the treatmentDrugs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of treatmentDrugs in body
     */
    @GetMapping("/treatment-drugs")
    @Timed
    public ResponseEntity<List<TreatmentDrug>> getAllTreatmentDrugs(TreatmentDrugCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TreatmentDrugs by criteria: {}", criteria);
        Page<TreatmentDrug> page = treatmentDrugQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/treatment-drugs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /treatment-drugs/count : count all the treatmentDrugs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/treatment-drugs/count")
    @Timed
    public ResponseEntity<Long> countTreatmentDrugs(TreatmentDrugCriteria criteria) {
        log.debug("REST request to count TreatmentDrugs by criteria: {}", criteria);
        return ResponseEntity.ok().body(treatmentDrugQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /treatment-drugs/:id : get the "id" treatmentDrug.
     *
     * @param id the id of the treatmentDrug to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatmentDrug, or with status 404 (Not Found)
     */
    @GetMapping("/treatment-drugs/{id}")
    @Timed
    public ResponseEntity<TreatmentDrug> getTreatmentDrug(@PathVariable Long id) {
        log.debug("REST request to get TreatmentDrug : {}", id);
        Optional<TreatmentDrug> treatmentDrug = treatmentDrugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatmentDrug);
    }

    /**
     * DELETE  /treatment-drugs/:id : delete the "id" treatmentDrug.
     *
     * @param id the id of the treatmentDrug to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatment-drugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatmentDrug(@PathVariable Long id) {
        log.debug("REST request to delete TreatmentDrug : {}", id);
        treatmentDrugService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
