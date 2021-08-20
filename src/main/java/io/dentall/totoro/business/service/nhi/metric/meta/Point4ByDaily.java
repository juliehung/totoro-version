package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 部分負擔點數
 */
public class Point4ByDaily extends SingleSourceCalculator<Map<LocalDate, Long>> {

    public Point4ByDaily(Collector collector, Source<?, ?> source) {
        super(collector, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(Collector collector) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = collector.retrieveSource(source());

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long points = sourceByDate.stream()
                    .map(NhiMetricRawVM::getPartialBurden)
                    .filter(Objects::nonNull)
                    .map(Long::valueOf)
                    .reduce(Long::sum)
                    .orElse(0L);
                map.put(date, points);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point4ByDaily;
    }
}
