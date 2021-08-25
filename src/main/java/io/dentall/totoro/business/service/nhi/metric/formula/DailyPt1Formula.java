package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Pt1ByDaily;
import io.dentall.totoro.business.service.nhi.metric.source.DailyByMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每天看診人數/就醫人數 不重複病患數量
 */
public class DailyPt1Formula extends AbstractFormula<Map<LocalDate, BigDecimal>> {

    private final Source<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> source;

    public DailyPt1Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new DailyByMonthSelectedSource(metricConfig);
    }

    @Override
    public Map<LocalDate, BigDecimal> doCalculate(MetricConfig metricConfig) {
        Pt1ByDaily pt1ByDaily = new Pt1ByDaily(metricConfig, source).apply();
        return pt1ByDaily.getResult().entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                map.put(entry.getKey(), new BigDecimal(entry.getValue()));
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }
}
