package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckService;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckVM;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.dto.table.PatientTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NhiRuleCheckResource {

    private final NhiRuleCheckService nhiRuleCheckService;

    private final PatientService patientService;

    public NhiRuleCheckResource(NhiRuleCheckService nhiRuleCheckService,
        PatientService patientService
    ) {
        this.nhiRuleCheckService = nhiRuleCheckService;
        this.patientService = patientService;
    }

    @GetMapping("/validate/91003C")
    @Timed
    public ResponseEntity<Boolean> validate91003C(NhiRuleCheckVM vm) {
        PatientTable pt = patientService.findPatientById(vm.getPatientId());
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        dto.setPatient(pt);
        return new ResponseEntity<>(nhiRuleCheckService.validate91003C(dto), HttpStatus.OK);
    }
}
