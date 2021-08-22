package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Point2ByDaily;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.DailyByMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 健保每日申請點數
 */
public class DailyPointsFormula extends AbstractFormula<Map<LocalDate, BigDecimal>> {

    private final Source<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> source;

    public DailyPointsFormula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new DailyByMonthSelectedSource(metricConfig);
    }

    @Override
    public Map<LocalDate, BigDecimal> doCalculate(MetricConfig metricConfig) {
        Point2ByDaily point2 = new Point2ByDaily(metricConfig, source).apply();
        return point2.getResult().entrySet().stream().reduce(new HashMap<>(),
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
