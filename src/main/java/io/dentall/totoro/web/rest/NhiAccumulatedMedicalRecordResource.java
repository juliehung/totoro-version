package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiAccumulatedMedicalRecord;
import io.dentall.totoro.service.NhiAccumulatedMedicalRecordService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.NhiAccumulatedMedicalRecordCriteria;
import io.dentall.totoro.service.NhiAccumulatedMedicalRecordQueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NhiAccumulatedMedicalRecord.
 */
@RestController
@RequestMapping("/api")
public class NhiAccumulatedMedicalRecordResource {

    private final Logger log = LoggerFactory.getLogger(NhiAccumulatedMedicalRecordResource.class);

    private static final String ENTITY_NAME = "nhiAccumulatedMedicalRecord";

    private final NhiAccumulatedMedicalRecordService nhiAccumulatedMedicalRecordService;

    private final NhiAccumulatedMedicalRecordQueryService nhiAccumulatedMedicalRecordQueryService;

    public NhiAccumulatedMedicalRecordResource(NhiAccumulatedMedicalRecordService nhiAccumulatedMedicalRecordService, NhiAccumulatedMedicalRecordQueryService nhiAccumulatedMedicalRecordQueryService) {
        this.nhiAccumulatedMedicalRecordService = nhiAccumulatedMedicalRecordService;
        this.nhiAccumulatedMedicalRecordQueryService = nhiAccumulatedMedicalRecordQueryService;
    }

    /**
     * POST  /nhi-accumulated-medical-records : Create a new nhiAccumulatedMedicalRecord.
     *
     * @param nhiAccumulatedMedicalRecord the nhiAccumulatedMedicalRecord to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiAccumulatedMedicalRecord, or with status 400 (Bad Request) if the nhiAccumulatedMedicalRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-accumulated-medical-records")
    @Timed
    public ResponseEntity<NhiAccumulatedMedicalRecord> createNhiAccumulatedMedicalRecord(@Valid @RequestBody NhiAccumulatedMedicalRecord nhiAccumulatedMedicalRecord) throws URISyntaxException {
        log.debug("REST request to save NhiAccumulatedMedicalRecord : {}", nhiAccumulatedMedicalRecord);
        if (nhiAccumulatedMedicalRecord.getId() != null) {
            throw new BadRequestAlertException("A new nhiAccumulatedMedicalRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }

        NhiAccumulatedMedicalRecordCriteria criteria = new NhiAccumulatedMedicalRecordCriteria();
        LongFilter lf = new LongFilter();
        lf.setEquals(nhiAccumulatedMedicalRecord.getPatient().getId());
        criteria.setPatientId(lf);
        if (nhiAccumulatedMedicalRecordQueryService.findByCriteria(criteria).stream()
                .anyMatch(queriedValue -> queriedValue.equals(nhiAccumulatedMedicalRecord))
        ) {
            throw new BadRequestAlertException("Already has this nhi accumulated medical record", ENTITY_NAME, "nhiaccumulatedmedicalrecordexists");
        }

        NhiAccumulatedMedicalRecord result = nhiAccumulatedMedicalRecordService.save(nhiAccumulatedMedicalRecord);
        return ResponseEntity.created(new URI("/api/nhi-accumulated-medical-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-accumulated-medical-records : Updates an existing nhiAccumulatedMedicalRecord.
     *
     * @param nhiAccumulatedMedicalRecord the nhiAccumulatedMedicalRecord to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiAccumulatedMedicalRecord,
     * or with status 400 (Bad Request) if the nhiAccumulatedMedicalRecord is not valid,
     * or with status 500 (Internal Server Error) if the nhiAccumulatedMedicalRecord couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-accumulated-medical-records")
    @Timed
    public ResponseEntity<NhiAccumulatedMedicalRecord> updateNhiAccumulatedMedicalRecord(@Valid @RequestBody NhiAccumulatedMedicalRecord nhiAccumulatedMedicalRecord) throws URISyntaxException {
        log.debug("REST request to update NhiAccumulatedMedicalRecord : {}", nhiAccumulatedMedicalRecord);
        if (nhiAccumulatedMedicalRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiAccumulatedMedicalRecord result = nhiAccumulatedMedicalRecordService.save(nhiAccumulatedMedicalRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiAccumulatedMedicalRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-accumulated-medical-records : get all the nhiAccumulatedMedicalRecords.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of nhiAccumulatedMedicalRecords in body
     */
    @GetMapping("/nhi-accumulated-medical-records")
    @Timed
    public ResponseEntity<List<NhiAccumulatedMedicalRecord>> getAllNhiAccumulatedMedicalRecords(NhiAccumulatedMedicalRecordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NhiAccumulatedMedicalRecords by criteria: {}", criteria);
        Page<NhiAccumulatedMedicalRecord> page = nhiAccumulatedMedicalRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhi-accumulated-medical-records");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /nhi-accumulated-medical-records/count : count all the nhiAccumulatedMedicalRecords.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/nhi-accumulated-medical-records/count")
    @Timed
    public ResponseEntity<Long> countNhiAccumulatedMedicalRecords(NhiAccumulatedMedicalRecordCriteria criteria) {
        log.debug("REST request to count NhiAccumulatedMedicalRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhiAccumulatedMedicalRecordQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /nhi-accumulated-medical-records/:id : get the "id" nhiAccumulatedMedicalRecord.
     *
     * @param id the id of the nhiAccumulatedMedicalRecord to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiAccumulatedMedicalRecord, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-accumulated-medical-records/{id}")
    @Timed
    public ResponseEntity<NhiAccumulatedMedicalRecord> getNhiAccumulatedMedicalRecord(@PathVariable Long id) {
        log.debug("REST request to get NhiAccumulatedMedicalRecord : {}", id);
        Optional<NhiAccumulatedMedicalRecord> nhiAccumulatedMedicalRecord = nhiAccumulatedMedicalRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiAccumulatedMedicalRecord);
    }

    /**
     * DELETE  /nhi-accumulated-medical-records/:id : delete the "id" nhiAccumulatedMedicalRecord.
     *
     * @param id the id of the nhiAccumulatedMedicalRecord to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-accumulated-medical-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiAccumulatedMedicalRecord(@PathVariable Long id) {
        log.debug("REST request to delete NhiAccumulatedMedicalRecord : {}", id);
        nhiAccumulatedMedicalRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
