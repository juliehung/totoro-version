package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.LedgerBusinessService;
import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.service.LedgerQueryService;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.dto.LedgerCriteria;
import io.dentall.totoro.service.mapper.LedgerGroupMapper;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupVM;
import io.dentall.totoro.web.rest.vm.LedgerVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Ledger.
 */
@Deprecated
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
    @Transactional
    public ResponseEntity<List<LedgerVM>> getLedger(
        LedgerCriteria criteria,
        @RequestParam(name = "headOnly", required = false) boolean headOnly,
        Pageable pageable
    ) {
        log.debug("REST request to find ledgers by id");

        if (criteria.getPatientId() == null &&
            criteria.getDate() == null &&
            criteria.getProjectCode() == null
        ) {
            throw new BadRequestAlertException("Require patient id, date , project code to query", ENTITY_NAME, "badquery");
        }

        Page<Ledger> page = ledgerQueryService.findByCriteria(criteria, pageable);
        List<LedgerVM> result = page.getContent().stream()
            .map(d -> {
                LedgerVM ledgerVM = LedgerGroupMapper.INSTANCE.convertLedgerFromDomainToVM(d);

                Patient p = patientService.findPatientById(d.getLedgerGroup().getPatientId())
                    .orElse(new Patient());
                ledgerVM.setPatient(p);
                ledgerVM.setPatientId(p.getId());

                return ledgerVM;
            })
            .collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .headers(PaginationUtil.generatePaginationHttpHeaders(page, "/api/business/ledgers"))
            .body(result);
    }

}
