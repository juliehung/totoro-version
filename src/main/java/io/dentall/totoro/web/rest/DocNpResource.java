package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.DocNp;
import io.dentall.totoro.repository.DocNpRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
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
 * REST controller for managing DocNp.
 */
@RestController
@RequestMapping("/api")
public class DocNpResource {

    private final Logger log = LoggerFactory.getLogger(DocNpResource.class);

    private static final String ENTITY_NAME = "docNp";

    private final DocNpRepository docNpRepository;

    public DocNpResource(DocNpRepository docNpRepository) {
        this.docNpRepository = docNpRepository;
    }

    /**
     * POST  /doc-nps : Create a new docNp.
     *
     * @param docNp the docNp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new docNp, or with status 400 (Bad Request) if the docNp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doc-nps")
    @Timed
    public ResponseEntity<DocNp> createDocNp(@RequestBody DocNp docNp) throws URISyntaxException {
        log.debug("REST request to save DocNp : {}", docNp);
        if (docNp.getId() != null) {
            throw new BadRequestAlertException("A new docNp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocNp result = docNpRepository.save(docNp);
        return ResponseEntity.created(new URI("/api/doc-nps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doc-nps : Updates an existing docNp.
     *
     * @param docNp the docNp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated docNp,
     * or with status 400 (Bad Request) if the docNp is not valid,
     * or with status 500 (Internal Server Error) if the docNp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doc-nps")
    @Timed
    public ResponseEntity<DocNp> updateDocNp(@RequestBody DocNp docNp) throws URISyntaxException {
        log.debug("REST request to update DocNp : {}", docNp);
        if (docNp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocNp result = docNpRepository.save(docNp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, docNp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doc-nps : get all the docNps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of docNps in body
     */
    @GetMapping("/doc-nps")
    @Timed
    public List<DocNp> getAllDocNps() {
        log.debug("REST request to get all DocNps");
        return docNpRepository.findAll();
    }

    /**
     * GET  /doc-nps/:id : get the "id" docNp.
     *
     * @param id the id of the docNp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the docNp, or with status 404 (Not Found)
     */
    @GetMapping("/doc-nps/{id}")
    @Timed
    public ResponseEntity<DocNp> getDocNp(@PathVariable Long id) {
        log.debug("REST request to get DocNp : {}", id);
        Optional<DocNp> docNp = docNpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(docNp);
    }

    /**
     * DELETE  /doc-nps/:id : delete the "id" docNp.
     *
     * @param id the id of the docNp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doc-nps/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocNp(@PathVariable Long id) {
        log.debug("REST request to delete DocNp : {}", id);

        docNpRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
