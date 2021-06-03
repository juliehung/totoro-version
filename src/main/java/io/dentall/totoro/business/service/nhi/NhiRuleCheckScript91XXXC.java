package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Arrays;

@Service
public class NhiRuleCheckScript91XXXC {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript91XXXC(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate91001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithMaxTimes(dto,
                    Arrays.asList("91001C"),
                    DateTimeUtil.startDayOfMonthDiff(
                        DateTimeUtil.transformROCDateToLocalDate(
                            dto.getNhiExtendTreatmentProcedure().getA71()
                        )
                    ),
                    2,
                    NhiRuleCheckFormat.D5_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecordWithMaxTimes(dto,
                    Arrays.asList("91001C"),
                    DateTimeUtil.startDayOfMonthDiff(
                        DateTimeUtil.transformROCDateToLocalDate(
                            dto.getNhiExtendTreatmentProcedure().getA71()
                        )
                    ),
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
                            dto.getNhiExtendTreatmentProcedure().getA71()
                        )
                    )
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
                            dto.getNhiExtendTreatmentProcedure().getA71()
                        )
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentOnCodeToday(
                    dto,
                    Arrays.asList("91003C~91005C", "91017C", "91019C", "91103C", "91104C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAnyOtherTreatment(dto),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                    Arrays.asList("91003C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D7_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91004C"),
                    DateTimeUtil.NHI_6_MONTH,
                    NhiRuleCheckFormat.W1_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(dto,
                    ToothConstraint.FOUR_PHASE_ZONE),
                vm
            );
        }

        Period patientAge = Period.between(
            dto.getPatient().getBirth(),
            dto.getNhiExtendDisposal().getDate()
        );
        if (12 > patientAge.getYears()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.addNotificationWithClause(
                    dto,
                    "須檢附影像",
                    nhiRuleCheckUtil.clauseIsLessThanAge12
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

        return vm;
    }

    public NhiRuleCheckResultVM validate91004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithSamePhase(
                    dto,
                    Arrays.asList("91003C", "91004C"),
                    DateTimeUtil.NHI_6_MONTH
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.FULL_ZONE
                ),
                vm
            );
        }

        Period patientAge = Period.between(
            dto.getPatient().getBirth(),
            dto.getNhiExtendDisposal().getDate()
        );
        if (12 > patientAge.getYears()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.addNotificationWithClause(dto,
                    "須檢附影像",
                    nhiRuleCheckUtil.clauseIsLessThanAge12),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("91015C", "91016C", "91018C", "91005C", "91103C", "91104C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT3.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91005C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(
                    dto,
                    Arrays.asList("91005C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91006C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91006C~91008C", "91015C", "91016C", "91018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
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
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.FULL_ZONE
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91007C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91015C", "91016C", "91018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
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
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.PARTIAL_ZONE
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91008C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91015C", "91016C", "91018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91013C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentOnCodeToday(
                    dto,
                    Arrays.asList("91011C", "91012C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90015C", "90019C", "90020C", "89001C~89015C", "89101C~89113C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("91004C", "91005C", "91020C")
                ),
                vm
            );
        }


        if (dto.getIncludeNhiCodes().contains("91004C")) {
            if (vm.isValidated()) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.isCodeBeforeDate(
                        dto,
                        Arrays.asList("91014C"),
                        DateTimeUtil.NHI_360_DAY,
                        NhiRuleCheckFormat.D4_1
                    ),
                    vm
                );
            }

            if (vm.isValidated()) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(
                        dto,
                        Arrays.asList("91014C"),
                        DateTimeUtil.NHI_360_DAY,
                        NhiRuleCheckFormat.D4_1
                    ),
                    vm
                );
            }
        }

        if (dto.getIncludeNhiCodes().contains("91005C")) {
            if (vm.isValidated()) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.isCodeBeforeDate(
                        dto,
                        Arrays.asList("91014C"),
                        DateTimeUtil.NHI_360_DAY,
                        NhiRuleCheckFormat.D4_1
                    ),
                    vm
                );
            }

            if (vm.isValidated()) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(
                        dto,
                        Arrays.asList("91014C"),
                        DateTimeUtil.NHI_360_DAY,
                        NhiRuleCheckFormat.D4_1
                    ),
                    vm
                );
            }
        }

        if (dto.getIncludeNhiCodes().contains("91020C")) {
            if (vm.isValidated()) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.isCodeBeforeDate(
                        dto,
                        Arrays.asList("91014C"),
                        DateTimeUtil.NHI_6_MONTH,
                        NhiRuleCheckFormat.D4_1
                    ),
                    vm
                );
            }

            if (vm.isValidated()) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(
                        dto,
                        Arrays.asList("91014C"),
                        DateTimeUtil.NHI_6_MONTH,
                        NhiRuleCheckFormat.D4_1
                    ),
                    vm
                );
            }
        }

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(dto,
                ToothConstraint.FULL_ZONE),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91006C", "91007C", "91008C", "91015C", "91018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
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

        return vm;
    }

    public NhiRuleCheckResultVM validate91016C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91006C", "91007C", "91008C", "91015C", "91018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
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

        return vm;
    }

    public NhiRuleCheckResultVM validate91017C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91017C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentOnCodeToday(
                    dto,
                    Arrays.asList("91001C", "91003C", "91004C", "91005C", "91014C", "91103C", "91104C", "91019C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91022C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.PERIO_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isTreatmentDependOnCodeInDuration(
                    dto,
                    Arrays.asList("91023C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.PERIO_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91006C~91008C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91019C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentOnCodeToday(
                    dto,
                    Arrays.asList("91001C", "91003C", "91004C", "91017C", "91103C", "91104C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91020C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91020C"),
                    DateTimeUtil.NHI_6_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(
                dto
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91021C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        Period patientAge = Period.between(
            dto.getPatient().getBirth(),
            dto.getNhiExtendDisposal().getDate()
        );

        if (vm.isValidated()) {
            if (30 > patientAge.getYears() ||
                30 == patientAge.getYears() &&
                0 == patientAge.getDays() ||
                nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getId(),
                    dto.getNhiExtendDisposal().getDate(),
                    Arrays.asList("91021C"),
                    DateTimeUtil.NHI_24_MONTH,
                    dto.getExcludeTreatmentProcedureIds()
                ) != null
            ) {
                nhiRuleCheckUtil.addResultToVm(
                    nhiRuleCheckUtil.addNotification(
                        String.format(
                            NhiRuleCheckFormat.XRAY.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73()
                        )
                    ),
                    vm
                );
            }
        }

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.VPN.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91021C"),
                    DateTimeUtil.NHI_12_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithMaxTimes(
                    dto,
                    Arrays.asList("91006C", "91007C"),
                    DateTimeUtil.NHI_12_MONTH,
                    3,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91022C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isTreatmentDependOnCodeInDuration(
                    dto,
                    Arrays.asList("91021C"),
                    DateTimeUtil.NHI_152_DAY,
                    NhiRuleCheckFormat.D8_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithMaxTimes(
                    dto,
                    Arrays.asList("91006C", "91007C"),
                    DateTimeUtil.NHI_12_MONTH,
                    3,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91023C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isTreatmentDependOnCodeInDuration(
                    dto,
                    Arrays.asList("91021C"),
                    DateTimeUtil.NHI_6_MONTH,
                    NhiRuleCheckFormat.D8_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateWithMaxTimes(
                    dto,
                    Arrays.asList("91006C", "91007C"),
                    DateTimeUtil.NHI_12_MONTH,
                    3,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91103C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91103C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91104C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91104C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate91114C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("91114C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        return vm;
    }
}
