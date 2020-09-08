package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckService;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api")
public class NhiRuleCheckResource {

    private final NhiRuleCheckService nhiRuleCheckService;

    public NhiRuleCheckResource(
        NhiRuleCheckService nhiRuleCheckService
    ) {
        this.nhiRuleCheckService = nhiRuleCheckService;
    }

    @GetMapping("/validate/{code}")
    @Timed
    public ResponseEntity<Boolean> validateCode(@PathVariable String code, NhiRuleCheckVM vm) throws
        NoSuchMethodException,
        IllegalAccessException,
        InvocationTargetException
    {
        return new ResponseEntity<>(
            nhiRuleCheckService.dispatcher(code, vm),
            HttpStatus.OK);

    }

}
