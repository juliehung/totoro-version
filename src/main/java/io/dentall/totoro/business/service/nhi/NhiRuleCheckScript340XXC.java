package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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

        LocalDate tpDate = DateTimeUtil.transformROCDateToLocalDate(
            dto.getNhiExtendDisposal().getA17()
        );
        LocalDate beginDayOfMonthOfTp = tpDate.withDayOfMonth(1);

        if (vm.isValidated()) {
            nhiRuleCheckUtil.isCodeBeforeDate(
                dto,
                this.getConflictList(),
                Period.between(beginDayOfMonthOfTp, tpDate),
                NhiRuleCheckFormat.W4_1
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(
                dto,
                this.getConflictList(),
                Period.between(beginDayOfMonthOfTp, tpDate),
                NhiRuleCheckFormat.W4_1
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.VALIDATED_ALL
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCurrentDateHasCode(
                    dto,
                    this.getConflictList()
                ),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate34002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        LocalDate tpDate = DateTimeUtil.transformROCDateToLocalDate(
            dto.getNhiExtendDisposal().getA17()
        );
        LocalDate beginDayOfMonthOfTp = tpDate.withDayOfMonth(1);

        if (vm.isValidated()) {
            nhiRuleCheckUtil.isCodeBeforeDate(
                dto,
                this.getConflictList(),
                Period.between(beginDayOfMonthOfTp, tpDate),
                NhiRuleCheckFormat.W4_1
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(
                dto,
                this.getConflictList(),
                Period.between(beginDayOfMonthOfTp, tpDate),
                NhiRuleCheckFormat.W4_1
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.VALIDATED_ALL
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCurrentDateHasCode(
                    dto,
                    this.getConflictList()
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate34003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCurrentDateHasCode(
                    dto,
                    this.getConflictList()
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate34004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("34004C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("34004C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoSelfConflictNhiCode(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.VALIDATED_ALL
            );
        }

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
