package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.CareerOptions;
import io.dentall.totoro.repository.CareerOptionsRepository;
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
 * REST controller for managing CareerOptions.
 */
@RestController
@RequestMapping("/api")
public class CareerOptionsResource {

    private final Logger log = LoggerFactory.getLogger(CareerOptionsResource.class);

    private static final String ENTITY_NAME = "careerOptions";

    private final CareerOptionsRepository careerOptionsRepository;

    public CareerOptionsResource(CareerOptionsRepository careerOptionsRepository) {
        this.careerOptionsRepository = careerOptionsRepository;
    }

    /**
     * POST  /career-options : Create a new careerOptions.
     *
     * @param careerOptions the careerOptions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerOptions, or with status 400 (Bad Request) if the careerOptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-options")
    @Timed
    public ResponseEntity<CareerOptions> createCareerOptions(@RequestBody CareerOptions careerOptions) throws URISyntaxException {
        log.debug("REST request to save CareerOptions : {}", careerOptions);
        if (careerOptions.getId() != null) {
            throw new BadRequestAlertException("A new careerOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CareerOptions result = careerOptionsRepository.save(careerOptions);
        return ResponseEntity.created(new URI("/api/career-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-options : Updates an existing careerOptions.
     *
     * @param careerOptions the careerOptions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerOptions,
     * or with status 400 (Bad Request) if the careerOptions is not valid,
     * or with status 500 (Internal Server Error) if the careerOptions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-options")
    @Timed
    public ResponseEntity<CareerOptions> updateCareerOptions(@RequestBody CareerOptions careerOptions) throws URISyntaxException {
        log.debug("REST request to update CareerOptions : {}", careerOptions);
        if (careerOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CareerOptions result = careerOptionsRepository.save(careerOptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, careerOptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-options : get all the careerOptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careerOptions in body
     */
    @GetMapping("/career-options")
    @Timed
    public List<CareerOptions> getAllCareerOptions() {
        log.debug("REST request to get all CareerOptions");
        return careerOptionsRepository.findAll();
    }

    /**
     * GET  /career-options/:id : get the "id" careerOptions.
     *
     * @param id the id of the careerOptions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerOptions, or with status 404 (Not Found)
     */
    @GetMapping("/career-options/{id}")
    @Timed
    public ResponseEntity<CareerOptions> getCareerOptions(@PathVariable Long id) {
        log.debug("REST request to get CareerOptions : {}", id);
        Optional<CareerOptions> careerOptions = careerOptionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(careerOptions);
    }

    /**
     * DELETE  /career-options/:id : delete the "id" careerOptions.
     *
     * @param id the id of the careerOptions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-options/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerOptions(@PathVariable Long id) {
        log.debug("REST request to delete CareerOptions : {}", id);

        careerOptionsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
