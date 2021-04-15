package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckService;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckScriptType;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class NhiRuleCheckResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final NhiRuleCheckService<NhiRuleCheckBody, NhiRuleCheckDTO, NhiRuleCheckResultVM> nhiRuleCheckService;

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    private final ApplicationContext applicationContext;

    public NhiRuleCheckResource(
        NhiRuleCheckService nhiRuleCheckService,
        NhiRuleCheckUtil nhiRuleCheckUtil,
        ApplicationContext applicationContext
    ) {
        this.nhiRuleCheckService = nhiRuleCheckService;
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
        this.applicationContext = applicationContext;
    }

    // 即便 vm validation 為 false ，仍有需要顯示的 message
    @PostMapping("/validation/{code}")
    @Timed
    public ResponseEntity<NhiRuleCheckResultVM> validateCode(
        @PathVariable String code,
        @RequestBody NhiRuleCheckBody body
    ) throws
        InvocationTargetException,
        IllegalAccessException
    {
        try {
            // 轉換至統一入口 Object
            NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(code, body);

            // 分派到號碼群的腳本當中
            NhiRuleCheckScriptType scriptType =
                Arrays.stream(NhiRuleCheckScriptType.values())
                    .filter(e -> e.getRegex().matcher(code).matches())
                    .findFirst()
                    .orElseThrow(NoSuchFieldException::new);

            Object scriptBean = applicationContext.getBean(scriptType.getScriptClass());

            // 並在該腳本中找到對應函式
            NhiRuleCheckResultVM rvm = (NhiRuleCheckResultVM) scriptBean
                .getClass()
                .getMethod("validate".concat(code), NhiRuleCheckDTO.class)
                .invoke(scriptBean, dto);

            // 若代碼檢核無異常，則根據不同情境回傳訊息
            if (rvm.isValidated()) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.appendSuccessSourceInfo(dto),
                    rvm
                );
            }

            return new ResponseEntity<>(
                nhiRuleCheckService.dispatch(code, body),
                HttpStatus.OK
            );
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            logger.error("NhiRuleCheckResource requested a code not supported, {}.", code);
            return new ResponseEntity<>(
                new NhiRuleCheckResultVM()
                    .validated(true),
                HttpStatus.OK
            );
        }
    }

}
