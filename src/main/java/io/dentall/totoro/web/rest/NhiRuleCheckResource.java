package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckService;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckVM;
import io.dentall.totoro.service.NhiExtendTreatmentProcedureService;
import io.dentall.totoro.service.PatientService;
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

    private final NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService;

    public NhiRuleCheckResource(
        NhiRuleCheckService nhiRuleCheckService,
        PatientService patientService,
        NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService
    ) {
        this.nhiRuleCheckService = nhiRuleCheckService;
        this.patientService = patientService;
        this.nhiExtendTreatmentProcedureService = nhiExtendTreatmentProcedureService;
    }

    @GetMapping("/validate/91003C")
    @Timed
    public ResponseEntity<Boolean> validate91003C(NhiRuleCheckVM vm) {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO().patient(patientService.findPatientById(vm.getPatientId()))
            .nhiExtendTreatmentProcedure(nhiExtendTreatmentProcedureService.findNhiExtendTreatmentProcedureById(vm.getTreatmentId()));
        return new ResponseEntity<>(nhiRuleCheckService.validate91003C(dto), HttpStatus.OK);
    }
}
