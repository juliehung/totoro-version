package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Endo90015CTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * 近三月根管未完成率 ＠date-15＠ 的 [1–(90001C+90002C+90003C+90016C+90018C+90019C+90020C) ∕ 90015C]*100%
 */
public class L21Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L21Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        MetaConfig config = new Tro1Config(collector);
        EndoTreatment endoTreatment = new EndoTreatment(collector, config, source.outputKey()).apply();
        Endo90015CTreatment endo90015CTreatment = new Endo90015CTreatment(collector, config, source.outputKey()).apply();
        try {
            BigDecimal tmp = divide(endoTreatment.getResult(), endo90015CTreatment.getResult());
            return toPercentage(ONE.subtract(tmp));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
