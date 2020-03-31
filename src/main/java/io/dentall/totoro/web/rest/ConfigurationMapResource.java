package io.dentall.totoro.web.rest;

import io.dentall.totoro.domain.ConfigurationMap;
import io.dentall.totoro.service.ConfigurationMapQueryService;
import io.dentall.totoro.service.ConfigurationMapService;
import io.dentall.totoro.service.dto.ConfigurationMapCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.vm.ConfigurationVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.dentall.totoro.domain.ConfigurationMap}.
 */
@RestController
@RequestMapping("/api")
public class ConfigurationMapResource {

    private final Logger log = LoggerFactory.getLogger(ConfigurationMapResource.class);

    private static final String ENTITY_NAME = "configurationMap";

    private final ConfigurationMapService configurationMapService;

    private final ConfigurationMapQueryService configurationMapQueryService;

    public ConfigurationMapResource(ConfigurationMapService configurationMapService, ConfigurationMapQueryService configurationMapQueryService) {
        this.configurationMapService = configurationMapService;
        this.configurationMapQueryService = configurationMapQueryService;
    }

    @PostMapping("/configuration-maps/multiple")
    public ResponseEntity<ConfigurationVM> createMultipleConfigurationMap(@RequestBody ConfigurationVM configurationVM) throws URISyntaxException {
        log.debug("REST request to save multiple ConfigurationMap : {}", configurationVM);
        ConfigurationVM result = configurationMapService.saveAll(configurationVM);
        return ResponseEntity.created(new URI("/api/configuration-maps/multiple"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "all"))
            .body(result);
    }

    @PutMapping("/configuration-maps/multiple")
    public ResponseEntity<ConfigurationVM> updateConfigurationMap(@RequestBody ConfigurationVM configurationVM) {
        log.debug("REST request to update multiple ConfigurationMap : {}", configurationVM);

        ConfigurationVM result = configurationMapService.updateAll(configurationVM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "all"))
            .body(result);
    }

    @DeleteMapping("/configuration-maps/multiple")
    public ResponseEntity<Void> deleteConfigurationMap(@RequestBody ConfigurationVM configurationVM) {
        log.debug("REST request to delete multiple ConfigurationMap : {}", configurationVM);

        configurationMapService.deleteAll(configurationVM);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "all")).build();
    }

    /**
     * {@code POST  /configuration-maps} : Create a new configurationMap.
     *
     * @param configurationMap the configurationMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configurationMap, or with status {@code 400 (Bad Request)} if the configurationMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/configuration-maps")
    public ResponseEntity<ConfigurationMap> createConfigurationMap(@RequestBody ConfigurationMap configurationMap) throws URISyntaxException {
        log.debug("REST request to save ConfigurationMap : {}", configurationMap);
        if (configurationMap.getId() != null) {
            throw new BadRequestAlertException("A new configurationMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigurationMap result = configurationMapService.save(configurationMap);
        return ResponseEntity.created(new URI("/api/configuration-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /configuration-maps} : Updates an existing configurationMap.
     *
     * @param configurationMap the configurationMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configurationMap,
     * or with status {@code 400 (Bad Request)} if the configurationMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configurationMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/configuration-maps")
    public ResponseEntity<ConfigurationMap> updateConfigurationMap(@RequestBody ConfigurationMap configurationMap) throws URISyntaxException {
        log.debug("REST request to update ConfigurationMap : {}", configurationMap);
        if (configurationMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfigurationMap result = configurationMapService.save(configurationMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configurationMap.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /configuration-maps} : get all the configurationMaps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configurationMaps in body.
     */
    @GetMapping("/configuration-maps")
    public ResponseEntity<List<ConfigurationMap>> getAllConfigurationMaps(ConfigurationMapCriteria criteria) {
        log.debug("REST request to get ConfigurationMaps by criteria: {}", criteria);
        List<ConfigurationMap> entityList = configurationMapQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /configuration-maps/count} : count all the configurationMaps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/configuration-maps/count")
    public ResponseEntity<Long> countConfigurationMaps(ConfigurationMapCriteria criteria) {
        log.debug("REST request to count ConfigurationMaps by criteria: {}", criteria);
        return ResponseEntity.ok().body(configurationMapQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /configuration-maps/:id} : get the "id" configurationMap.
     *
     * @param id the id of the configurationMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configurationMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/configuration-maps/{id}")
    public ResponseEntity<ConfigurationMap> getConfigurationMap(@PathVariable Long id) {
        log.debug("REST request to get ConfigurationMap : {}", id);
        Optional<ConfigurationMap> configurationMap = configurationMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configurationMap);
    }

    /**
     * {@code DELETE  /configuration-maps/:id} : delete the "id" configurationMap.
     *
     * @param id the id of the configurationMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/configuration-maps/{id}")
    public ResponseEntity<Void> deleteConfigurationMap(@PathVariable Long id) {
        log.debug("REST request to delete ConfigurationMap : {}", id);
        configurationMapService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
