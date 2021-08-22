package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculateExamByClassifier;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculateExamRegular;

public abstract class Exam<T> extends SingleSourceMetaCalculator<T> {

    public Exam(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    protected Long doCalculateRegular(MetricConfig metricConfig, List<String> codes) {
        List<NhiMetricRawVM> source = metricConfig.retrieveSource(source().key());
        return calculateExamRegular(source, codes, getConfig());
    }

    protected Map<Long, Long> doCalculateByClassifier(MetricConfig metricConfig, List<String> codes, Function<NhiMetricRawVM, Long> classifier) {
        List<NhiMetricRawVM> source = metricConfig.retrieveSource(source().key());
        return calculateExamByClassifier(source, codes, classifier, getConfig());
    }

    protected Map<LocalDate, Long> doCalculateByDaily(MetricConfig metricConfig, List<String> codes) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = metricConfig.retrieveSource(source().key());

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
