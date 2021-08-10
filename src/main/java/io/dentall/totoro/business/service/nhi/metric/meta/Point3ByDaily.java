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
        Exam1ByDaily exam1 = new Exam1ByDaily(collector, sourceName()).apply();
        Exam2ByDaily exam2 = new Exam2ByDaily(collector, sourceName()).apply();
        Exam3ByDaily exam3 = new Exam3ByDaily(collector, sourceName()).apply();
        Exam4ByDaily exam4 = new Exam4ByDaily(collector, sourceName()).apply();

        Map<LocalDate, Long> exam1Map = exam1.getResult();
        Map<LocalDate, Long> exam2Map = exam2.getResult();
        Map<LocalDate, Long> exam3Map = exam3.getResult();
        Map<LocalDate, Long> exam4Map = exam4.getResult();

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long points = sourceByDate.stream()
                    .map(NhiMetricRawVM::getTreatmentProcedureTotal)
                    .filter(Objects::nonNull)
                    .reduce(Long::sum)
                    .orElse(0L);
                points = points - exam1Map.get(date)- exam2Map.get(date)- exam3Map.get(date)- exam4Map.get(date);
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
