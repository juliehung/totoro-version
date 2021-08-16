package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper.INSTANCE;
import static java.util.Optional.ofNullable;

/**
 * 申報OD之乳牙顆數
 */
public class OdDeciduousTreatment extends SingleSourceCalculator<Long> {

    public OdDeciduousTreatment(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    public OdDeciduousTreatment(Collector collector, Exclude exclude, String sourceName) {
        super(collector, exclude, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<Map<Long, Map<String, List<OdDto>>>> source = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();
        return source.get(0).values().stream()
            .map(Map::values)
            .flatMap(Collection::stream)
            .flatMap(Collection::stream)
            .filter(dto -> ofNullable(exclude).map(exclude1 -> exclude1.test(INSTANCE.mapToExcludeDto(dto))).orElse(true))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.OdDeciduousTreatment;
    }
}
