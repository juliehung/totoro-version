package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculateExamByClassifier;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculateExamRegular;

public abstract class Exam<T> extends SingleSourceCalculator<T> {

    public Exam(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    protected Long doCalculateRegular(Collector collector, List<String> codes) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        return calculateExamRegular(source, codes, getConfig());
    }

    protected Map<Long, Long> doCalculateByClassifier(Collector collector, List<String> codes, Function<NhiMetricRawVM, Long> classifier) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        return calculateExamByClassifier(source, codes, classifier, getConfig());
    }

    protected Map<LocalDate, Long> doCalculateByDaily(Collector collector, List<String> codes) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = collector.retrieveSource(sourceName());

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long points = calculateExamRegular(sourceByDate, codes, getConfig());
                map.put(date, points);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

}
