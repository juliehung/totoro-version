package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.*;

public abstract class Exam<T> extends SingleSourceMetaCalculator<T> {

    public Exam(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    private void checkPoint6(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Class<?> clz = this.getClass();

        if (config.isIncludePoint6By12MPoints() &&
            (clz.isAssignableFrom(Exam2.class) ||
                clz.isAssignableFrom(Exam4.class) ||
                clz.isAssignableFrom(Exam2ByClassifier.class) ||
                clz.isAssignableFrom(Exam4ByClassifier.class) ||
                clz.isAssignableFrom(Exam2ByDaily.class) ||
                clz.isAssignableFrom(Exam4ByDaily.class))) {

            Point6 point6 = new Point6(metricConfig, source()).apply();
            if (point6.getResult() > 12000000) {
                // 取消使用00121C計算點數，意即將差額點數納入計算
                config.setUse00121CPoint(false);
            }
        }
    }

    protected Long doCalculatePurge(MetricConfig metricConfig, List<String> codes) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        return calculatePurge(source, codes, getConfig(), metricConfig.getHolidayMap());
    }

    protected Long doCalculateRegular(MetricConfig metricConfig, List<String> codes) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        checkPoint6(metricConfig);
        return calculateExamRegular(source, codes, getConfig(), metricConfig.getHolidayMap());
    }

    protected Long doCalculateDifference(MetricConfig metricConfig, List<String> codes) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        return calculateExamDifference(source, codes);
    }

    protected Map<Long, Long> doCalculateByClassifier(MetricConfig metricConfig, List<String> codes, Function<MetricTooth, Long> classifier) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        checkPoint6(metricConfig);
        return calculateExamByClassifier(source, codes, classifier, getConfig(), metricConfig.getHolidayMap());
    }

    protected Map<LocalDate, Long> doCalculateByDaily(MetricConfig metricConfig, List<String> codes) {
        List<Map<LocalDate, List<MetricTooth>>> source = metricConfig.retrieveSource(source().key());
        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<MetricTooth> sourceByDate = entry.getValue();
                Long points = calculateExamRegular(sourceByDate, codes, getConfig(), metricConfig.getHolidayMap());
                map.put(date, points);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

}
