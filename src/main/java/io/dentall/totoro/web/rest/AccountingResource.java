package io.dentall.totoro.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.repository.AccountingRepository;
import io.dentall.totoro.service.AccountingService;
import io.dentall.totoro.service.dto.AccountingDTO;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Accounting.
 */
@RestController
@RequestMapping("/api")
public class AccountingResource {

    private final Logger log = LoggerFactory.getLogger(AccountingResource.class);

    private static final String ENTITY_NAME = "accounting";

    private final AccountingRepository accountingRepository;

    private final AccountingService accountingService;

    public AccountingResource(AccountingRepository accountingRepository, AccountingService accountingService) {
        this.accountingRepository = accountingRepository;
        this.accountingService = accountingService;
    }

    /**
     * POST  /accountings : Create a new accounting.
     *
     * @param accounting the accounting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accounting, or with status 400 (Bad Request) if the accounting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accountings")
    @Timed
    public ResponseEntity<Accounting> createAccounting(@Valid @RequestBody Accounting accounting) throws URISyntaxException {
        log.debug("REST request to save Accounting : {}", accounting);
        if (accounting.getId() != null) {
            throw new BadRequestAlertException("A new accounting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Accounting result = accountingService.save(accounting);
        return ResponseEntity.created(new URI("/api/accountings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accountings : Updates an existing accounting.
     *
     * @param accounting the accounting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accounting,
     * or with status 400 (Bad Request) if the accounting is not valid,
     * or with status 500 (Internal Server Error) if the accounting couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accountings")
    @Timed
    public ResponseEntity<Accounting> updateAccounting(@Valid @RequestBody Accounting accounting) throws URISyntaxException {
        log.debug("REST request to update Accounting : {}", accounting);
        if (accounting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Accounting result = accountingService.update(accounting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accounting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /accountings : get all the accountings.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of accountings in body
     */
    @GetMapping("/accountings")
    @Timed
    public ResponseEntity<List<Accounting>> getAllAccountings(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("registration-is-null".equals(filter)) {
            log.debug("REST request to get all Accountings where registration is null");
            return new ResponseEntity<>(StreamSupport
                .stream(accountingRepository.findAll().spliterator(), false)
                .filter(accounting -> accounting.getRegistration() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Accountings");
        Page<Accounting> page = accountingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accountings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /accountings/:id : get the "id" accounting.
     *
     * @param id the id of the accounting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accounting, or with status 404 (Not Found)
     */
    @GetMapping("/accountings/{id}")
    @Timed
    public ResponseEntity<Accounting> getAccounting(@PathVariable Long id) {
        log.debug("REST request to get Accounting : {}", id);
        Optional<Accounting> accounting = accountingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accounting);
    }

    /**
     * DELETE  /accountings/:id : delete the "id" accounting.
     *
     * @param id the id of the accounting to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accountings/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccounting(@PathVariable Long id) {
        log.debug("REST request to delete Accounting : {}", id);

        accountingRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * 
     * GET /accountings/search
     * 
     * @param arrivalTimeBegin
     * @param arrivalTimeEnd
     * @param status
     * @return
     */
    @GetMapping("/accountings/search")
    @Timed
    public ResponseEntity<List<AccountingDTO>> getAllAccountingBySearchParam(
            @RequestParam(name = "registration.ArrivalTime.beginDate") Instant arrivalTimeBegin, 
            @RequestParam(name = "registration.ArrivalTime.endDate") Instant arrivalTimeEnd,
            @RequestParam(required = false, name = "registration.Status") RegistrationStatus status) {
        List<AccountingDTO> accountingList = accountingService.getAllAccountingsByAppointmentAndRegistration(arrivalTimeBegin, arrivalTimeEnd, status);
        return ResponseEntity.ok().body(accountingList);
    }
}
