package io.dentall.totoro.business.service.nhi.util;

public class NhiProcedureUtil {

    public static boolean isEndoAtSalary(String code) {
        return NhiProcedureSalaryType.ENDO.getCodeListAtSalary().contains(code);
    }

    public static boolean isPeriodAtSalary(String code) {
        return NhiProcedureSalaryType.PERIOD.getCodeListAtSalary().contains(code);
    }

    public static boolean isExaminationCodeAtSalary(String code) {
        return NhiProcedureSalaryType.EXAMINATION_CODE.getCodeListAtSalary().contains(code);
    }

    public static boolean isInfectionExaminationCodeAtSalary(String code) {
        return NhiProcedureSalaryType.INFECTION_EXAMINATION_CODE.getCodeListAtSalary().contains(code);
    }
}
