package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript910XXC {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript910XXC(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate91001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoConflictNhiCode(dto,
                Arrays.asList("91003C~91005C", "91017C", "91019C", "91103C", "91104C")
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateWithMaxTimes(dto,
                Arrays.asList("91001C"),
                DateTimeUtil.startDayOfMonthDiff(
                    DateTimeUtil.transformROCDateToLocalDate(
                        dto.getNhiExtendTreatmentProcedure().getA71())),
                2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isSinglePhase(dto),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoSelfConflictNhiCode(dto),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecordWithMaxTimes(dto,
                    Arrays.asList("91001C"),
                    DateTimeUtil.startDayOfMonthDiff(
                        DateTimeUtil.transformROCDateToLocalDate(
                            dto.getNhiExtendTreatmentProcedure().getA71())),
                    2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithSamePhase(dto,
                    Arrays.asList("91001C"),
                    DateTimeUtil.startDayOfMonthDiff(
                        DateTimeUtil.transformROCDateToLocalDate(
                            dto.getNhiExtendTreatmentProcedure().getA71()))
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecordWithSamePhase(dto,
                    Arrays.asList("91001C"),
                    DateTimeUtil.startDayOfMonthDiff(
                        DateTimeUtil.transformROCDateToLocalDate(
                            dto.getNhiExtendTreatmentProcedure().getA71()))
                ),
                vm
            );
        }

        return vm;
    }

    // 910***

    public NhiRuleCheckResultVM validate91003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(dto,
                ToothConstraint.FOUR_PHASE_ZONE),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotificationWithClause(dto,
                "須檢附影像",
                nhiRuleCheckUtil.clauseIsLessThanAge12),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithSamePhase(dto,
                    Arrays.asList("91003C", "91004C"),
                    DateTimeUtil.NHI_6_MONTH
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91018C", "91005C", "91103C", "91104C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91003C", "91004C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D7_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91003C", "91004C"),
                    DateTimeUtil.NHI_6_MONTH,
                    NhiRuleCheckFormat.W1_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoSelfConflictNhiCode(dto),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate91004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(dto,
                ToothConstraint.FULL_ZONE),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotificationWithClause(dto,
                "須檢附影像",
                nhiRuleCheckUtil.clauseIsLessThanAge12),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithSamePhase(dto,
                    Arrays.asList("91003C", "91004C"),
                    DateTimeUtil.NHI_6_MONTH
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91018C", "91005C", "91103C", "91104C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91003C", "91004C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D7_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91003C", "91004C"),
                    DateTimeUtil.NHI_6_MONTH,
                    NhiRuleCheckFormat.W1_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoSelfConflictNhiCode(dto),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate91005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91005C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"91005C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate91014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(dto,
                ToothConstraint.FULL_ZONE),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isMustIncludeNhiCode(dto,
                Arrays.asList("91004C", "91005C", "91020C")),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoSelfConflictNhiCode(dto),
                vm
            );
        }

        if (vm.isValidated() &&
            dto.getIncludeNhiCodes().contains("91004C")
        ) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                    Arrays.asList("91014C"),
                    DateTimeUtil.NHI_360_DAY),
                vm
            );

            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91014C"),
                    DateTimeUtil.NHI_360_DAY),
                vm
            );
        }

        if (vm.isValidated() &&
            dto.getIncludeNhiCodes().contains("91005C")
        ) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                    Arrays.asList("91014C"),
                    DateTimeUtil.NHI_360_DAY),
                vm
            );

            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91014C"),
                    DateTimeUtil.NHI_360_DAY),
                vm
            );
        }

        if (vm.isValidated() &&
            dto.getIncludeNhiCodes().contains("91020C")
        ) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                    Arrays.asList("91014C"),
                    DateTimeUtil.NHI_6_MONTH),
                vm
            );

            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91014C"),
                    DateTimeUtil.NHI_6_MONTH),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate91015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91015C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"91015C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate91016C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91016C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"91016C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate91017C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91017C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"91017C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate91018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91018C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"91018C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate91020C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "牙菌斑清除"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"91020C"}.clone()),
                DateTimeUtil.NHI_6_MONTH
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(
                dto
            ),
            vm
        );

        return vm;
    }
}
