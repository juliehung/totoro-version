package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.ConditionType;
import io.dentall.totoro.service.ConditionTypeService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.ConditionTypeCriteria;
import io.dentall.totoro.service.ConditionTypeQueryService;
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
 * REST controller for managing ConditionType.
 */
@RestController
@RequestMapping("/api")
public class ConditionTypeResource {

    private final Logger log = LoggerFactory.getLogger(ConditionTypeResource.class);

    private static final String ENTITY_NAME = "conditionType";

    private final ConditionTypeService conditionTypeService;

    private final ConditionTypeQueryService conditionTypeQueryService;

    public ConditionTypeResource(ConditionTypeService conditionTypeService, ConditionTypeQueryService conditionTypeQueryService) {
        this.conditionTypeService = conditionTypeService;
        this.conditionTypeQueryService = conditionTypeQueryService;
    }

    /**
     * POST  /condition-types : Create a new conditionType.
     *
     * @param conditionType the conditionType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conditionType, or with status 400 (Bad Request) if the conditionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/condition-types")
    @Timed
    public ResponseEntity<ConditionType> createConditionType(@Valid @RequestBody ConditionType conditionType) throws URISyntaxException {
        log.debug("REST request to save ConditionType : {}", conditionType);
        if (conditionType.getId() != null) {
            throw new BadRequestAlertException("A new conditionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConditionType result = conditionTypeService.save(conditionType);
        return ResponseEntity.created(new URI("/api/condition-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /condition-types : Updates an existing conditionType.
     *
     * @param conditionType the conditionType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conditionType,
     * or with status 400 (Bad Request) if the conditionType is not valid,
     * or with status 500 (Internal Server Error) if the conditionType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/condition-types")
    @Timed
    public ResponseEntity<ConditionType> updateConditionType(@Valid @RequestBody ConditionType conditionType) throws URISyntaxException {
        log.debug("REST request to update ConditionType : {}", conditionType);
        if (conditionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConditionType result = conditionTypeService.save(conditionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conditionType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /condition-types : get all the conditionTypes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of conditionTypes in body
     */
    @GetMapping("/condition-types")
    @Timed
    public ResponseEntity<List<ConditionType>> getAllConditionTypes(ConditionTypeCriteria criteria) {
        log.debug("REST request to get ConditionTypes by criteria: {}", criteria);
        List<ConditionType> entityList = conditionTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /condition-types/count : count all the conditionTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/condition-types/count")
    @Timed
    public ResponseEntity<Long> countConditionTypes(ConditionTypeCriteria criteria) {
        log.debug("REST request to count ConditionTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(conditionTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /condition-types/:id : get the "id" conditionType.
     *
     * @param id the id of the conditionType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conditionType, or with status 404 (Not Found)
     */
    @GetMapping("/condition-types/{id}")
    @Timed
    public ResponseEntity<ConditionType> getConditionType(@PathVariable Long id) {
        log.debug("REST request to get ConditionType : {}", id);
        Optional<ConditionType> conditionType = conditionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conditionType);
    }

    /**
     * DELETE  /condition-types/:id : delete the "id" conditionType.
     *
     * @param id the id of the conditionType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/condition-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteConditionType(@PathVariable Long id) {
        log.debug("REST request to delete ConditionType : {}", id);
        conditionTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
