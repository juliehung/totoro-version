package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Endo1Point;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * (季)非根管治療點數佔總點數之百分比
 * ＠date-10＠ 的[@Point-1@-@date-10@@endo-1@點數總和]/@Point-1@
 */
public class I11Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public I11Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.disposalSource = new QuarterDisposalSource(metricConfig);
        this.source.setExclude(Tro1);
        this.disposalSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        Point1 point1 = new Point1(metricConfig, config, source, disposalSource).apply();
        Endo1Point endo1Point = new Endo1Point(metricConfig, config, source).apply();
        try {
            return divide(point1.getResult() - endo1Point.getResult(), point1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
