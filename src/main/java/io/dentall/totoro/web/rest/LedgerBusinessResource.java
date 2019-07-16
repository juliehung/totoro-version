package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.LedgerBusinessService;
import io.dentall.totoro.domain.Ledger;
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
@RequestMapping("/api")
public class LedgerBusinessResource {

    private final Logger log = LoggerFactory.getLogger(LedgerBusinessResource.class);

    private final LedgerBusinessService ledgerBusinessService;

    public LedgerBusinessResource(LedgerBusinessService ledgerBusinessService) {
        this.ledgerBusinessService = ledgerBusinessService;
    }

    @PostMapping("/create/ledger")
    @Timed
    public ResponseEntity<Ledger> createLedger(@Valid @RequestBody Ledger ledger) {
        log.debug("REST request to save ledger");
        Ledger vm = ledgerBusinessService.saveLedgerRecord(ledger);
        return new ResponseEntity<>(vm, HttpStatus.CREATED);
    }

    @GetMapping("/get/headRecord")
    @Timed
    public ResponseEntity<List<Ledger>> getHeadRecord(@RequestParam("gid") Long gid) {
        log.debug("REST request to find ledger head by gid");
        List<Ledger> vm = ledgerBusinessService.findHeadRecord(gid);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

    @GetMapping("/get/records")
    @Timed
    public ResponseEntity<List<Ledger>> getRecords(@RequestParam("id") Long id) {
        log.debug("REST request to find ledger body records by gid");
        List<Ledger> vm = ledgerBusinessService.findRecords(id);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

}
