package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Endo90015CTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatment;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * 一年根管未完成率
 * [1–(＠date-3＠ 90001C+ ＠date-3＠ 90002C+ ＠date-3＠ 90003C+ ＠date-3＠ 90016C+ ＠date-3＠ 90018C+ ＠date-3＠ 90019C+＠date-3＠ 90020C) ∕ ＠date-3＠  90015C]*100%
 */
public class L23Formula extends AbstractFormula {

    private final String sourceName;

    public L23Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        EndoTreatment endoTreatment = apply(new EndoTreatment(sourceName));
        Endo90015CTreatment endo90015CTreatment = apply(new Endo90015CTreatment(sourceName));
        try {
            BigDecimal tmp = divide(endoTreatment.getResult(), endo90015CTreatment.getResult());
            return BigDecimal.valueOf(100L).multiply(ONE.subtract(tmp));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
