package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

/**
 * (季)就醫病患者平均耗用值
 * <p>
 * (@date-10@@Point-2@/@date-10@@PT-1@)/3
 */
public class J2h3Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public J2h3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.disposalSource = new QuarterDisposalSource(metricConfig);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Point2 point2 = new Point2(metricConfig, source, disposalSource).apply();
        Pt1 pt1 = new Pt1(metricConfig, source).apply();

        try {
            return divide(divide(point2.getResult(), pt1.getResult()), valueOf(3));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }

}
