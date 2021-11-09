package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.LedgerGroup;
import io.dentall.totoro.domain.LedgerReceipt;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.LedgerGroupRepository;
import io.dentall.totoro.repository.LedgerReceiptRepository;
import io.dentall.totoro.service.LedgerQueryService;
import io.dentall.totoro.service.LedgerService;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.dto.LedgerCriteria;
import io.dentall.totoro.service.mapper.LedgerGroupMapper;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.LedgerGroupVM;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupUpdateVM;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupVM;
import io.dentall.totoro.web.rest.vm.LedgerVM;
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
import java.util.stream.Collectors;

/**
 * REST controller for managing Ledger.
 */
@RestController
@RequestMapping("/api")
public class LedgerResource {

    private final Logger log = LoggerFactory.getLogger(LedgerResource.class);

    private static final String ENTITY_NAME = "ledger";

    private final LedgerService ledgerService;

    private final LedgerQueryService ledgerQueryService;

    private final LedgerGroupRepository ledgerGroupRepository;

    private final PatientService patientService;

    private final LedgerReceiptRepository ledgerReceiptRepository;

    public LedgerResource(
        LedgerService ledgerService,
        LedgerQueryService ledgerQueryService,
        LedgerGroupRepository ledgerGroupRepository,
        PatientService patientService,
        LedgerReceiptRepository ledgerReceiptRepository
    ) {
        this.ledgerService = ledgerService;
        this.ledgerQueryService = ledgerQueryService;
        this.ledgerGroupRepository = ledgerGroupRepository;
        this.patientService = patientService;
        this.ledgerReceiptRepository = ledgerReceiptRepository;
    }

    /**
     * POST  /ledgers : Create a new ledger.
     *
     * @param ledgerUnwrapGroupVM the ledger to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ledger, or with status 400 (Bad Request) if the ledger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ledgers")
    @Timed
    public ResponseEntity<Ledger> createLedger(@Valid @RequestBody LedgerUnwrapGroupVM ledgerUnwrapGroupVM) throws URISyntaxException {
        log.debug("REST request to save Ledger : {}", ledgerUnwrapGroupVM);
        if (ledgerUnwrapGroupVM.getId() != null) {
            throw new BadRequestAlertException("A new ledger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LedgerGroup lgo = ledgerGroupRepository.findById(ledgerUnwrapGroupVM.getGid())
            .orElseThrow(() -> new BadRequestAlertException("Can not found ledger group", ENTITY_NAME, "notfound"));

        Ledger ledger = LedgerGroupMapper.INSTANCE.convertLedgerUnwrapGroupVMToLedger(ledgerUnwrapGroupVM);
        ledger.setLedgerGroup(lgo);
        Ledger result = ledgerService.save(ledger);
        return ResponseEntity.created(new URI("/api/ledgers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ledgers : Updates an existing ledger.
     *
     * @param ledger the ledger to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ledger,
     * or with status 400 (Bad Request) if the ledger is not valid,
     * or with status 500 (Internal Server Error) if the ledger couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ledgers")
    @Timed
    public ResponseEntity<Ledger> updateLedger(@Valid @RequestBody LedgerUnwrapGroupUpdateVM ledger) throws URISyntaxException {
        log.debug("REST request to update Ledger : {}", ledger);
        if (ledger.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ledger result = ledgerService.update(ledger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ledger.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ledgers : get all the ledgers.
     * 2021-03-17: 僅使用 patientId 作為 query string
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ledgers in body
     */
    @GetMapping("/ledgers")
    @Timed
    public ResponseEntity<List<LedgerVM>> getAllLedgers(LedgerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ledgers by criteria: {}", criteria);
        Page<Ledger> page = ledgerQueryService.findByCriteria(criteria, pageable);
        List<LedgerVM> result = page.getContent().stream()
            .map(d -> {
                LedgerUnwrapGroupVM ledgerUnwrapGroupVM = LedgerGroupMapper.INSTANCE.convertLedgerToLedgerUnwrapGroupVM(d);
                LedgerVM ledgerVM = new LedgerVM();
                ledgerVM.setLedger(ledgerUnwrapGroupVM);

                Patient p = patientService.findPatientById(d.getLedgerGroup().getPatientId());
                ledgerVM.setPatient(p);

                return ledgerVM;
            })
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ledgers");
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
    * GET  /ledgers/count : count all the ledgers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/ledgers/count")
    @Timed
    public ResponseEntity<Long> countLedgers(LedgerCriteria criteria) {
        log.debug("REST request to count Ledgers by criteria: {}", criteria);
        return ResponseEntity.ok().body(ledgerQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /ledgers/:id : get the "id" ledger.
     *
     * @param id the id of the ledger to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ledger, or with status 404 (Not Found)
     */
    @Deprecated
    @GetMapping("/ledgers/{id}")
    @Timed
    public ResponseEntity<Ledger> getLedger(@PathVariable Long id) {
        log.debug("REST request to get Ledger : {}", id);
        Optional<Ledger> ledger = ledgerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ledger);
    }

    /**
     * DELETE  /ledgers/:id : delete the "id" ledger.
     *
     * @param id the id of the ledger to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ledgers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLedger(@PathVariable Long id) {
        log.debug("REST request to delete Ledger : {}", id);
        ledgerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/ledgers/receipts")
    @Timed
    public LedgerGroupVM createLedgerV2(@RequestBody LedgerReceipt ledgerReceipt) {

        log.info("Hello: {}", ledgerReceipt);
        ledgerReceiptRepository.save(ledgerReceipt);

        return new LedgerGroupVM();
    }
}
