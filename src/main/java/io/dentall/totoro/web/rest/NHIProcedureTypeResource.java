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

    private static final String ENTITY_NAME = "nHIProcedureType";

    private final NHIProcedureTypeRepository nHIProcedureTypeRepository;

    public NHIProcedureTypeResource(NHIProcedureTypeRepository nHIProcedureTypeRepository) {
        this.nHIProcedureTypeRepository = nHIProcedureTypeRepository;
    }

    /**
     * POST  /nhi-procedure-types : Create a new nHIProcedureType.
     *
     * @param nHIProcedureType the nHIProcedureType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nHIProcedureType, or with status 400 (Bad Request) if the nHIProcedureType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-procedure-types")
    @Timed
    public ResponseEntity<NHIProcedureType> createNHIProcedureType(@Valid @RequestBody NHIProcedureType nHIProcedureType) throws URISyntaxException {
        log.debug("REST request to save NHIProcedureType : {}", nHIProcedureType);
        if (nHIProcedureType.getId() != null) {
            throw new BadRequestAlertException("A new nHIProcedureType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHIProcedureType result = nHIProcedureTypeRepository.save(nHIProcedureType);
        return ResponseEntity.created(new URI("/api/nhi-procedure-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-procedure-types : Updates an existing nHIProcedureType.
     *
     * @param nHIProcedureType the nHIProcedureType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nHIProcedureType,
     * or with status 400 (Bad Request) if the nHIProcedureType is not valid,
     * or with status 500 (Internal Server Error) if the nHIProcedureType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-procedure-types")
    @Timed
    public ResponseEntity<NHIProcedureType> updateNHIProcedureType(@Valid @RequestBody NHIProcedureType nHIProcedureType) throws URISyntaxException {
        log.debug("REST request to update NHIProcedureType : {}", nHIProcedureType);
        if (nHIProcedureType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHIProcedureType result = nHIProcedureTypeRepository.save(nHIProcedureType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nHIProcedureType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-procedure-types : get all the nHIProcedureTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nHIProcedureTypes in body
     */
    @GetMapping("/nhi-procedure-types")
    @Timed
    public List<NHIProcedureType> getAllNHIProcedureTypes() {
        log.debug("REST request to get all NHIProcedureTypes");
        return nHIProcedureTypeRepository.findAll();
    }

    /**
     * GET  /nhi-procedure-types/:id : get the "id" nHIProcedureType.
     *
     * @param id the id of the nHIProcedureType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nHIProcedureType, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-procedure-types/{id}")
    @Timed
    public ResponseEntity<NHIProcedureType> getNHIProcedureType(@PathVariable Long id) {
        log.debug("REST request to get NHIProcedureType : {}", id);
        Optional<NHIProcedureType> nHIProcedureType = nHIProcedureTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nHIProcedureType);
    }

    /**
     * DELETE  /nhi-procedure-types/:id : delete the "id" nHIProcedureType.
     *
     * @param id the id of the nHIProcedureType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-procedure-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteNHIProcedureType(@PathVariable Long id) {
        log.debug("REST request to delete NHIProcedureType : {}", id);

        nHIProcedureTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
