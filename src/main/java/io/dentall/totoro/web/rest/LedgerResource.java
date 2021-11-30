package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.config.TimeConfig;
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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
import java.util.HashMap;
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


    @PostMapping("/ledger-groups")
    @Timed
    public LedgerGroup createLedgerGroup(@RequestBody @Valid LedgerGroup ledgerGroup) {
        if (ledgerGroup.getId() != null) {
            throw new BadRequestAlertException("Can not include id for create ledger group", ENTITY_NAME, "limit");
        }

        if (!patientService.hasPatient(ledgerGroup.getPatientId())) {
            throw new BadRequestAlertException("Can not found patient by id", ENTITY_NAME, "notfound");
        }

        return ledgerGroupRepository.save(ledgerGroup);
    }

    @GetMapping("/ledger-groups")
    @Timed
    public List<LedgerGroup> getLedgerGroup(@RequestParam(value = "patientId") Long patientId) {
        return ledgerGroupRepository.findByPatientId(patientId);
    }

    @PatchMapping("/ledger-groups")
    @Timed
    @Transactional
    public LedgerGroup updateLedgerGroup(@RequestBody @Valid LedgerGroup updateLedgerGroup) {
        log.info("Patch ledger group by id {} ", updateLedgerGroup.getId());
        if (updateLedgerGroup.getId() == null) {
            throw new BadRequestAlertException("Require ledger group id", ENTITY_NAME, "noid");
        }

        if (updateLedgerGroup.getPatientId() != null) {
            throw new BadRequestAlertException("Unable to modify patient id of ledger group", ENTITY_NAME, "limit");
        }

        LedgerGroup ledgerGroup = ledgerGroupRepository.findById(updateLedgerGroup.getId())
            .orElseThrow(() -> new BadRequestAlertException("Can not found ledger group by id", ENTITY_NAME, "notfound"));

        LedgerGroupMapper.INSTANCE.patchLedgerGroup(ledgerGroup, updateLedgerGroup);

        return ledgerGroup;
    }

    @DeleteMapping("/ledger-groups/{id}")
    @Timed
    @Transactional
    public ResponseEntity<Void> removeLedgerGroup(@PathVariable Long id) {
        List<Ledger> ledgers = ledgerService.getLedgersByGid(id);
        for (Ledger ledger : ledgers) {
            ledgerService.delete(ledger.getId());
        }

        ledgerGroupRepository.deleteById(id);

        return ResponseEntity.ok(null);
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
                HashMap<Long, String> urls = new HashMap<>();
                d.getLedgerReceipts().forEach(lr -> {
                    if (lr.getFilePath() != null &&
                        lr.getFileName() != null
                    ) {
                        urls.put(
                            lr.getId(),
                            imageGcsBusinessService.getUrlForDownload()
                                .concat(lr.getFilePath())
                                .concat(lr.getFileName())
                        );
                    }
                });

                ledgerVM.getLedgerReceipts().forEach(vmlr -> {
                    if (urls.containsKey(vmlr.getId())) {
                        vmlr.setUrl(urls.get(vmlr.getId()));
                    }
                });

                Patient p = patientService.findPatientById(d.getLedgerGroup().getPatientId())
                    .orElse(new Patient());
                ledgerVM.setPatientId(p.getId());
                ledgerVM.setPatient(p);

                ledgerVM.setDoctorId(Long.valueOf(d.getDoctor()));

                return ledgerVM;
            })
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ledgers");
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @GetMapping("/ledger-receipts")
    @Timed
    public List<LedgerReceiptExcelVM> findLedgerReceiptsByGid(
        @RequestParam(required = false, name = "gid") Long gid,
        @RequestParam(required = false, name = "id") Long id
    ) {
        LedgerReceipt lr = new LedgerReceipt();
        LedgerGroup lg = new LedgerGroup();
        lg.setId(gid);
        lr.setId(id);
        lr.setLedgerGroup(lg);

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnorePaths("createdDate", "lastModifiedDate", "ledgerGroup.createdDate", "ledgerGroup.lastModifiedDate");

        List<LedgerReceiptExcelVM> vmList = LedgerGroupMapper.INSTANCE.ledgerReceiptListToLedgerReceiptExcelVMList(
            ledgerReceiptRepository.findAll(Example.of(lr, matcher))
        );

        for (LedgerReceiptExcelVM vm : vmList) {
            if (vm.getFilePath() != null &&
                vm.getFileName() != null
            ) {
                vm.setUrl(
                    imageGcsBusinessService.getUrlForDownload()
                        .concat(vm.getFilePath())
                        .concat(vm.getFileName())
                );
            }
        }

        return vmList;
    }

    /**
     * Only modify ledgerReceipt.signed from false to true.
     * @param ledgerReceiptVM
     * @return
     */
    @PatchMapping("/ledger-receipts")
    @Timed
    @Transactional
    public LedgerReceiptVM updateLedgerReceipt(@RequestBody @Valid LedgerReceiptVM ledgerReceiptVM) {
        log.info("Patch ledger receipt by id {} ", ledgerReceiptVM.getId());
        if (ledgerReceiptVM.getId() == null) {
            throw new BadRequestAlertException("Id is required", ENTITY_NAME, "required");
        }

        LedgerReceipt ledgerReceipt = ledgerReceiptRepository.findById(ledgerReceiptVM.getId())
            .orElseThrow(() -> new BadRequestAlertException("Can not found ledger receipt by id", ENTITY_NAME, "notfound"));

        ledgerReceipt.setSigned(true);

        return LedgerGroupMapper.INSTANCE.convertLedgerReceiptFromDomainToVM(ledgerReceipt);
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

        // Prevent object been modified by validate method
        final LedgerReceiptCreateVM ledgerReceiptCreateVMForValidate = ledgerReceiptCreateVM;
        ledgerService.validateLedgersInLedgerReceipt(ledgerReceiptCreateVMForValidate);

        LedgerReceipt ledgerReceipt = LedgerGroupMapper.INSTANCE.convertLedgerReceiptFromCreateVMToDomain(
            ledgerReceiptCreateVM
        );

        return LedgerGroupMapper.INSTANCE.convertLedgerReceiptFromDomainToVM(
            ledgerReceiptRepository.save(ledgerReceipt)
        );
    }

    @PostMapping("/ledger-receipts/{id}/pdf")
    @Timed
    @Transactional
    public LedgerReceiptVM uploadFileAndModifyLedgerReceipt(
        @PathVariable(name = "id") Long id,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (!file.getContentType().equals("application/pdf")) {
            throw new BadRequestAlertException("Only support file mime as application/pdf", ENTITY_NAME, "mimerequired");
        }

        LedgerReceipt ledgerReceipt = ledgerReceiptRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Can not found ledger receipt by id", ENTITY_NAME, "notfound"));

        if (ledgerReceipt.getFilePath() != null ||
            ledgerReceipt.getFileName() != null
        ) {
            throw new BadRequestAlertException("Can not post a file while ledger receipt already has one", ENTITY_NAME, "limit");
        }

        Patient patient = patientService.findPatientById(ledgerReceipt.getLedgerGroup().getPatientId())
            .orElseThrow(() -> new BadRequestAlertException("Can not found patient by id from ledgerReceipt.ledgerGroup.patientId", ENTITY_NAME, "notfound"));

        String filePath = imageGcsBusinessService.getClinicName()
            .concat("/")
            .concat(patient.getId().toString())
            .concat("/");
        String fileName = "收支紀錄"
            .concat("_")
            .concat(patient.getName())
            .concat(patient.getBirth().toString())
            .concat("_")
            .concat(ledgerReceipt.getLedgerGroup().getProjectCode())
            .concat(ledgerReceipt.getLedgerGroup().getDisplayName())
            .concat("_")
            .concat(Instant.now().atOffset(TimeConfig.ZONE_OFF_SET).toString())
            .concat(".pdf");

        imageGcsBusinessService.uploadFile(
            filePath,
            fileName,
            file.getBytes(),
            "application/pdf"
        );

        ledgerReceipt.setFilePath(filePath);
        ledgerReceipt.setFileName(fileName);

        LedgerReceiptVM vm = LedgerGroupMapper.INSTANCE.convertLedgerReceiptFromDomainToVM(ledgerReceipt);
        vm.setUrl(
            imageGcsBusinessService.getUrlForDownload()
                .concat(filePath)
                .concat(fileName)
        );

        return vm;
    }

    @PostMapping("/ledger-receipts/{id}/ledger-receipt-printed-records")
    @Timed
    @Transactional
    public LedgerReceiptPrintedRecordVM createLedgerReceiptRecord(
        @PathVariable(name = "id") Long id
    ) throws IOException {
        LedgerReceipt ledgerReceipt = ledgerReceiptRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Can not found ledger receipt by id", ENTITY_NAME, "notfound"));

        LedgerReceiptPrintedRecord ledgerReceiptPrintedRecord = new LedgerReceiptPrintedRecord();
        ledgerReceiptPrintedRecord.setTime(Instant.now());
        ledgerReceiptPrintedRecord.setLedgerReceipt(ledgerReceipt);

        return LedgerGroupMapper.INSTANCE.ledgerReceiptPrintedRecordToLedgerReceiptPrintedRecordVM(
            ledgerReceiptPrintedRecordRepository.save(ledgerReceiptPrintedRecord)
        );
    }
}
