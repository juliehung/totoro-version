package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.PatientBusinessService;
import io.dentall.totoro.business.vm.PatientSearchVM;
import io.dentall.totoro.business.vm.PatientVM;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/prerogative/patient")
public class InternalPatientResource {

    private static Logger log = LoggerFactory.getLogger(InternalPatientResource.class);

    private final PatientBusinessService patientBusinessService;

    public InternalPatientResource(PatientBusinessService patientBusinessService) {
        this.patientBusinessService = patientBusinessService;
    }

    @GetMapping("/{id}")
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

    @GetMapping("/birth")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByBirth(
        @RequestParam String search,
        @ApiParam(allowableValues = "CE,ROC", required = true) @RequestParam String format,
        Pageable pageable
    ) {
        log.debug("REST request to get a page of Patients by birth[{}] and format[{}]", search, format);

        Page<PatientSearchVM> page = patientBusinessService.findByBirth(search, format, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prerogative/patient/birth");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/name")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByName(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by name[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByName(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prerogative/patient/name");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/phone")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByPhone(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by phone[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByPhone(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prerogative/patient/phone");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/national-id")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByNationalId(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by nationalId[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByNationalId(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prerogative/patient/national-id");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/medical-id")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByMedicalId(@RequestParam String search, Pageable pageable) {
        log.debug("REST request to get a page of Patients by medicalId[{}]", search);

        Page<PatientSearchVM> page = patientBusinessService.findByMedicalId(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prerogative/patient/medical-id");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/has-registration")
    @Timed
    public ResponseEntity<List<PatientSearchVM>> findByRegistration(@RequestParam(required = false) Instant begin, @RequestParam(required = false) Instant end) {
        log.debug("REST request to get a page of Patients, begin[{}], end[{}]", begin, end);

        List<PatientSearchVM> list = patientBusinessService.findByRegistration(begin, end);
        return ResponseEntity.ok().body(list);
    }
}
