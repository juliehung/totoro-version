package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.FindingType;
import io.dentall.totoro.service.FindingTypeService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.FindingTypeCriteria;
import io.dentall.totoro.service.FindingTypeQueryService;
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
 * REST controller for managing FindingType.
 */
@RestController
@RequestMapping("/api")
public class FindingTypeResource {

    private final Logger log = LoggerFactory.getLogger(FindingTypeResource.class);

    private static final String ENTITY_NAME = "findingType";

    private final FindingTypeService findingTypeService;

    private final FindingTypeQueryService findingTypeQueryService;

    public FindingTypeResource(FindingTypeService findingTypeService, FindingTypeQueryService findingTypeQueryService) {
        this.findingTypeService = findingTypeService;
        this.findingTypeQueryService = findingTypeQueryService;
    }

    /**
     * POST  /finding-types : Create a new findingType.
     *
     * @param findingType the findingType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new findingType, or with status 400 (Bad Request) if the findingType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/finding-types")
    @Timed
    public ResponseEntity<FindingType> createFindingType(@Valid @RequestBody FindingType findingType) throws URISyntaxException {
        log.debug("REST request to save FindingType : {}", findingType);
        if (findingType.getId() != null) {
            throw new BadRequestAlertException("A new findingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FindingType result = findingTypeService.save(findingType);
        return ResponseEntity.created(new URI("/api/finding-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /finding-types : Updates an existing findingType.
     *
     * @param findingType the findingType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated findingType,
     * or with status 400 (Bad Request) if the findingType is not valid,
     * or with status 500 (Internal Server Error) if the findingType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/finding-types")
    @Timed
    public ResponseEntity<FindingType> updateFindingType(@Valid @RequestBody FindingType findingType) throws URISyntaxException {
        log.debug("REST request to update FindingType : {}", findingType);
        if (findingType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FindingType result = findingTypeService.save(findingType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, findingType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /finding-types : get all the findingTypes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of findingTypes in body
     */
    @GetMapping("/finding-types")
    @Timed
    public ResponseEntity<List<FindingType>> getAllFindingTypes(FindingTypeCriteria criteria) {
        log.debug("REST request to get FindingTypes by criteria: {}", criteria);
        List<FindingType> entityList = findingTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /finding-types/count : count all the findingTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/finding-types/count")
    @Timed
    public ResponseEntity<Long> countFindingTypes(FindingTypeCriteria criteria) {
        log.debug("REST request to count FindingTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(findingTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /finding-types/:id : get the "id" findingType.
     *
     * @param id the id of the findingType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the findingType, or with status 404 (Not Found)
     */
    @GetMapping("/finding-types/{id}")
    @Timed
    public ResponseEntity<FindingType> getFindingType(@PathVariable Long id) {
        log.debug("REST request to get FindingType : {}", id);
        Optional<FindingType> findingType = findingTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(findingType);
    }

    /**
     * DELETE  /finding-types/:id : delete the "id" findingType.
     *
     * @param id the id of the findingType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/finding-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteFindingType(@PathVariable Long id) {
        log.debug("REST request to delete FindingType : {}", id);
        findingTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
