package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculatePt;

/**
 * 申報件數
 * <p>
 * 有卡號＋異常代碼之處置單數量總和
 * 同一療程在當月完成以一件計算，跨月完成則以二件計算
 */
public class Ic3 extends SingleSourceMetaCalculator<Long> {

    public Ic3(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Ic3(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());

        return source.stream().reduce(0L, calculatePt(), Long::sum);
    }

}
