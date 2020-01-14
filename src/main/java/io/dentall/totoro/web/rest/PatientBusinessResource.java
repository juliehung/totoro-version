package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.dto.TeethGraphConfigDTO;
import io.dentall.totoro.business.service.PatientBusinessService;
import io.dentall.totoro.business.vm.PatientSearchVM;
import io.dentall.totoro.business.vm.PatientVM;
import io.dentall.totoro.business.vm.TeethGraphConfigVM;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/patients/{id}")
    @Timed
    public ResponseEntity<PatientVM> findPatientWithFirstVisitDate(
        @PathVariable Long id
    ) {
        PatientVM patientVM = patientBusinessService.findPatientWithFirstVisitDate(id);
        if (patientVM == null) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(patientVM);
    }

    @GetMapping("/patients/birth")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByBirth(@RequestParam String search, @RequestParam String format, Pageable pageable) {
        log.debug("REST request to get a page of Patients by birth[{}] and format[{}]", search, format);

        Page<PatientSearchVM> page = patientBusinessService.findByBirth(search, format, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business/patients/birth");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/patients/name")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByName(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by name[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByName(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business/patients/name");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/patients/phone")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByPhone(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by phone[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByPhone(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business/patients/phone");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/patients/national-id")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByNationalId(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by nationalId[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByNationalId(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business/patients/national-id");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/patients/medical-id")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByMedicalId(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by medicalId[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByMedicalId(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business/patients/medical-id");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
