package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Ic3ByDaily;
import io.dentall.totoro.business.service.nhi.metric.source.DailyByMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Comparator.naturalOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toList;

/**
 * 每天申報件數
 */
public class DailyIc3Formula extends AbstractFormula<List<Entry<LocalDate, BigDecimal>>> {

    private final Source<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> source;

    public DailyIc3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new DailyByMonthSelectedSource(metricConfig);
    }

    @Override
    public List<Entry<LocalDate, BigDecimal>> doCalculate(MetricConfig metricConfig) {
        Ic3ByDaily ic3ByDaily = new Ic3ByDaily(metricConfig, source).apply();
        Map<LocalDate, BigDecimal> result = ic3ByDaily.getResult().entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                map.put(entry.getKey(), new BigDecimal(entry.getValue()));
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });

        return result.entrySet().stream().sorted(comparingByKey(naturalOrder())).collect(toList());
    }
}
