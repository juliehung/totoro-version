package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript00XXXC {
    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript00XXXC(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate00315C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleForXray(
                dto,
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_1095_DAY),
                String.valueOf(DateTimeUtil.NHI_1095_DAY.getDays()),
                NhiRuleCheckFormat.D1_2,
                null,
                null,
                NhiRuleCheckFormat.D1_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.XRAY.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("01271C", "01272C", "01273C", "00316C", "00317C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate00316C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleForXray(
                dto,
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                NhiRuleCheckFormat.D1_5,
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.XRAY.getFormat(),
                    nhiRuleCheckUtil.getCurrentTxNhiCode(dto)
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("01271C", "01272C", "01273C", "00315C", "00317C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate00317C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("00317C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.XRAY.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("01271C", "01272C", "01273C", "00315C", "00316C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

}
