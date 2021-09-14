package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Pt2;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * (月)平均每位 OD 患者填補顆數
 * <p>
 * ＠date-15＠ 的 @OD-1@齒數/@PT-2@@OD-1@
 */
public class F5h5Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public F5h5Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdMonthSelectedSource(metricConfig);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, source).apply();
        Od1Pt2 od1Pt2 = new Od1Pt2(metricConfig, config, source).apply();
        try {
            return divide(od1ToothCount.getResult(), od1Pt2.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
