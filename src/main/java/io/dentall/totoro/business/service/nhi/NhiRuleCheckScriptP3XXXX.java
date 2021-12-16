package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScriptP3XXXX {
    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScriptP3XXXX(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validateP30002(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("P30002"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("P6702C", "P6703C", "P6704C", "P6705C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }
}
