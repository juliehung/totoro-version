package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.service.NhiExtendDisposalService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NhiExtendDisposal.
 */
@RestController
@RequestMapping("/api")
public class NhiExtendDisposalResource {

    private final Logger log = LoggerFactory.getLogger(NhiExtendDisposal.class);

    private static final String ENTITY_NAME = "nhiExtendDisposal";

    private final NhiExtendDisposalService nhiExtendDisposalService;

    public NhiExtendDisposalResource(NhiExtendDisposalService nhiExtendDisposalService) {
        this.nhiExtendDisposalService = nhiExtendDisposalService;
    }

    /**
     * POST  /nhi-extend-disposals : Create a new nhiExtendDisposal.
     *
     * @param nhiExtendDisposal the nhiExtendDisposal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiExtendDisposal, or with status 400 (Bad Request) if the nhiExtendDisposal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<NhiExtendDisposal> createNhiExtendDisposal(@RequestBody NhiExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to save NhiExtendDisposal : {}", nhiExtendDisposal);
        if (nhiExtendDisposal.getId() != null) {
            throw new BadRequestAlertException("A new nhiExtendDisposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiExtendDisposal result = nhiExtendDisposalService.save(nhiExtendDisposal);
        return ResponseEntity.created(new URI("/api/nhi-extend-disposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-extend-disposals : Updates an existing nhiExtendDisposal.
     *
     * @param nhiExtendDisposal the nhiExtendDisposal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiExtendDisposal,
     * or with status 400 (Bad Request) if the nhiExtendDisposal is not valid,
     * or with status 500 (Internal Server Error) if the nhiExtendDisposal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<NhiExtendDisposal> updateNhiExtendDisposal(@RequestBody NhiExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to update NhiExtendDisposal : {}", nhiExtendDisposal);
        if (nhiExtendDisposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiExtendDisposal result = nhiExtendDisposalService.update(nhiExtendDisposal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiExtendDisposal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-extend-disposals : get all the nhiExtendDisposals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiExtendDisposals in body
     */
    @GetMapping("/nhi-extend-disposals")
    @Timed
    public List<NhiExtendDisposal> getAllNhiExtendDisposals(@RequestParam(required = false) LocalDate date) {
        log.debug("REST request to get all NhiExtendDisposals");

        if (date == null) {
            return nhiExtendDisposalService.findAll();
        } else {
            return nhiExtendDisposalService.findByDate(date);
        }
    }

    /**
     * GET  /nhi-extend-disposals/:id : get the "id" nhiExtendDisposal.
     *
     * @param id the id of the nhiExtendDisposal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiExtendDisposal, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-extend-disposals/{id}")
    @Timed
    public ResponseEntity<NhiExtendDisposal> getNhiExtendDisposal(@PathVariable Long id) {
        log.debug("REST request to get NhiExtendDisposal : {}", id);
        Optional<NhiExtendDisposal> nhiExtendDisposal = nhiExtendDisposalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiExtendDisposal);
    }

    /**
     * DELETE  /nhi-extend-disposals/:id : delete the "id" nhiExtendDisposal.
     *
     * @param id the id of the nhiExtendDisposal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-extend-disposals/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiExtendDisposal(@PathVariable Long id) {
        log.debug("REST request to delete NhiExtendDisposal : {}", id);
        nhiExtendDisposalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}