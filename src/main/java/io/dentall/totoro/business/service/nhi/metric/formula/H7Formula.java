package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1ButPoint6Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * (月)院所別就醫患者之平均OD填補顆數>各分區95百分位列計1點
 *
 * @ OD-1@齒數/@PT-1@
 */
public class H7Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> odSource;

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public H7Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdMonthSelectedSource(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.odSource.setExclude(Tro1);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1ButPoint6Config config = new Tro1ButPoint6Config(metricConfig);
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odSource).apply();
        Pt1 pt1 = new Pt1(metricConfig, config, source).apply();
        try {
            return divide(od1ToothCount.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
