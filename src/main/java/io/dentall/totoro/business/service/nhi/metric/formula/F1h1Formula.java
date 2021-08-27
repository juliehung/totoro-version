package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;

/**
 * 合計點數 ＠date-15＠ 的 @Point-1@
 */
public class F1h1Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public F1h1Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.source.setExclude(Tro1);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config tro1Config = new Tro1Config(metricConfig);
        Point1 point1 = new Point1(metricConfig, tro1Config, source).apply();
        return new BigDecimal(point1.getResult());
    }

}
