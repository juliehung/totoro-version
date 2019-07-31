package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.dto.TeethGraphConfigDTO;
import io.dentall.totoro.business.service.PatientBusinessService;
import io.dentall.totoro.business.vm.TeethGraphConfigVM;
import io.dentall.totoro.domain.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
public class PatientBusinessResource {

    private final Logger log = LoggerFactory.getLogger(PatientBusinessResource.class);

    private PatientBusinessService patientBusinessService;

    public PatientBusinessResource(
        PatientBusinessService patientBusinessService
    ) {
        this.patientBusinessService = patientBusinessService;
    }

    @PutMapping("/patients/{id}/teeth-graph-configs/permanent-switches")
    @Timed
    public ResponseEntity<TeethGraphConfigVM> saveTeethGraphConfigs(
        @PathVariable Long id,
        @RequestBody TeethGraphConfigDTO teethGraphConfigDTO
    ) {
        log.debug("REST request to PUT patient({})'s teeth graph config, with TeethGraphConfigDTO " +
            "{}", id, teethGraphConfigDTO);

        // Validate patient
        Patient patient = patientBusinessService.validatePatientExistence(id);

        return new ResponseEntity<>(
            patientBusinessService.updateTeethGraphConfig(patient, teethGraphConfigDTO),
            HttpStatus.OK);
    }

    @GetMapping("/patients/{id}/teeth-graph-configs/permanent-switches")
    @Timed
    public ResponseEntity<TeethGraphConfigVM> getTeethGraphConfigs(
        @PathVariable Long id
    ) {
        log.debug("REST request to GET patient({})'s teeth graph config with permanent " +
            "switches", id);

        // Validate patient
        Patient patient = patientBusinessService.validatePatientExistence(id);

        return new ResponseEntity<>(
            patientBusinessService.getTeethGraphConfig(patient),
            HttpStatus.OK);
    }

}
