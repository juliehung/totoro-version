package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiMonthDeclaration;
import io.dentall.totoro.service.NhiMonthDeclarationService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NhiMonthDeclaration.
 */
@RestController
@RequestMapping("/api")
public class NhiMonthDeclarationResource {

    private final Logger log = LoggerFactory.getLogger(NhiMonthDeclarationResource.class);

    private static final String ENTITY_NAME = "nhiMonthDeclaration";

    private final NhiMonthDeclarationService nhiMonthDeclarationService;

    public NhiMonthDeclarationResource(NhiMonthDeclarationService nhiMonthDeclarationService) {
        this.nhiMonthDeclarationService = nhiMonthDeclarationService;
    }

    /**
     * POST  /nhi-month-declarations : Create a new nhiMonthDeclaration.
     *
     * @param nhiMonthDeclaration the nhiMonthDeclaration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiMonthDeclaration, or with status 400 (Bad Request) if the nhiMonthDeclaration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-month-declarations")
    @Timed
    public ResponseEntity<NhiMonthDeclaration> createNhiMonthDeclaration(@Valid @RequestBody NhiMonthDeclaration nhiMonthDeclaration) throws URISyntaxException {
        log.debug("REST request to save NhiMonthDeclaration : {}", nhiMonthDeclaration);
        if (nhiMonthDeclaration.getId() != null) {
            throw new BadRequestAlertException("A new nhiMonthDeclaration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiMonthDeclaration result = nhiMonthDeclarationService.save(nhiMonthDeclaration);
        return ResponseEntity.created(new URI("/api/nhi-month-declarations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-month-declarations : Updates an existing nhiMonthDeclaration.
     *
     * @param nhiMonthDeclaration the nhiMonthDeclaration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiMonthDeclaration,
     * or with status 400 (Bad Request) if the nhiMonthDeclaration is not valid,
     * or with status 500 (Internal Server Error) if the nhiMonthDeclaration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-month-declarations")
    @Timed
    public ResponseEntity<NhiMonthDeclaration> updateNhiMonthDeclaration(@Valid @RequestBody NhiMonthDeclaration nhiMonthDeclaration) throws URISyntaxException {
        log.debug("REST request to update NhiMonthDeclaration : {}", nhiMonthDeclaration);
        if (nhiMonthDeclaration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiMonthDeclaration result = nhiMonthDeclarationService.save(nhiMonthDeclaration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiMonthDeclaration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-month-declarations : get all the nhiMonthDeclarations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiMonthDeclarations in body
     */
    @GetMapping("/nhi-month-declarations")
    @Timed
    public List<NhiMonthDeclaration> getAllNhiMonthDeclarations() {
        log.debug("REST request to get all NhiMonthDeclarations");
        return nhiMonthDeclarationService.findAll();
    }

    /**
     * GET  /nhi-month-declarations/:id : get the "id" nhiMonthDeclaration.
     *
     * @param id the id of the nhiMonthDeclaration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiMonthDeclaration, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-month-declarations/{id}")
    @Timed
    public ResponseEntity<NhiMonthDeclaration> getNhiMonthDeclaration(@PathVariable Long id) {
        log.debug("REST request to get NhiMonthDeclaration : {}", id);
        Optional<NhiMonthDeclaration> nhiMonthDeclaration = nhiMonthDeclarationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiMonthDeclaration);
    }

    /**
     * DELETE  /nhi-month-declarations/:id : delete the "id" nhiMonthDeclaration.
     *
     * @param id the id of the nhiMonthDeclaration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-month-declarations/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiMonthDeclaration(@PathVariable Long id) {
        log.debug("REST request to delete NhiMonthDeclaration : {}", id);
        nhiMonthDeclarationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
