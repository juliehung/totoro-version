package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestPatientDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
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
 * 診療費 病患點數(最高者)
 */
public class HighestPoint1Patient extends AbstractMetaCalculator<HighestPatientDto> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public HighestPoint1Patient(
        MetricConfig metricConfig,
        Source<MetricTooth, MetricTooth> source,
        Source<MetricDisposal, MetricDisposal> disposalSource) {
        this(metricConfig, null, source, disposalSource);
    }

    public HighestPoint1Patient(
        MetricConfig metricConfig,
        MetaConfig metaConfig,
        Source<MetricTooth, MetricTooth> source,
        Source<MetricDisposal, MetricDisposal> disposalSource) {
        super(metricConfig, metaConfig, new Source[]{source, disposalSource});
        this.source = source;
        this.disposalSource = disposalSource;
    }

    @Override
    public HighestPatientDto doCalculate(MetricConfig metricConfig) {
        Function<MetricDisposal, Long> classifier = MetricDisposal::getPatientId;
        Exam1ByClassifier exam1 = new Exam1ByClassifier(metricConfig, disposalSource, classifier).apply();
        Exam2ByClassifier exam2 = new Exam2ByClassifier(metricConfig, disposalSource, classifier).apply();
        Exam3ByClassifier exam3 = new Exam3ByClassifier(metricConfig, disposalSource, classifier).apply();
        Exam4ByClassifier exam4 = new Exam4ByClassifier(metricConfig, disposalSource, classifier).apply();
        Point3ByClassifier point3 = new Point3ByClassifier(metricConfig, source, MetricTooth::getPatientId).apply();
        Point1 point1 = new Point1(metricConfig, source, disposalSource).apply();

        Map<Long, Long> map = new HashMap<>(exam1.getResult().size());
        exam1.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam2.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam4.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        point3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));

        Optional<Entry<Long, Long>> optional = map.entrySet().stream().max(comparingByValue());

        if (!optional.isPresent()) {
            return new HighestPatientDto(null, null);
        }

        Entry<Long, Long> entry = optional.get();
        BigDecimal value;

        try {
            value = toPercentage(entry.getValue(), point1.getResult());
        } catch (ArithmeticException e) {
            value = ZERO;
        }

        return new HighestPatientDto(entry.getKey(), value);
    }

}
