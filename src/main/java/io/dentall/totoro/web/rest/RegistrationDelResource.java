package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.RegistrationDel;
import io.dentall.totoro.service.RegistrationDelService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.RegistrationDelCriteria;
import io.dentall.totoro.service.RegistrationDelQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RegistrationDel.
 */
@RestController
@RequestMapping("/api")
public class RegistrationDelResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationDelResource.class);

    private static final String ENTITY_NAME = "registrationDel";

    private final RegistrationDelService registrationDelService;

    private final RegistrationDelQueryService registrationDelQueryService;

    public RegistrationDelResource(RegistrationDelService registrationDelService, RegistrationDelQueryService registrationDelQueryService) {
        this.registrationDelService = registrationDelService;
        this.registrationDelQueryService = registrationDelQueryService;
    }

    /**
     * POST  /registration-dels : Create a new registrationDel.
     *
     * @param registrationDel the registrationDel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registrationDel, or with status 400 (Bad Request) if the registrationDel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/registration-dels")
    @Timed
    public ResponseEntity<RegistrationDel> createRegistrationDel(@Valid @RequestBody RegistrationDel registrationDel) throws URISyntaxException {
        log.debug("REST request to save RegistrationDel : {}", registrationDel);
        if (registrationDel.getId() != null) {
            throw new BadRequestAlertException("A new registrationDel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistrationDel result = registrationDelService.save(registrationDel);
        return ResponseEntity.created(new URI("/api/registration-dels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registration-dels : Updates an existing registrationDel.
     *
     * @param registrationDel the registrationDel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registrationDel,
     * or with status 400 (Bad Request) if the registrationDel is not valid,
     * or with status 500 (Internal Server Error) if the registrationDel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/registration-dels")
    @Timed
    public ResponseEntity<RegistrationDel> updateRegistrationDel(@Valid @RequestBody RegistrationDel registrationDel) throws URISyntaxException {
        log.debug("REST request to update RegistrationDel : {}", registrationDel);
        if (registrationDel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegistrationDel result = registrationDelService.save(registrationDel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, registrationDel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registration-dels : get all the registrationDels.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of registrationDels in body
     */
    @GetMapping("/registration-dels")
    @Timed
    public ResponseEntity<List<RegistrationDel>> getAllRegistrationDels(RegistrationDelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RegistrationDels by criteria: {}", criteria);
        Page<RegistrationDel> page = registrationDelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/registration-dels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /registration-dels/count : count all the registrationDels.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/registration-dels/count")
    @Timed
    public ResponseEntity<Long> countRegistrationDels(RegistrationDelCriteria criteria) {
        log.debug("REST request to count RegistrationDels by criteria: {}", criteria);
        return ResponseEntity.ok().body(registrationDelQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /registration-dels/:id : get the "id" registrationDel.
     *
     * @param id the id of the registrationDel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registrationDel, or with status 404 (Not Found)
     */
    @GetMapping("/registration-dels/{id}")
    @Timed
    public ResponseEntity<RegistrationDel> getRegistrationDel(@PathVariable Long id) {
        log.debug("REST request to get RegistrationDel : {}", id);
        Optional<RegistrationDel> registrationDel = registrationDelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registrationDel);
    }

    /**
     * DELETE  /registration-dels/:id : delete the "id" registrationDel.
     *
     * @param id the id of the registrationDel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/registration-dels/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegistrationDel(@PathVariable Long id) {
        log.debug("REST request to delete RegistrationDel : {}", id);
        registrationDelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
