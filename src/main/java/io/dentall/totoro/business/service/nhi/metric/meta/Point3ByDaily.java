package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam3;

/**
 * 診療費
 */
public class Point3ByDaily extends SingleSourceCalculator<Map<LocalDate, Long>> {

    public Point3ByDaily(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(Collector collector) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = collector.retrieveSource(sourceName());

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long points = sourceByDate.stream()
                    .map(NhiMetricRawVM::getTreatmentProcedureTotal)
                    .filter(Objects::nonNull)
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
        return MetaType.Point3ByDaily;
    }

}
