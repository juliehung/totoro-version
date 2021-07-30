package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Endo90015CTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoReTreatmentByTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatmentByTooth;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * 半年根管再治療率
 * date-2 Point-11 (90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C) / ＠date-2@(90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C)
 */
public class L24Formula extends AbstractFormula {

    private final String sourceName;

    public L24Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        EndoTreatmentByTooth endoTreatmentByTooth = apply(new EndoTreatmentByTooth(sourceName));
        EndoReTreatmentByTooth endoReTreatmentByTooth = apply(new EndoReTreatmentByTooth(sourceName));
        try {
            return divide(endoReTreatmentByTooth.getResult(), endoTreatmentByTooth.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
