package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
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

    public NhiRuleCheckResultVM validate90001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.PERMANENT_TOOTH
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isTreatmentDependOnCodeInDuration(
                    dto,
                    Arrays.asList("90015C"),
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D8_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNhiMedicalRecordDependOnCodeInDuration(
                    dto,
                    Arrays.asList("90015C"),
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D8_1
                ),
                vm
            );
        }

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
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                    dto,
                    Arrays.asList("90001C"),
                    DateTimeUtil.NHI_2_MONTH,
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D7_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90001C"),
                    DateTimeUtil.NHI_2_MONTH,
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D7_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                    dto,
                    Arrays.asList("90001C~90003C", "90019C~90020C"),
                    DateTimeUtil.NHI_3_MONTH,
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.W6_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90001C~90003C", "90019C~90020C"),
                    DateTimeUtil.NHI_3_MONTH,
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.W6_1
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }

    public NhiRuleCheckResultVM validate90003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }

    public NhiRuleCheckResultVM validate90007C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
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

    public NhiRuleCheckResultVM validate90015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }


    public NhiRuleCheckResultVM validate90016C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }

    public NhiRuleCheckResultVM validate90018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }

    public NhiRuleCheckResultVM validate90019C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }

    public NhiRuleCheckResultVM validate90020C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        return vm;
    }

}
