package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NHIExtendDisposal;
import io.dentall.totoro.service.NHIExtendDisposalService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
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

/**
 * REST controller for managing NHIExtendDisposal.
 */
@RestController
@RequestMapping("/api")
public class NHIExtendDisposalResource {

    private final Logger log = LoggerFactory.getLogger(NHIExtendDisposalResource.class);

    private static final String ENTITY_NAME = "nhiExtendDisposal";

    private final NHIExtendDisposalService nhiExtendDisposalService;

    public NHIExtendDisposalResource(NHIExtendDisposalService nhiExtendDisposalService) {
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
    public ResponseEntity<NHIExtendDisposal> createNHIExtendDisposal(@Valid @RequestBody NHIExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to save NHIExtendDisposal : {}", nhiExtendDisposal);
        if (nhiExtendDisposal.getId() != null) {
            throw new BadRequestAlertException("A new nhiExtendDisposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHIExtendDisposal result = nhiExtendDisposalService.save(nhiExtendDisposal);
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
    public ResponseEntity<NHIExtendDisposal> updateNHIExtendDisposal(@Valid @RequestBody NHIExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to update NHIExtendDisposal : {}", nhiExtendDisposal);
        if (nhiExtendDisposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHIExtendDisposal result = nhiExtendDisposalService.update(nhiExtendDisposal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiExtendDisposal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-extend-disposals : get all the nhiExtendDisposals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhiExtendDisposals in body
     */
    @GetMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<List<NHIExtendDisposal>> getAllNHIExtendDisposals(Pageable pageable) {
        log.debug("REST request to get a page of NHIExtendDisposals");
        Page<NHIExtendDisposal> page = nhiExtendDisposalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhi-extend-disposals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhi-extend-disposals/:id : get the "id" nhiExtendDisposal.
     *
     * @param id the id of the nhiExtendDisposal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiExtendDisposal, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-extend-disposals/{id}")
    @Timed
    public ResponseEntity<NHIExtendDisposal> getNHIExtendDisposal(@PathVariable Long id) {
        log.debug("REST request to get NHIExtendDisposal : {}", id);
        Optional<NHIExtendDisposal> nhiExtendDisposal = nhiExtendDisposalService.findOne(id);
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
    public ResponseEntity<Void> deleteNHIExtendDisposal(@PathVariable Long id) {
        log.debug("REST request to delete NHIExtendDisposal : {}", id);

        nhiExtendDisposalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
