package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NhiRuleCheckScript340XXC {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript340XXC(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate34001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                this.getConflictList(),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_1),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_1),
                String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_1),
                String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_1),
                NhiRuleCheckFormat.W4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.VALIDATED_ALL
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate34002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                this.getConflictList(),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_1),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_1),
                String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_1),
                String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_1),
                NhiRuleCheckFormat.W4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.VALIDATED_ALL
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate34003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                this.getConflictList(),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY),
                NhiRuleCheckFormat.W4_1
            ),
            vm
        );


        return vm;
    }

    public NhiRuleCheckResultVM validate34004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("34004C"),
                null,
                null,
                1,
                NhiRuleCheckFormat.D2_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH_AND_FM
            ),
            vm
        );

        return vm;
    }

    /**
     * 若作為 private static final 會導致 object reflection 認定 method 為 null，原因不明
     * @return
     */
    private List<String> getConflictList() {
        return Arrays.asList(
            "90012C",
            "92015C",
            "90020C",
            "90016C",
            "90015C",
            "90007C",
            "90003C",
            "90002C",
            "90001C",
            "92016C",
            "90018C",
            "34002C",
            "92041C",
            "90019C",
            "92063C",
            "92058C",
            "92057C",
            "92056C",
            "92050C",
            "92042C",
            "92033C",
            "92028C",
            "90112C",
            "90006C",
            "92064C",
            "92059C"
        );
    }
}
