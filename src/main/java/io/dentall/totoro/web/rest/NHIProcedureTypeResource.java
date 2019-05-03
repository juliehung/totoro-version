package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NHIProcedureType;
import io.dentall.totoro.repository.NHIProcedureTypeRepository;
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
 * REST controller for managing NHIProcedureType.
 */
@RestController
@RequestMapping("/api")
public class NHIProcedureTypeResource {

    private final Logger log = LoggerFactory.getLogger(NHIProcedureTypeResource.class);

    private static final String ENTITY_NAME = "nhiProcedureType";

    private final NHIProcedureTypeRepository nhiProcedureTypeRepository;

    public NHIProcedureTypeResource(NHIProcedureTypeRepository nhiProcedureTypeRepository) {
        this.nhiProcedureTypeRepository = nhiProcedureTypeRepository;
    }

    /**
     * POST  /nhi-procedure-types : Create a new nhiProcedureType.
     *
     * @param nhiProcedureType the nhiProcedureType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiProcedureType, or with status 400 (Bad Request) if the nhiProcedureType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-procedure-types")
    @Timed
    public ResponseEntity<NHIProcedureType> createNHIProcedureType(@Valid @RequestBody NHIProcedureType nhiProcedureType) throws URISyntaxException {
        log.debug("REST request to save NHIProcedureType : {}", nhiProcedureType);
        if (nhiProcedureType.getId() != null) {
            throw new BadRequestAlertException("A new nhiProcedureType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHIProcedureType result = nhiProcedureTypeRepository.save(nhiProcedureType);
        return ResponseEntity.created(new URI("/api/nhi-procedure-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-procedure-types : Updates an existing nhiProcedureType.
     *
     * @param nhiProcedureType the nhiProcedureType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiProcedureType,
     * or with status 400 (Bad Request) if the nhiProcedureType is not valid,
     * or with status 500 (Internal Server Error) if the nhiProcedureType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-procedure-types")
    @Timed
    public ResponseEntity<NHIProcedureType> updateNHIProcedureType(@Valid @RequestBody NHIProcedureType nhiProcedureType) throws URISyntaxException {
        log.debug("REST request to update NHIProcedureType : {}", nhiProcedureType);
        if (nhiProcedureType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHIProcedureType result = nhiProcedureTypeRepository.save(nhiProcedureType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiProcedureType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-procedure-types : get all the nhiProcedureTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiProcedureTypes in body
     */
    @GetMapping("/nhi-procedure-types")
    @Timed
    public List<NHIProcedureType> getAllNHIProcedureTypes() {
        log.debug("REST request to get all NHIProcedureTypes");
        return nhiProcedureTypeRepository.findAll();
    }

    /**
     * GET  /nhi-procedure-types/:id : get the "id" nhiProcedureType.
     *
     * @param id the id of the nhiProcedureType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiProcedureType, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-procedure-types/{id}")
    @Timed
    public ResponseEntity<NHIProcedureType> getNHIProcedureType(@PathVariable Long id) {
        log.debug("REST request to get NHIProcedureType : {}", id);
        Optional<NHIProcedureType> nhiProcedureType = nhiProcedureTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nhiProcedureType);
    }

    /**
     * DELETE  /nhi-procedure-types/:id : delete the "id" nhiProcedureType.
     *
     * @param id the id of the nhiProcedureType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-procedure-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteNHIProcedureType(@PathVariable Long id) {
        log.debug("REST request to delete NHIProcedureType : {}", id);

        nhiProcedureTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
