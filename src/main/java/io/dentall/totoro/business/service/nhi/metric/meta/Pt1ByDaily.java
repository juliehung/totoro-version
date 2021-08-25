package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculatePt;

/**
 * 每天看診人數/就醫人數 不重複病患數量
 */
public class Pt1ByDaily extends SingleSourceMetaCalculator<Map<LocalDate, Long>> {

    public Pt1ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = metricConfig.retrieveSource(source().key());
        Exclude exclude = getExclude();

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long value = sourceByDate.stream()
                    .filter(applyExcludeByVM(exclude))
                    .reduce(0L, calculatePt(), Long::sum);
                map.put(date, value);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

    @Override
    public MetaType metaType() {
        return MetaType.Pt1ByDaily;
    }
}
