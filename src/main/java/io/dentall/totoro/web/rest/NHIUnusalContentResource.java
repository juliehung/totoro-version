package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NHIUnusalContent;
import io.dentall.totoro.repository.NHIUnusalContentRepository;
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
 * REST controller for managing NHIUnusalContent.
 */
@RestController
@RequestMapping("/api")
public class NHIUnusalContentResource {

    private final Logger log = LoggerFactory.getLogger(NHIUnusalContentResource.class);

    private static final String ENTITY_NAME = "nHIUnusalContent";

    private final NHIUnusalContentRepository nHIUnusalContentRepository;

    public NHIUnusalContentResource(NHIUnusalContentRepository nHIUnusalContentRepository) {
        this.nHIUnusalContentRepository = nHIUnusalContentRepository;
    }

    /**
     * POST  /nhi-unusal-contents : Create a new nHIUnusalContent.
     *
     * @param nHIUnusalContent the nHIUnusalContent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nHIUnusalContent, or with status 400 (Bad Request) if the nHIUnusalContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-unusal-contents")
    @Timed
    public ResponseEntity<NHIUnusalContent> createNHIUnusalContent(@Valid @RequestBody NHIUnusalContent nHIUnusalContent) throws URISyntaxException {
        log.debug("REST request to save NHIUnusalContent : {}", nHIUnusalContent);
        if (nHIUnusalContent.getId() != null) {
            throw new BadRequestAlertException("A new nHIUnusalContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHIUnusalContent result = nHIUnusalContentRepository.save(nHIUnusalContent);
        return ResponseEntity.created(new URI("/api/nhi-unusal-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-unusal-contents : Updates an existing nHIUnusalContent.
     *
     * @param nHIUnusalContent the nHIUnusalContent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nHIUnusalContent,
     * or with status 400 (Bad Request) if the nHIUnusalContent is not valid,
     * or with status 500 (Internal Server Error) if the nHIUnusalContent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-unusal-contents")
    @Timed
    public ResponseEntity<NHIUnusalContent> updateNHIUnusalContent(@Valid @RequestBody NHIUnusalContent nHIUnusalContent) throws URISyntaxException {
        log.debug("REST request to update NHIUnusalContent : {}", nHIUnusalContent);
        if (nHIUnusalContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHIUnusalContent result = nHIUnusalContentRepository.save(nHIUnusalContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nHIUnusalContent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-unusal-contents : get all the nHIUnusalContents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nHIUnusalContents in body
     */
    @GetMapping("/nhi-unusal-contents")
    @Timed
    public List<NHIUnusalContent> getAllNHIUnusalContents() {
        log.debug("REST request to get all NHIUnusalContents");
        return nHIUnusalContentRepository.findAll();
    }

    /**
     * GET  /nhi-unusal-contents/:id : get the "id" nHIUnusalContent.
     *
     * @param id the id of the nHIUnusalContent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nHIUnusalContent, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-unusal-contents/{id}")
    @Timed
    public ResponseEntity<NHIUnusalContent> getNHIUnusalContent(@PathVariable Long id) {
        log.debug("REST request to get NHIUnusalContent : {}", id);
        Optional<NHIUnusalContent> nHIUnusalContent = nHIUnusalContentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nHIUnusalContent);
    }

    /**
     * DELETE  /nhi-unusal-contents/:id : delete the "id" nHIUnusalContent.
     *
     * @param id the id of the nHIUnusalContent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-unusal-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteNHIUnusalContent(@PathVariable Long id) {
        log.debug("REST request to delete NHIUnusalContent : {}", id);

        nHIUnusalContentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
