package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Service
public class NhiRuleCheckService20200901Impl implements NhiRuleCheckService {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckService20200901Impl(NhiRuleCheckUtil nhiRuleCheckUtil) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    private NhiRuleCheckDTO convertVmToDto(NhiRuleCheckVM vm) {
        return nhiRuleCheckUtil.convertVmToDto(vm);
    }

    @Override
    public NhiRuleCheckResultVM dispatcher(String code, NhiRuleCheckVM vm) throws
        NoSuchMethodException,
        InvocationTargetException,
        IllegalAccessException {
        return (NhiRuleCheckResultVM) this.getClass()
            .getMethod("".concat(code), NhiRuleCheckDTO.class)
            .invoke(this, convertVmToDto(vm));
    }

    @Override
    public NhiRuleCheckResultVM validate91003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91003C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91004C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91005C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91005C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91015C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91015C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91016C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91016C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91017C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91017C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91018C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91103C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91103C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91104C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        // 半年內已做過 91003C 的治療
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91104C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear),
            vm
        );

        // 病患是否大於 12 歲
        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.equalsOrGreaterThanAge12(dto),
            vm
        );

        return vm;
    }
}
