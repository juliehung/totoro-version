package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.SurfaceConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Service
public class NhiRuleCheckService20200901Impl implements NhiRuleCheckService<NhiRuleCheckVM, NhiRuleCheckDTO, NhiRuleCheckResultVM> {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckService20200901Impl(NhiRuleCheckUtil nhiRuleCheckUtil) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    private NhiRuleCheckDTO convertVmToDto(String code, NhiRuleCheckVM vm) {
        return nhiRuleCheckUtil.convertVmToDto(code, vm);
    }

    @Override
    public NhiRuleCheckResultVM dispatch(String code, NhiRuleCheckVM vm) throws
        NoSuchMethodException,
        InvocationTargetException,
        IllegalAccessException {
        return (NhiRuleCheckResultVM) this.getClass()
            .getMethod("validate".concat(code), NhiRuleCheckDTO.class)
            .invoke(this, this.convertVmToDto(code, vm));
    }

    // 910***
    @Override
    public NhiRuleCheckResultVM validate91003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91003C"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91004C"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
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

    @Override
    public NhiRuleCheckResultVM validate91015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
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

    @Override
    public NhiRuleCheckResultVM validate91016C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
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

    @Override
    public NhiRuleCheckResultVM validate91017C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
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

    @Override
    public NhiRuleCheckResultVM validate91018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
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

    @Override
    public NhiRuleCheckResultVM validate91103C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91103C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91104C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91104C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate81(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"81"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge6(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate87(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"87"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate88(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"88"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge6(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(
                dto,
                Arrays.asList(new String[]{"89"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
                vm);

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(dto),
            vm);

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
              "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89002C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_2_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_2_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89008C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89009C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89010C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89011C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89012C(NhiRuleCheckDTO dto) {

        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89013C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PERMANENT_TOOTH
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                null,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PERMANENT_TOOTH
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                null,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89015C(NhiRuleCheckDTO dto) {

        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89101C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MAX_3_SURFACES
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89102C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89103C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89104C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89105C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89108C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89109C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89110C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89111C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89112C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89113C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89114C(NhiRuleCheckDTO dto) {
        return null;
    }

    @Override
    public NhiRuleCheckResultVM validate89115C(NhiRuleCheckDTO dto) {
        return null;
    }
}
