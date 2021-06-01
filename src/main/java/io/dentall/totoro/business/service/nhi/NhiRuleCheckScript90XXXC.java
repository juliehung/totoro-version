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
public class NhiRuleCheckScript90XXXC {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript90XXXC(
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

    public NhiRuleCheckResultVM validate90003C(NhiRuleCheckDTO dto) {
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

    public NhiRuleCheckResultVM validate90004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.GENERAL_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
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

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.DECIDUOUS_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90006C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
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

    public NhiRuleCheckResultVM validate90007C(NhiRuleCheckDTO dto) {
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

        return vm;
    }

    public NhiRuleCheckResultVM validate90008C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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

    public NhiRuleCheckResultVM validate90010C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.PERMANENT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90011C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.PERMANENT_TOOTH
                ),
                vm
            );
        }

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

    public NhiRuleCheckResultVM validate90013C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.PERMANENT_FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.PERMANENT_BACK_TOOTH
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

    public NhiRuleCheckResultVM validate90015C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("90015C"),
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                    Arrays.asList("90015C"),
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("90005C"),
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                    Arrays.asList("90005C"),
                    DateTimeUtil.NHI_2_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90016C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("90016C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                    Arrays.asList("90016C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

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
                    ToothConstraint.DECIDUOUS_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90017C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.addNotification(
                    String.format(
                        NhiRuleCheckFormat.PT1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCurrentDateHasCode(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C")
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

    public NhiRuleCheckResultVM validate90018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDate(dto,
                    Arrays.asList("90018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                    Arrays.asList("90018C"),
                    DateTimeUtil.NHI_3_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

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
                    ToothConstraint.DECIDUOUS_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90019C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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

    public NhiRuleCheckResultVM validate90020C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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

    public NhiRuleCheckResultVM validate90021C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.addNotification(
                    String.format(
                        NhiRuleCheckFormat.PT1_3.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.addNotification(
                    String.format(
                        NhiRuleCheckFormat.PT2.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90091C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90092C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90093C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90094C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90095C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90096C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90097C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

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
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90098C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isTreatmentDependOnCodeToday(
                    dto,
                    Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate90112C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
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

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.GENERAL_TOOTH
                ),
                vm
            );
        }

        return vm;
    }
}
