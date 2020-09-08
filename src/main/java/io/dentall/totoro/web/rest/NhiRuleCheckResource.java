package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckService;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NhiRuleCheckResource {

    private final NhiRuleCheckService nhiRuleCheckService;

    public NhiRuleCheckResource(
        NhiRuleCheckService nhiRuleCheckService
    ) {
        this.nhiRuleCheckService = nhiRuleCheckService;
    }

    @GetMapping("/validate/91003C")
    @Timed
    public ResponseEntity<Boolean> validate91003C(NhiRuleCheckVM vm) {
        return new ResponseEntity<>(
            nhiRuleCheckService.validate91003C(
                nhiRuleCheckService.convertVmToDto(vm)),
            HttpStatus.OK);

    }
}
