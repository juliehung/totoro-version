package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.NhiRuleCheckSourceType;
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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.VALIDATED_ALL_EXCLUDE_FM
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isD5_1(
                dto,
                null,
                Arrays.asList("91001C"),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_1),
                String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_1),
                3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isD4_2(
                dto,
                null,
                Arrays.asList("91001C"),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_1),
                String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_1)
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91003C", "91004C", "91005C", "91017C", "91103C", "91104C", "91019C", "91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D6_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.ALL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isW5_1(dto),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isD4_2(
                dto,
                null,
                Arrays.asList("91003C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays())
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FOUR_PHASE_ZONE
            ),
            vm
        );

        if (nhiRuleCheckUtil.getPatientAge(dto).getYears() < 12) {
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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91003C", "91104C", "91015C", "91016C", "91017C", "91018C", "91005C", "91103C", "91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91004C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        if (nhiRuleCheckUtil.getPatientAge(dto).getYears() < 12) {
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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91003C", "91104C", "91015C", "91016C", "91017C", "91018C", "91005C", "91103C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT3.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91005C"),
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
                Arrays.asList("91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91006C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91018C", "91015C", "91016C", "91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91006C~91008C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91007C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91018C", "91015C", "91016C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FOUR_PHASE_ZONE
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91008C(NhiRuleCheckDTO dto) {
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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FOUR_PHASE_ZONE_AND_PERMANENT_TOOTH
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91018C", "91015C", "91016C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91011C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FOUR_PHASE_ZONE_AND_PERMANENT_TOOTH
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PERIO_REC_2.getFormat(),
                    nhiRuleCheckUtil.getCurrentTxNhiCode(dto)
                )
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91012C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PARTIAL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PERIO_REC_2.getFormat(),
                    nhiRuleCheckUtil.getCurrentTxNhiCode(dto)
                )
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91013C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91011C", "91012C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D6_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("90001C", "90002C", "90003C", "90015C", "90019C", "90020C", "89001C~89015C", "89101C~89113C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                NhiRuleCheckFormat.W3_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("91004C", "91005C", "91020C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                NhiRuleCheckFormat.W3_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor91014C(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PERIO_REC_1.getFormat(),
                    nhiRuleCheckUtil.getCurrentTxNhiCode(dto)
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91018C", "91015C", "91006C", "91007C", "91008C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
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

        return vm;
    }

    public NhiRuleCheckResultVM validate91016C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PERIO_REC_1.getFormat(),
                    nhiRuleCheckUtil.getCurrentTxNhiCode(dto)
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91018C", "91015C", "91006C", "91007C", "91008C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
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

        return vm;
    }

    public NhiRuleCheckResultVM validate91017C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91017C"),
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
                Arrays.asList("91001C", "91003C", "91004C", "91005C", "91014C", "91103C", "91104C", "91019C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D6_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor91018C(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91018C"),
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
                Arrays.asList("91006C~91008C", "91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91019C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.VALIDATED_ALL_EXCLUDE_FM
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91001C", "91003C", "91004C", "91017C", "91103C", "91104C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D6_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91020C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91020C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
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

    public NhiRuleCheckResultVM validate91021C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        Period age = nhiRuleCheckUtil.getPatientAge(dto);
        if (age.getYears() < 30 ||
            age.getYears() == 30 &&
            age.getMonths() == 0 &&
            age.getDays() == 0 ||
            nhiRuleCheckUtil.getSourceDataByOnlySourceTypeAndCodesAndDuration(
                dto,
                NhiRuleCheckSourceType.SYSTEM_RECORD,
                Arrays.asList("91021C"),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_YEAR_2)
            ).size() > 0
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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.VPN.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91021C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91006C", "91007C"),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_YEAR_1),
                String.valueOf(1),
                3,
                NhiRuleCheckFormat.D1_2_3
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91022C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PERIO_REC_1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("91021C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_152_DAY),
                String.valueOf(DateTimeUtil.NHI_152_DAY.getDays()),
                NhiRuleCheckFormat.D8_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91006C", "91007C"),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_YEAR_1),
                String.valueOf(1),
                3,
                NhiRuleCheckFormat.D1_2_3
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91023C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PERIO_REC_1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("91021C", "91022C"),
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
                Arrays.asList("91006C", "91007C"),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_YEAR_1),
                String.valueOf(1),
                3,
                NhiRuleCheckFormat.D1_2_3
            ),
            vm
        );

        return vm;
    }


    public NhiRuleCheckResultVM validate91088C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91103C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FOUR_PHASE_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91103C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91104C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91089C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91104C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate91114C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FULL_ZONE
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                String.format(
                    NhiRuleCheckFormat.PT1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("91114C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        return vm;
    }
}
