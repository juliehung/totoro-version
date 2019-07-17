package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.LedgerBusinessService;
import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing Ledger.
 */
@RestController
@RequestMapping("/api/business")
public class LedgerBusinessResource {

    private final Logger log = LoggerFactory.getLogger(LedgerBusinessResource.class);

    private static final String ENTITY_NAME = "ledger";

    private final LedgerBusinessService ledgerBusinessService;

    public LedgerBusinessResource(LedgerBusinessService ledgerBusinessService) {
        this.ledgerBusinessService = ledgerBusinessService;
    }

    @PostMapping("/ledgers")
    @Timed
    public ResponseEntity<Ledger> createLedger(@Valid @RequestBody Ledger ledger) {
        log.debug("REST request to save ledger");
        Ledger vm = ledgerBusinessService.saveLedgerRecord(ledger);
        return new ResponseEntity<>(vm, HttpStatus.CREATED);
    }

    @GetMapping("/ledgers")
    @Timed
    public ResponseEntity<List<Ledger>> getLedger(
        @RequestParam(name = "id", required = false) Long id,
        @RequestParam(name = "gid", required = false) Long gid,
        @RequestParam(name = "headOnly", required = false) boolean headOnly
    ) {
        log.debug("REST request to find ledgers by id");
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

        List<Ledger> vm = ledgerBusinessService.findRecords(id, gid, headOnly);
        return new ResponseEntity<>(vm, HttpStatus.OK);

    }

}
