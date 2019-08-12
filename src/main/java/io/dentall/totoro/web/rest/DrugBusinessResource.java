package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.DrugBusinessService;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Drug.
 */
@RestController
@RequestMapping("/api/business")
public class DrugBusinessResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);

    private static final String ENTITY_NAME = "drug";

    private final DrugBusinessService drugBusinessService;

    public DrugBusinessResource(
        DrugBusinessService drugBusinessService
    ) {
        this.drugBusinessService = drugBusinessService;
    }

    /**
     * DELETE  /drugs/:id : delete the "id" drug.
     *
     * @param id the id of the drug to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugBusinessService.delete(id);
        return ResponseEntity.ok().headers(
            HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
