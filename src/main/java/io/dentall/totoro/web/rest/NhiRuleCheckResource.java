package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckService;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class NhiRuleCheckResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final NhiRuleCheckService<NhiRuleCheckVM, NhiRuleCheckDTO, NhiRuleCheckResultVM> nhiRuleCheckService;

    public NhiRuleCheckResource(
        NhiRuleCheckService nhiRuleCheckService
    ) {
        this.nhiRuleCheckService = nhiRuleCheckService;
    }

    // 即便 vm validation 為 false ，仍有需要顯示的 message
    @GetMapping("/validation/{code}")
    @Timed
    public ResponseEntity<NhiRuleCheckResultVM> validateCode(@PathVariable String code, NhiRuleCheckVM vm) throws
        InvocationTargetException,
        IllegalAccessException {
        try {
            return new ResponseEntity<>(
                nhiRuleCheckService.dispatch(code, vm),
                HttpStatus.OK);
        } catch (NoSuchMethodException e) {
            return new ResponseEntity<>(
                new NhiRuleCheckResultVM()
                    .validated(true)
                    .messages(Arrays.asList("Not supported code")),
                HttpStatus.OK);
        }
    }

}
