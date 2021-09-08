package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 申報OD之恆牙顆數
 */
public class OdPermanentToothCount extends SingleSourceMetaCalculator<Long> {

    public OdPermanentToothCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public OdPermanentToothCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<Map<Long, Map<String, List<MetricTooth>>>> source = metricConfig.retrieveSource(source().key());
        return source.get(0).values().stream()
            .map(Map::values)
            .flatMap(Collection::stream)
            .flatMap(Collection::stream)
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.OdPermanentToothCount;
    }
}
