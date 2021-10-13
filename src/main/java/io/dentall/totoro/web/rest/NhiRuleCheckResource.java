package io.dentall.totoro.web.rest;


import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckMapper;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.NhiMonthDeclarationRuleCheckReportVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckReportBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.domain.NhiMonthDeclarationRuleCheckReport;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NhiRuleCheckResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    private final ApplicationContext applicationContext;

    public NhiRuleCheckResource(
        NhiRuleCheckUtil nhiRuleCheckUtil,
        ApplicationContext applicationContext
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
        this.applicationContext = applicationContext;
    }

    @PostMapping("/validation")
    @Timed
    public ResponseEntity<NhiRuleCheckResultVM> validate(
        @RequestBody NhiRuleCheckBody body
    ) throws
        InvocationTargetException,
        IllegalAccessException
    {
        NhiRuleCheckResultVM result = new NhiRuleCheckResultVM();
        for (int i = 0; i < body.getTxSnapshots().size(); i++) {
            for (int j = 0; j < body.getTxSnapshots().size(); j++) {
                if (j != i) {
                    body.getTxSnapshots().get(j).setTargetTx(false);
                } else {
                    body.getTxSnapshots().get(j).setTargetTx(true);
                }
            }

            try {
                NhiRuleCheckResultVM tmp = nhiRuleCheckUtil.dispatch(
                    body.getTxSnapshots().get(i).getNhiCode(),
                    body
                );

                // Assemble results
                result.validated(result.isValidated() && tmp.isValidated());
                result.getMessages().addAll(tmp.getMessages());
                result.getCheckHistory().addAll(tmp.getCheckHistory());
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                logger.error("NhiRuleCheckResource requested a code not supported");
            }
        }
        return new ResponseEntity<>(
            result,
            HttpStatus.OK
        );
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
            return new ResponseEntity<>(
                nhiRuleCheckUtil.dispatch(code, body),
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

    @Profile("img-gcs")
    @GetMapping("/validation/reports/{yyyymm}")
    @Timed
    public List<NhiMonthDeclarationRuleCheckReportVM> getMonthDeclarationRuleCheckReport(
        @PathVariable(name = "yyyymm") Integer partialACDateTime
    ) {
        String partialACDateTimeString = String.valueOf(partialACDateTime);
        if (partialACDateTimeString.length() != 6 &&
            partialACDateTimeString.length() != 8
        ) {
            throw new BadRequestAlertException(
                "year month must be 6 or 8 digits",
                "VALIDATION",
                "as title"
            );
        }

        partialACDateTimeString = partialACDateTimeString.substring(0, 4)
            .concat("-")
            .concat(
                partialACDateTimeString.substring(4, 6)
            );

        ImageGcsBusinessService imageGcsBusinessService = applicationContext.getBean(ImageGcsBusinessService.class);
        return nhiRuleCheckUtil.getMonthDeclarationRuleCheckReport(partialACDateTimeString).stream()
            .map(d -> NhiRuleCheckMapper.INSTANCE.convertToNhiMonthDeclarationRuleCheckReport(d))
            .map(d -> {
                d.setComment(imageGcsBusinessService.getUrlForDownload() + d.getComment());
                return d;
            }).collect(Collectors.toList());
    }

    @Profile("img-gcs")
    @PostMapping("/validation/reports/{yyyymm}")
    @Timed
    @Transactional
    public ResponseEntity<String> generateMonthDeclarationRuleCheckReport(
        @PathVariable(name = "yyyymm") Integer partialACDateTime,
        @RequestBody NhiRuleCheckReportBody nhiRuleCheckReportBody
    ) {
        String partialACDateTimeString = String.valueOf(partialACDateTime);
        partialACDateTimeString = partialACDateTimeString.substring(0, 4)
            .concat("-")
            .concat(
                partialACDateTimeString.substring(4, 6)
            );

        if (
            this.nhiRuleCheckUtil.getMonthDeclarationRuleCheckReport(partialACDateTimeString).stream()
                .filter(d -> BatchStatus.LOCK.equals(d.getStatus()))
                .count() > 0
        ) {
            return ResponseEntity.accepted().body("Month declaration currently lock, wait until procedure done and download result from url");
        }

        NhiMonthDeclarationRuleCheckReport report =
            nhiRuleCheckUtil.createMonthDeclarationRuleCheckReportStatus(partialACDateTimeString);

        DateTimeUtil.BeginEnd be = DateTimeUtil.convertYearMonth(partialACDateTimeString);
        List<Long> zeroIdList = new ArrayList<>();
        zeroIdList.add(0L);
        List<Long> finalExcludeDisposals = nhiRuleCheckReportBody == null ||
            nhiRuleCheckReportBody.getExcludeDisposals() == null ||
            nhiRuleCheckReportBody.getExcludeDisposals().size() == 0
            ? zeroIdList
            : nhiRuleCheckReportBody.getExcludeDisposals();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                ImageGcsBusinessService imageGcsBusinessService = applicationContext.getBean(ImageGcsBusinessService.class);
                String gcpUrl = nhiRuleCheckUtil.generateMonthDeclarationRuleCheckReport(
                    be.getBegin(),
                    be.getEnd(),
                    finalExcludeDisposals,
                    imageGcsBusinessService
                );

                nhiRuleCheckUtil.updateMonthDeclarationRuleCheckReportStatus(
                    report.getId(),
                    BatchStatus.DONE,
                    gcpUrl
                );
            } catch (Exception e) {
                nhiRuleCheckUtil.updateMonthDeclarationRuleCheckReportStatus(
                    report.getId(),
                    BatchStatus.FAILURE,
                    e.getMessage()
                );
            }
        });

        return ResponseEntity.accepted().body("Month declaration is running.");
    }

    @Profile("img-gcs")
    @PatchMapping("/validation/reports/{id}/delock")
    @Timed
    public NhiMonthDeclarationRuleCheckReport delockMonthDeclarationRuleCheckReport(
        @PathVariable(name = "id") Long id,
        @RequestBody(required = false) String comment
    ) {
        return nhiRuleCheckUtil.updateMonthDeclarationRuleCheckReportStatus(
            id,
            BatchStatus.CANCEL,
            comment
        );
    }

}
