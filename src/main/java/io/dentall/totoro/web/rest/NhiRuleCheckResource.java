package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckService;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckVM;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final NhiRuleCheckService nhiRuleCheckService;

    public NhiRuleCheckResource(
        NhiRuleCheckService nhiRuleCheckService
    ) {
        this.nhiRuleCheckService = nhiRuleCheckService;
    }

    @GetMapping("/validate/{code}")
    @Timed
    public ResponseEntity<Boolean> validateCode(@PathVariable String code, NhiRuleCheckVM vm) throws
        InvocationTargetException,
        IllegalAccessException
    {
        try {
            return new ResponseEntity<>(
                nhiRuleCheckService.dispatcher(code, vm),
                HttpStatus.OK);
        } catch (NoSuchMethodException e) {
            throw new BadRequestAlertException("Not support ".concat(code).concat(" validation yet."), "not.specify", "not.specify");
        }
    }

}
