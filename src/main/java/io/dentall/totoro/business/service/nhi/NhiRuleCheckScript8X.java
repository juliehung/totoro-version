package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckScript8X {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckScript8X(
        NhiRuleCheckUtil nhiRuleCheckUtil
    ) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    public NhiRuleCheckResultVM validate81(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge6(dto),
            vm
        );

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.isCodeBeforeDateV2(
                dto,
                null,
                Arrays.asList("81"),
                nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_6),
                String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_6),
                1,
                NhiRuleCheckFormat.D4_1
            ),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate87(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("87"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_3),
                    DateTimeUtil.NUMBERS_OF_MONTH_3
                ),
                vm
            );
        }

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(dto),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate88(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("88"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_3),
                    DateTimeUtil.NUMBERS_OF_MONTH_3
                ),
                vm
            );
        }

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge6(dto),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate89(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("81", "87", "88", "89"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_3),
                    DateTimeUtil.NUMBERS_OF_MONTH_3
                ),
                vm
            );
        }

        nhiRuleCheckUtil.addResultToVm(
            nhiRuleCheckUtil.lessThanAge12(dto),
            vm
        );

        return vm;
    }

    public NhiRuleCheckResultVM validate8A(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_16
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8A"
                    )
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8B(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_26
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8B"
                    )
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_36
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8C"
                    )
                ),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate8D(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_46
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8D"
                    )
                ),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate8E(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_16
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8E"
                    )
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8F(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_26
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8F"
                    )
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8G(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_36
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8G"
                    )
                ),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate8H(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();


        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_46
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8H"
                    )
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8I(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_16
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8I"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8A", "8E"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8A", "8E"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8A", "8E")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8J(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_26
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8J"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8B", "8F"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8B", "8F"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8B", "8F")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8K(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_36
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8K"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8C", "8G"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8C", "8G"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8C", "8G")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8L(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_46
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8L"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8D", "8H"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8D", "8H"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8D", "8H")
                ),
                vm
            );
        }

        return vm;
    }


    public NhiRuleCheckResultVM validate8M(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_16
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8I"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8A", "8E"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_12
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_12
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8A", "8E"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_12
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_12
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8I"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8I"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8A", "8E")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8I")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8N(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_26
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8N"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8B", "8F"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_12
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_12
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8B", "8F"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_12
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_12
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8J"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8J"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8B", "8F")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8J")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8O(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_36
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8O"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8C", "8G"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8C", "8G"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_12
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_12
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8K"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8K"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8C", "8G")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8K")
                ),
                vm
            );
        }

        return vm;
    }

    public NhiRuleCheckResultVM validate8P(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isAllLimitedTooth(
                    dto,
                    ToothConstraint.ONLY_46
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isOnceInWholeLife(
                    dto,
                    Arrays.asList(
                        "8P"
                    )
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8D", "8H"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_12
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_12
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8D", "8H"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_12
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_12
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDuration(
                    dto,
                    Arrays.asList("8L"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                nhiRuleCheckUtil.isCodeBetweenDurationByNhiMedicalRecord(
                    dto,
                    Arrays.asList("8L"),
                    nhiRuleCheckUtil.specialMonthDurationCalculation(
                        dto,
                        DateTimeUtil.NUMBERS_OF_MONTH_6
                    ),
                    DateTimeUtil.NUMBERS_OF_MONTH_6
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8D", "8H")
                ),
                vm
            );
        }

        if (vm.isValidated()) {
            nhiRuleCheckUtil.addResultToVm(
                this.nhiRuleCheckUtil.isTreatmentDependOnCode(
                    dto,
                    Arrays.asList("8L")
                ),
                vm
            );
        }

        return vm;
    }
}
