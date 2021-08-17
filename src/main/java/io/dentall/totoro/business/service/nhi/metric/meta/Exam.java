package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.calculateByClassifier;

public abstract class Exam<T> extends SingleSourceCalculator<T> {

    public Exam(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    protected Long doCalculateRegular(Collector collector, List<String> codes) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        boolean use00121CPoint = getConfig().isUse00121CPoint();
        return ExamHelper.calculate(source, codes, use00121CPoint);
    }

    protected Map<Long, Long> doCalculateByClassifier(Collector collector, List<String> codes, Function<NhiMetricRawVM, Long> classifier) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        boolean use00121CPoint = getConfig().isUse00121CPoint();
        return calculateByClassifier(source, codes, classifier, use00121CPoint);
    }

    protected Map<LocalDate, Long> doCalculateByDaily(Collector collector, List<String> codes) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = collector.retrieveSource(sourceName());
        boolean use00121CPoint = getConfig().isUse00121CPoint();

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
    public String extraKey() {
        return getConfig().isUse00121CPoint() ? "use00121CPoint" : null;
    }
}
