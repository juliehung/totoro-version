package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3ByDaily;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每天申報件數
 */
public class DailyIc3Formula extends AbstractFormula<Map<LocalDate, BigDecimal>> {

    private final Source<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> source;

    public DailyIc3Formula(Collector collector,
                           Source<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public Map<LocalDate, BigDecimal> doCalculate(Collector collector) {
        Ic3ByDaily ic3ByDaily = new Ic3ByDaily(collector, source.outputKey()).apply();
        return ic3ByDaily.getResult().entrySet().stream().reduce(new HashMap<>(),
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
