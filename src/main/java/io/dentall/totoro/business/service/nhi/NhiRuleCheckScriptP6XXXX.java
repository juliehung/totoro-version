package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScriptP6XXXX {
    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScriptP6XXXX(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validateP6701C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }

    public NhiRuleCheckResultVM validateP6702C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("P6701C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                NhiRuleCheckFormat.D8_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91014C", "91020C", "91114C", "92051B", "92072C", "P30002", "81", "87"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D6_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validateP6703C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("P6702C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("P6702C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D8_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("91014C", "91114C", "91020C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                NhiRuleCheckFormat.W3_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validateP6704C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("P6703C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("P6703C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                NhiRuleCheckFormat.D8_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91014C", "91020C", "91114C", "92051B", "92072C", "P30002", "81", "87"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D6_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validateP6705C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("P6704C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("P6704C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D8_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("91014C", "91114C", "91020C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                NhiRuleCheckFormat.W3_1
            ),
            vm
        );

        return vm;
    }
}
