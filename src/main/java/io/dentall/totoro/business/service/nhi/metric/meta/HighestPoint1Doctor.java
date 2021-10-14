package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestDoctorDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;
import static java.util.Map.Entry.comparingByValue;
import static java.util.Optional.ofNullable;

/**
 * 診療費 醫師點數(最高者)
 */
public class HighestPoint1Doctor extends SingleSourceMetaCalculator<HighestDoctorDto> {

    public HighestPoint1Doctor(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public HighestPoint1Doctor(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public HighestDoctorDto doCalculate(MetricConfig metricConfig) {
        Function<MetricTooth, Long> classifier = MetricTooth::getDoctorId;
        Exam1ByClassifier exam1 = new Exam1ByClassifier(metricConfig, source(), classifier).apply();
        Exam2ByClassifier exam2 = new Exam2ByClassifier(metricConfig, source(), classifier).apply();
        Exam3ByClassifier exam3 = new Exam3ByClassifier(metricConfig, source(), classifier).apply();
        Exam4ByClassifier exam4 = new Exam4ByClassifier(metricConfig, source(), classifier).apply();
        Point3ByClassifier point3 = new Point3ByClassifier(metricConfig, source(), classifier).apply();
        Point1 point1 = new Point1(metricConfig, source()).apply();

        Map<Long, Long> map = new HashMap<>(exam1.getResult().size());
        exam1.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam2.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam4.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        point3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));

        Optional<Entry<Long, Long>> optional = map.entrySet().stream().max(comparingByValue());

        if (!optional.isPresent()) {
            return new HighestDoctorDto(null, null);
        }

        Entry<Long, Long> entry = optional.get();
        BigDecimal value;

        try {
            value = toPercentage(entry.getValue(), point1.getResult());
        } catch (ArithmeticException e) {
            value = ZERO;
        }

        return new HighestDoctorDto(entry.getKey(), value);
    }

}
