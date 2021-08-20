package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;

/**
 * 申報OD之恆牙顆數
 */
public class OdPermanentTreatment extends SingleSourceCalculator<Long> {

    public OdPermanentTreatment(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public OdPermanentTreatment(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<Map<Long, Map<String, List<OdDto>>>> source = collector.retrieveSource(source());
        Exclude exclude = getExclude();
        return source.get(0).values().stream()
            .map(Map::values)
            .flatMap(Collection::stream)
            .flatMap(Collection::stream)
            .filter(applyExcludeByDto(exclude))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.OdPermanentTreatment;
    }
}
