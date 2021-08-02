package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.NhiRuleCheckSourceType;
import io.dentall.totoro.business.service.nhi.util.*;
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
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_1_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(dto),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_2_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_3_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(dto),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_1_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_2_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89006C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89006C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PERMANENT_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PERMANENT_TOOTH
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isDependOnCodeBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89015C", "89101C~89115C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY),
                String.valueOf(DateTimeUtil.NHI_0_DAY.getDays()),
                NhiRuleCheckFormat.W3_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89008C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                NhiRuleCheckSourceType.SYSTEM_RECORD,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_1_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89009C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                NhiRuleCheckSourceType.SYSTEM_RECORD,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_2_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89010C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                NhiRuleCheckSourceType.SYSTEM_RECORD,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_3_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89011C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89012C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                NhiRuleCheckSourceType.SYSTEM_RECORD,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_3_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                NhiRuleCheckSourceType.SYSTEM_RECORD,
                Arrays.asList("89013C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                NhiRuleCheckSourceType.SYSTEM_RECORD,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.EQUAL_4_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("89001C~89005C", "89008C~89012C", "89014C~89015C"),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_365_DAY),
                nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_545_DAY),
                String.valueOf(DateTimeUtil.NHI_365_DAY.getDays()),
                String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                NhiRuleCheckFormat.D1_2
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.EQUAL_4_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoCodeWithToothBeforeDate(
                dto,
                null,
                Arrays.asList("90007C"),
                null,
                null,
                null,
                null,
                NhiRuleCheckFormat.D1_3
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoRemovedTeeth(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89088C(NhiRuleCheckDTO dto) {
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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_1_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_2_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_3_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_1_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_2_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_1_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
            );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_2_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_3_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MIN_3_SURFACES
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PERMANENT_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH
            ),
            vm
        );

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

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isSpecialRuleFor89XXXC(
                dto
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH
            ),
            vm
        );

        return vm;
    }
}
