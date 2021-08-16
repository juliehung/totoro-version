package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam1;

public abstract class Exam<T> extends SingleSourceCalculator<T> {

    protected boolean use00121CPoint = false;

    public Exam(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    public Exam(Collector collector, String sourceName, boolean use00121CPoint) {
        super(collector, sourceName);
        this.use00121CPoint = use00121CPoint;
    }

    protected Map<LocalDate, Long> doCalculateByDaily(Collector collector, List<String> codes) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = collector.retrieveSource(sourceName());

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long points = ExamHelper.calculate(sourceByDate, codes, use00121CPoint);
                map.put(date, points);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

    @Override
    public String extractKey() {
        return use00121CPoint ? "use00121CPoint" : null;
    }
}
