package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.LedgerBusinessService;
import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.service.LedgerQueryService;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.dto.LedgerCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.LedgerVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Ledger.
 */
@RestController
@RequestMapping("/api/business")
public class LedgerBusinessResource {

    private final Logger log = LoggerFactory.getLogger(LedgerBusinessResource.class);

    private static final String ENTITY_NAME = "ledger";

    private final LedgerBusinessService ledgerBusinessService;

    private final LedgerQueryService ledgerQueryService;

    private final PatientService patientService;

    public LedgerBusinessResource(
        LedgerBusinessService ledgerBusinessService,
        LedgerQueryService ledgerQueryService,
        PatientService patientService
    ) {
        this.ledgerBusinessService = ledgerBusinessService;
        this.ledgerQueryService = ledgerQueryService;
        this.patientService = patientService;
    }

    @Deprecated
    @PostMapping("/ledgers")
    @Timed
    public ResponseEntity<Ledger> createLedger(@Valid @RequestBody Ledger ledger) {
        log.debug("REST request to save ledger");
        if (ledger.getId() != null) {
            throw new BadRequestAlertException("A new ledger cannot assign an ID ", ENTITY_NAME, " idexists");
        }

        Ledger vm = ledgerBusinessService.saveLedgerRecord(ledger);
        return new ResponseEntity<>(vm, HttpStatus.CREATED);
    }

    /**
     * 2021-03-17: 當前僅使用 patientId, date 兩項 query string
     */
    @GetMapping("/ledgers")
    @Timed
    public ResponseEntity<List<LedgerVM>> getLedger(
        LedgerCriteria criteria,
        @RequestParam(name = "headOnly", required = false) boolean headOnly,
        Pageable pageable
    ) {
        log.debug("REST request to find ledgers by id");

        if (criteria.getPatientId() != null ||
            criteria.getDate() != null ||
            criteria.getProjectCode() != null
        ) {
            Page<Ledger> page = ledgerQueryService.findByCriteria(criteria, pageable);
            List<LedgerVM> content = page.getContent().stream()
                .map(l -> {

                    LedgerVM vm = new LedgerVM();
                    vm.setLedger(l);

                    if (l != null &&
                        l.getPatientId() != null
                    ) {
                        vm.setPatient(patientService.findPatientById(l.getPatientId()));
                    }

                    return vm;
                })
                .collect(Collectors.toList());

            return ResponseEntity
                .ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(page, "/api/business/ledgers"))
                .body(page != null? content: null);
        }

        Long id = criteria.getId() == null? null: criteria.getId().getEquals();
        Long gid = criteria.getGid() == null? null: criteria.getGid().getEquals();

        if (headOnly &&
            (id == null || gid != null)
        ) {
            throw new BadRequestAlertException("Id must be fulfilled and gid must be empty for finding head", ENTITY_NAME, "paraconstraint");
        }

        if (!headOnly &&
            (id != null || gid == null)
        ) {
            throw new BadRequestAlertException("Gid must be fulfilled and id must be empty for finding group", ENTITY_NAME, "paraconstraint");
        }

        List<LedgerVM> vm = ledgerBusinessService.findRecords(id, gid, headOnly).stream()
            .map(LedgerVM::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(vm, HttpStatus.OK);

    }

}
