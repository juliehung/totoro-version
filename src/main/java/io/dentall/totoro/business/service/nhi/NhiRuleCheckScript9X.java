package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript9X {
    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript9X(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate95(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("95"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.moreThanOrEqualToAge30(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate97(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.moreOrEqualToAge18AndLessThan30(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("97"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_730_DAY),
                String.valueOf(DateTimeUtil.NHI_730_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );


        return vm;
    }
}
