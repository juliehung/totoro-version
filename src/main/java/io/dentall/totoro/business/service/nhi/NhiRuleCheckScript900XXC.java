package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript900XXC {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript900XXC(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }


    public NhiRuleCheckResultVM validate90012C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.XRAY.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentAtSpecificTooth(
                    dto,
                    Arrays.asList("92013C~92015C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoNhiMedicalRecordAtSpecificTooth(
                    dto,
                    Arrays.asList("92013C~92015C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.PERMANENT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

}
