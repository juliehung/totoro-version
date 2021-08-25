package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 每天申報件數
 * <p>
 * 有卡號＋異常代碼之處置單數量總和
 * 同一療程在當月完成以一件計算，跨月完成則以二件計算
 */
public class Ic3ByDaily extends SingleSourceMetaCalculator<Map<LocalDate, Long>> {

    public Ic3ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = metricConfig.retrieveSource(source().key());

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long value = (long) sourceByDate.stream()
                    .filter(vm -> vm.getDisposalDate() != null && isNotBlank(vm.getCardNumber()))
                    .map(vm -> vm.getDisposalDate() + vm.getCardNumber())
                    .collect(groupingBy(key -> key))
                    .keySet().size();
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
        return MetaType.Ic3ByDaily;
    }
}
