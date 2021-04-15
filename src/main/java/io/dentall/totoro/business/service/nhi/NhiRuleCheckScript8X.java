package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript8X {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript8X(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate81(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge6(dto),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoSelfConflictNhiCode(dto),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(dto,
                    Arrays.asList("81"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_6),
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(dto,
                    Arrays.asList("81"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_6),
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate87(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"87"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"87"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate88(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"88"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"88"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge6(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate89(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(
                dto,
                Arrays.asList(new String[]{"89"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm);

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"89"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(dto),
            vm);

        return vm;
    }
}
