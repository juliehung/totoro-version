package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * (季)就醫患者之平均O.D.顆數
 *
 * @ date-10@ 的 @OD-1@齒數/@date-10@@PT-1@
 */
public class I5Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> odSource;

    private final Source<MetricTooth, MetricTooth> source;

    public I5Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdMonthSelectedSource(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.odSource.setExclude(Tro1);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        Pt1 pt1 = new Pt1(metricConfig, config, source).apply();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odSource).apply();
        try {
            return divide(od1ToothCount.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
