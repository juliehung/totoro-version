package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Point;
import io.dentall.totoro.business.service.nhi.metric.meta.Point3;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * (季)O.D.點數佔總點數之百分比
 *
 * @ date-10@ (@OD-1@/@Point-3@)*100%
 */
public class I18Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricTooth, MetricTooth> odSource;

    public I18Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.odSource = new OdQuarterSource(metricConfig);
        this.source.setExclude(Tro1);
        this.odSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        Point3 point3 = new Point3(metricConfig, config, source).apply();
        Od1Point od1Point = new Od1Point(metricConfig, config, odSource).apply();
        try {
            return toPercentage(od1Point.getResult(), point3.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
