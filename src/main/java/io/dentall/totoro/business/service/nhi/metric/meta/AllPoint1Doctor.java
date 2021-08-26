package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.meta.MetaType.*;
import static java.util.Optional.ofNullable;

/**
 * 所有醫師Point1
 */
public class AllPoint1Doctor extends SingleSourceMetaCalculator<Map<Long, Long>> {

    public AllPoint1Doctor(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public AllPoint1Doctor(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Map<Long, Long> doCalculate(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Function<NhiMetricRawVM, Long> classifier = NhiMetricRawVM::getDoctorId;
        Exam1ByClassifier exam1 = new Exam1ByClassifier(metricConfig, config, HighestExam1Doctor, source(), classifier).apply();
        Exam2ByClassifier exam2 = new Exam2ByClassifier(metricConfig, config, HighestExam2Doctor, source(), classifier).apply();
        Exam3ByClassifier exam3 = new Exam3ByClassifier(metricConfig, config, HighestExam3Doctor, source(), classifier).apply();
        Exam4ByClassifier exam4 = new Exam4ByClassifier(metricConfig, config, HighestExam4Doctor, source(), classifier).apply();
        Point3ByClassifier point3 = new Point3ByClassifier(metricConfig, config, HighestPoint3ByDoctor, source(), classifier).apply();

        Map<Long, Long> map = new HashMap<>(exam1.getResult().size());
        exam1.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam2.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam4.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        point3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));

        return map;
    }

    @Override
    public MetaType metaType() {
        return MetaType.AllPoint1Doctor;
    }

}
