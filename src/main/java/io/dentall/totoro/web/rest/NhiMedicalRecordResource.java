package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.repository.NhiMedicineRepository;
import io.dentall.totoro.repository.NhiTxRepository;
import io.dentall.totoro.service.NhiMedicalRecordQueryService;
import io.dentall.totoro.service.NhiMedicalRecordService;
import io.dentall.totoro.service.dto.NhiMedicalRecordCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.NhiMedicalRecordVM;
import io.github.jhipster.service.filter.LongFilter;
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
 * REST controller for managing NhiMedicalRecord.
 */
@RestController
@RequestMapping("/api")
public class NhiMedicalRecordResource {

    private final Logger log = LoggerFactory.getLogger(NhiMedicalRecordResource.class);

    private static final String ENTITY_NAME = "nhiMedicalRecord";

    private final NhiMedicalRecordService nhiMedicalRecordService;

    private final NhiMedicalRecordQueryService nhiMedicalRecordQueryService;

    private final NhiTxRepository nhiTxRepository;

    private final NhiMedicineRepository nhiMedicineRepository;

    public NhiMedicalRecordResource(
        NhiMedicalRecordService nhiMedicalRecordService,
        NhiMedicalRecordQueryService nhiMedicalRecordQueryService,
        NhiTxRepository nhiTxRepository,
        NhiMedicineRepository nhiMedicineRepository
    ) {
        this.nhiMedicalRecordService = nhiMedicalRecordService;
        this.nhiMedicalRecordQueryService = nhiMedicalRecordQueryService;
        this.nhiTxRepository = nhiTxRepository;
        this.nhiMedicineRepository = nhiMedicineRepository;
    }

    /**
     * POST  /nhi-medical-records : Create a new nhiMedicalRecord.
     *
     * @param nhiMedicalRecord the nhiMedicalRecord to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiMedicalRecord, or with status 400 (Bad Request) if the nhiMedicalRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-medical-records")
    @Timed
    public ResponseEntity<NhiMedicalRecord> createNhiMedicalRecord(@RequestBody NhiMedicalRecord nhiMedicalRecord) throws URISyntaxException {
        log.debug("REST request to save NhiMedicalRecord : {}", nhiMedicalRecord);
        if (nhiMedicalRecord.getId() != null) {
            throw new BadRequestAlertException("A new nhiMedicalRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }

        NhiMedicalRecordCriteria criteria = new NhiMedicalRecordCriteria();
        LongFilter lf = new LongFilter();
        lf.setEquals(nhiMedicalRecord.getNhiExtendPatient().getId());
        criteria.setNhiExtendPatientId(lf);
        if (nhiMedicalRecordQueryService.findByCriteria(criteria).stream()
            .anyMatch(queriedValue -> queriedValue.equals(nhiMedicalRecord))
        ) {
            throw new BadRequestAlertException("Already has this nhi medical record", ENTITY_NAME, "nhi.medical.record.duplicated");
        }

        NhiMedicalRecord result = nhiMedicalRecordService.save(nhiMedicalRecord);
        return ResponseEntity.created(new URI("/api/nhi-medical-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-medical-records : Updates an existing nhiMedicalRecord.
     *
     * @param nhiMedicalRecord the nhiMedicalRecord to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiMedicalRecord,
     * or with status 400 (Bad Request) if the nhiMedicalRecord is not valid,
     * or with status 500 (Internal Server Error) if the nhiMedicalRecord couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-medical-records")
    @Timed
    public ResponseEntity<NhiMedicalRecord> updateNhiMedicalRecord(@RequestBody NhiMedicalRecord nhiMedicalRecord) throws URISyntaxException {
        log.debug("REST request to update NhiMedicalRecord : {}", nhiMedicalRecord);
        if (nhiMedicalRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiMedicalRecord result = nhiMedicalRecordService.update(nhiMedicalRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiMedicalRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-medical-records : get all the nhiMedicalRecords.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of nhiMedicalRecords in body
     */
    @GetMapping("/nhi-medical-records")
    @Timed
    public ResponseEntity<List<NhiMedicalRecordVM>> getAllNhiMedicalRecords(NhiMedicalRecordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NhiMedicalRecords by criteria: {}", criteria);
        Page<NhiMedicalRecordVM> entityList = nhiMedicalRecordQueryService.findVmByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entityList, "/api/nhi-medical-records");
        return ResponseEntity.ok().headers(headers).body(entityList.getContent());
    }

    /**
    * GET  /nhi-medical-records/count : count all the nhiMedicalRecords.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/nhi-medical-records/count")
    @Timed
    public ResponseEntity<Long> countNhiMedicalRecords(NhiMedicalRecordCriteria criteria) {
        log.debug("REST request to count NhiMedicalRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhiMedicalRecordQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /nhi-medical-records/:id : get the "id" nhiMedicalRecord.
     *
     * @param id the id of the nhiMedicalRecord to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiMedicalRecord, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-medical-records/{id}")
    @Timed
    public ResponseEntity<NhiMedicalRecord> getNhiMedicalRecord(@PathVariable Long id) {
        log.debug("REST request to get NhiMedicalRecord : {}", id);
        Optional<NhiMedicalRecord> nhiMedicalRecord = nhiMedicalRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiMedicalRecord);
    }

    /**
     * DELETE  /nhi-medical-records/:id : delete the "id" nhiMedicalRecord.
     *
     * @param id the id of the nhiMedicalRecord to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-medical-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiMedicalRecord(@PathVariable Long id) {
        log.debug("REST request to delete NhiMedicalRecord : {}", id);
        nhiMedicalRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
