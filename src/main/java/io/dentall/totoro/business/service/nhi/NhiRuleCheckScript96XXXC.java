package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript96XXXC {
    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript96XXXC(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate96001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PARTIAL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList(
                    "90001C", "90002C", "90003C", "90005C", "90015C", "90016C", "90018C", "90019C", "90020C", "91013C", "92012C", "92013C", "92014C", "92015C", "92016C", "92017C",
                    "92027C", "92028C", "92029C", "92030C", "92031C", "92032C", "92033C", "92041C", "92042C", "92043C", "92050C", "92055C", "92056C", "92057C", "92058C", "92059C",
                    "92063C", "92064C", "92071C", "92092C"
                ),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                1,
                NhiRuleCheckFormat.W4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isW5_1(dto),
            vm
        );

        return vm;
    }
}
