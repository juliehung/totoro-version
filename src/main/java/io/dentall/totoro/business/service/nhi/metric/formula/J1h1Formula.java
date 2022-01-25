package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro2Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro2;

/**
 * 院所之醫師個人每月醫療費用點數超過 52.5 萬
 * 列出 @date-10@ 醫師 @Point-1@ > 525000 點的醫師名字
 */
public class J1h1Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public J1h1Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.disposalSource = new MonthSelectedDisposalSource(metricConfig);
        this.source.setExclude(Tro2);
        this.disposalSource.setExclude(Tro2);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro2Config config = new Tro2Config();
        Point1 point1 = new Point1(metricConfig, config, source, disposalSource).apply();
        return new BigDecimal(point1.getResult());
    }

}