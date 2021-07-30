package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.CourseCase;
import io.dentall.totoro.business.service.nhi.metric.meta.HighestPoint1ByPatient;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 同療程件數 ＠date-15＠ 的 就醫類別為 AA, AB 的處置單數量總和
 */
public class L11Formula extends AbstractFormula {

    private final String sourceName;

    public L11Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        CourseCase courseCase = apply(new CourseCase(sourceName));
        return new BigDecimal(courseCase.getResult());
    }
}
