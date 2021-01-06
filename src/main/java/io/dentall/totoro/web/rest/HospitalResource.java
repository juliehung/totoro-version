package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Hospital;
import io.dentall.totoro.repository.HospitalRepository;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing Hospital.
 */
@RestController
@RequestMapping("/api")
public class HospitalResource {

    private final Logger log = LoggerFactory.getLogger(HospitalResource.class);

    private static final String ENTITY_NAME = "hospital";

    private final HospitalRepository hospitalRepository;

    public HospitalResource(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * GET  /hospitals : get all the hospitals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hospitals in body
     */
    @GetMapping("/hospitals")
    @Timed
    public ResponseEntity<Hospital> getAllHospitals(@RequestParam("hospitalId")String hospitalId) {
        log.debug("REST request to get a page of Hospitals");
        Optional<Hospital> h = hospitalRepository.findByHospitalId(hospitalId);
        return ResponseUtil.wrapOrNotFound(h);
    }

}
