package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.LedgerGroupRepository;
import io.dentall.totoro.repository.LedgerReceiptPrintedRecordRepository;
import io.dentall.totoro.repository.LedgerReceiptRepository;
import io.dentall.totoro.service.*;
import io.dentall.totoro.service.dto.LedgerCriteria;
import io.dentall.totoro.service.mapper.LedgerGroupMapper;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Ledger.
 */
@Profile("img-gcs")
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

    private final LedgerReceiptPrintedRecordRepository ledgerReceiptPrintedRecordRepository;

    private final ImageGcsBusinessService imageGcsBusinessService;

    public LedgerResource(
        LedgerService ledgerService,
        LedgerQueryService ledgerQueryService,
        LedgerGroupRepository ledgerGroupRepository,
        PatientService patientService,
        LedgerReceiptRepository ledgerReceiptRepository,
        LedgerReceiptPrintedRecordRepository ledgerReceiptPrintedRecordRepository,
        ImageGcsBusinessService imageGcsBusinessService
    ) {
        this.ledgerService = ledgerService;
        this.ledgerQueryService = ledgerQueryService;
        this.ledgerGroupRepository = ledgerGroupRepository;
        this.patientService = patientService;
        this.ledgerReceiptRepository = ledgerReceiptRepository;
        this.ledgerReceiptPrintedRecordRepository = ledgerReceiptPrintedRecordRepository;
        this.imageGcsBusinessService = imageGcsBusinessService;
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
                LedgerVM ledgerVM = LedgerGroupMapper.INSTANCE.convertLedgerFromDomainToVM(d);

                ledgerVM.getLedgerReceipts()
                    .forEach(d2 -> {
                        d2.getLedgerReceiptPrintedRecords()
                            .forEach(d3 -> {
                                d3.setUrl(imageGcsBusinessService.getUrlForDownload()
                                    .concat(d3.getFilePath())
                                    .concat(d3.getFileName())
                                );
                            });
                    });

                Patient p = patientService.findPatientById(d.getLedgerGroup().getPatientId());
                ledgerVM.setPatient(p);

                return ledgerVM;
            })
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ledgers");
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @GetMapping("/ledger-receipts")
    @Timed
    public List<LedgerReceiptExcelVM> findLedgerReceiptsByGid(
        @RequestParam(name = "gid") Long gid
    ) {
        return LedgerGroupMapper.INSTANCE.ledgerReceiptListToLedgerReceiptExcelVMList(
            ledgerReceiptRepository.findAllByLedgerGroup_id(gid)
        );
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

    @PostMapping("/ledger-receipts")
    @Timed
    public LedgerReceiptVM createLedgerReceipt(@Valid @RequestBody LedgerReceiptCreateVM ledgerReceiptCreateVM) {
        if (ledgerReceiptCreateVM.getLedgers().size() == 0) {
            throw new BadRequestAlertException("Associated ledger for ledger receipt is required.", ENTITY_NAME, "fieldrequired");
        }

        ledgerService.validateLedgersInLedgerReceipt(ledgerReceiptCreateVM);

        LedgerReceipt ledgerReceipt = LedgerGroupMapper.INSTANCE.convertLedgerReceiptFromCreateVMToDomain(
            ledgerReceiptCreateVM
        );

        return LedgerGroupMapper.INSTANCE.convertLedgerReceiptFromDomainToVM(
            ledgerReceiptRepository.save(ledgerReceipt)
        );
    }

    @PostMapping("/ledger-receipts/{id}/ledger-receipt-printed-records")
    @Timed
    @Transactional
    public LedgerReceiptPrintedRecordVM createLedgerReceiptRecord(
        @PathVariable(name = "id") Long id,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        LedgerReceipt ledgerReceipt = ledgerReceiptRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Can not found ledger receipt by id", ENTITY_NAME, "notfound"));
        if (ledgerReceipt.getLedgers() == null ||
            ledgerReceipt.getLedgers().get(0) == null ||
            ledgerReceipt.getLedgers().get(0).getId() == null ||
            ledgerReceipt.getLedgers().get(0).getPatientId() == null ||
            ledgerReceipt.getLedgers().get(0).getLedgerGroup() == null
        ) {
            throw new BadRequestAlertException("Can not found ledger receipt by id", ENTITY_NAME, "fieldrequired");
        }
        if (!file.getContentType().equals("application/pdf")) {
            throw new BadRequestAlertException("Only support file mime as application/pdf", ENTITY_NAME, "mimerequired");
        }

        Patient patient = patientService.findPatientById(ledgerReceipt.getLedgers().get(0).getPatientId());

        String filePath = imageGcsBusinessService.getClinicName()
            .concat("/")
            .concat(patient.getId().toString())
            .concat("/");
        String fileName = "收支紀錄"
            .concat("_")
            .concat(patient.getName())
            .concat(patient.getBirth().toString())
            .concat("_")
            .concat(ledgerReceipt.getLedgers().get(0).getLedgerGroup().getProjectCode())
            .concat(ledgerReceipt.getLedgers().get(0).getLedgerGroup().getDisplayName())
            .concat(".pdf");

        imageGcsBusinessService.uploadFile(
            filePath,
            fileName,
            file.getBytes(),
            "application/pdf"
        );

        LedgerReceiptPrintedRecord ledgerReceiptPrintedRecord = new LedgerReceiptPrintedRecord();
        ledgerReceiptPrintedRecord.setTime(Instant.now());
        ledgerReceiptPrintedRecord.setFilePath(filePath);
        ledgerReceiptPrintedRecord.setFileName(fileName);
        ledgerReceiptPrintedRecord.setLedgerReceipt(ledgerReceipt);

        return LedgerGroupMapper.INSTANCE.ledgerReceiptPrintedRecordToLedgerReceiptPrintedRecordVM(
            ledgerReceiptPrintedRecordRepository.save(ledgerReceiptPrintedRecord)
        );
    }
}
