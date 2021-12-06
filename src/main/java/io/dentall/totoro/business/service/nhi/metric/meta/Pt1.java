package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculatePt;

/**
 * 看診人數/就醫人數 不重複病患數量
 */
public class Pt1 extends SingleSourceMetaCalculator<Long> {

    public Pt1(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Pt1(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());

        return source.stream().reduce(0L, calculatePt(), Long::sum);
    }

}