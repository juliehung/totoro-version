package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.SurfaceConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript89XXXC {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript89XXXC(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate89001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_1_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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

        return vm;
    }

    public NhiRuleCheckResultVM validate89002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_2_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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

        return vm;
    }

    public NhiRuleCheckResultVM validate89003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_3_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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

        return vm;
    }

    public NhiRuleCheckResultVM validate89004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_1_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_2_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89006C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89006C"),
                DateTimeUtil.NHI_6_MONTH,
                DateTimeUtil.NHI_6_MONTH,
                NhiRuleCheckFormat.D4_1
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

    public NhiRuleCheckResultVM validate89007C(NhiRuleCheckDTO dto) {
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

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isMustIncludeNhiCode(dto,
                    Arrays.asList("89001C~89015C", "89101C~89115C")),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89008C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_1_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89009C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_2_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89010C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_3_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89011C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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

        return vm;
    }

    public NhiRuleCheckResultVM validate89012C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_3_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89013C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.isCodeBeforeDate(
                    dto,
                    Arrays.asList("89013C"),
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D4_1
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.EQUAL_4_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH,
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
                    dto,
                    Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                    DateTimeUtil.NHI_12_MONTH,
                    DateTimeUtil.NHI_18_MONTH,
                    NhiRuleCheckFormat.D1_2
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.EQUAL_4_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatment(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentByNhiMedicalRecord(
                    dto,
                    Arrays.asList("90007C")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89088C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();
        // Do nothing
        return vm;
    }

    public NhiRuleCheckResultVM validate89101C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_1_SURFACES
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

    public NhiRuleCheckResultVM validate89102C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_2_SURFACES
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

    public NhiRuleCheckResultVM validate89103C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_3_SURFACES
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

    public NhiRuleCheckResultVM validate89104C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_1_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89105C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_2_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89108C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_1_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89109C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_2_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89110C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_3_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89111C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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

    public NhiRuleCheckResultVM validate89112C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedSurface(
                    dto,
                    SurfaceConstraint.MIN_3_SURFACES
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89113C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
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

    public NhiRuleCheckResultVM validate89114C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.FRONT_TOOTH
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate89115C(NhiRuleCheckDTO dto) {
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
                nhiRuleCheckUtil.specificRule_1_for89XXXC(
                    dto
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.BACK_TOOTH
                ),
                vm
            );
        }

        return vm;
    }
}
