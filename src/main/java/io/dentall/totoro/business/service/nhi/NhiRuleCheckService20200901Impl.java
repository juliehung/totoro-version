package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.CopaymentCode;
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

    @Override
    public NhiRuleCheckResultVM dispatch(String code, NhiRuleCheckVM vm) throws
        NoSuchMethodException,
        InvocationTargetException,
        IllegalAccessException {

        // 轉換至統一入口 Object
        NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(code, vm);

        // 依照個代碼進行檢核
        NhiRuleCheckResultVM rvm = (NhiRuleCheckResultVM) this.getClass()
            .getMethod("validate".concat(code), NhiRuleCheckDTO.class)
            .invoke(this, dto);

        // 若代碼檢核無異常，則根據不同情境回傳訊息
        if (rvm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.appendSuccessSourceInfo(dto),
                rvm
            );
        }

        return rvm;
    }

    // 012***, TODO: 啟用時記得加回 overwrite annotation
    public NhiRuleCheckResultVM validate01271C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoConflictNhiCode(dto,
                Arrays.asList("34001C", "34004C")
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoTreatmentInPeriod(dto,
                DateTimeUtil.NHI_36_MONTH),
            vm
        );

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isNoTreatmentInPeriodByNhiMedicalRecord(dto),
                vm
            );
        }

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification("申報時應檢附Panoramic radiography環口全景X光片攝影。"),
            vm
        );


        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification("病歷中除應記載缺牙部位、牙冠牙橋與阻生齒外，應記載X光片呈現之診斷與發現。"),
            vm
        );

        return vm;
    }

    // 900***, TODO: 啟用時記得加回 overwrite annotation
    public NhiRuleCheckResultVM validate90012C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification("使用橡皮障防濕裝置時，需檢附Ｘ光片或相片(規格需為3*5吋以上且可清晰判讀)佐證。（X光片或相片費用已內含）。"),
            vm
        );

        return vm;
    }

    // 910***, TODO: 啟用時記得加回 overwrite annotation
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
    @Override
    public NhiRuleCheckResultVM validate91003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(dto,
                Arrays.asList(new String[]{"91003C"}.clone()),
                DateTimeUtil.NHI_6_MONTH,
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(dto,
                Arrays.asList(new String[]{"91003C"}.clone()),
                DateTimeUtil.NHI_6_MONTH,
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91004C"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"91004C"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(dto,
                ToothConstraint.FOUR_PHASE_ZONE),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate91004C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList("91004C", "91003C"),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList("91004C", "91003C"),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList("91015C~91018C"),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList("91015C~91018C"),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList("91103C", "91104C"),
                DateTimeUtil.NHI_2_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList("91103C", "91104C"),
                DateTimeUtil.NHI_2_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoConflictNhiCode(dto,
                Arrays.asList("91001C", "91017C", "91019C")
            ),
            vm
        );

        return vm;
    }

    @Override
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

    @Override
    public NhiRuleCheckResultVM validate91014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isMustIncludeNhiCode(dto,
                Arrays.asList("91004C", "91005C", "91020C")),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isNoConflictNhiCode(dto,
                Arrays.asList("91017C")),
            vm
        );

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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public NhiRuleCheckResultVM validate91103C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91103C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
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
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91104C"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
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
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"81"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"81"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
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
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"87"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
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
            nhiRuleCheckUtil.isCodeBeforeDate(dto,
                Arrays.asList(new String[]{"88"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"88"}.clone()),
                DateTimeUtil.NHI_6_MONTH),
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
            nhiRuleCheckUtil.isCodeBeforeDate(
                dto,
                Arrays.asList(new String[]{"89"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm);

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateByNhiMedicalRecord(dto,
                Arrays.asList(new String[]{"89"}.clone()),
                DateTimeUtil.NHI_3_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(dto),
            vm);

        return vm;
    }

    // 890**C
    @Override
    public NhiRuleCheckResultVM validate89001C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.GENERAL_TOOTH
            ),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89012C(NhiRuleCheckDTO dto) {

        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                null,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.PERMANENT_TOOTH),
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
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89015C(NhiRuleCheckDTO dto) {

        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
                dto,
                Arrays.asList(new String[]{"89001C~89005C", "89008C~89012C", "89014C~89015C"}.clone()),
                DateTimeUtil.NHI_12_MONTH,
                DateTimeUtil.NHI_18_MONTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
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
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89101C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
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
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
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
    public NhiRuleCheckResultVM validate89103C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
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
    public NhiRuleCheckResultVM validate89104C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
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
    public NhiRuleCheckResultVM validate89105C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
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
    public NhiRuleCheckResultVM validate89108C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
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
    public NhiRuleCheckResultVM validate89109C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
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
    public NhiRuleCheckResultVM validate89110C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
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
    public NhiRuleCheckResultVM validate89111C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
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

    @Override
    public NhiRuleCheckResultVM validate89112C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
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
    public NhiRuleCheckResultVM validate89113C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
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

    @Override
    public NhiRuleCheckResultVM validate89114C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.FRONT_TOOTH),
            vm
        );

        return vm;
    }

    @Override
    public NhiRuleCheckResultVM validate89115C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isPatientIdentityInclude(
                dto,
                CopaymentCode._001
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.addNotification(
                "應於病歷詳列充填牙面部位。"
            ),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedTooth(
                dto,
                ToothConstraint.BACK_TOOTH),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isAllLimitedSurface(
                dto,
                SurfaceConstraint.MUST_HAVE_M_D_O
            ),
            vm
        );

        return vm;
    }
}
