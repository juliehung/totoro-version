package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Endo90015CTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatment;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * 當月根管未完成率 ＠date-15＠ 的 [1–(90001C+90002C+90003C+90016C+90018C+90019C+90020C) ∕ 90015C]*100%
 */
public class L20Formula extends AbstractFormula {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L20Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        EndoTreatment endoTreatment = new EndoTreatment(collector, source.outputKey()).apply();
        Endo90015CTreatment endo90015CTreatment = new Endo90015CTreatment(collector, source.outputKey()).apply();
        try {
            BigDecimal tmp = divide(endoTreatment.getResult(), endo90015CTreatment.getResult());
            return BigDecimal.valueOf(100L).multiply(ONE.subtract(tmp));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
