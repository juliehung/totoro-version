package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestPatientDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.meta.MetaType.*;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;
import static java.util.Map.Entry.comparingByValue;
import static java.util.Optional.ofNullable;

/**
 * 診療費 病患點數(最高者)
 */
public class HighestPoint1Patient extends SingleSourceCalculator<HighestPatientDto> {

    public HighestPoint1Patient(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public HighestPatientDto doCalculate(Collector collector) {
        Function<NhiMetricRawVM, Long> classifier = NhiMetricRawVM::getPatientId;
        Exam1ByClassifier exam1 = new Exam1ByClassifier(collector, HighestExam1Patient, sourceName(), classifier).apply();
        Exam2ByClassifier exam2 = new Exam2ByClassifier(collector, HighestExam2Patient, sourceName(), classifier).apply();
        Exam3ByClassifier exam3 = new Exam3ByClassifier(collector, HighestExam3Patient, sourceName(), classifier).apply();
        Exam4ByClassifier exam4 = new Exam4ByClassifier(collector, HighestExam4Patient, sourceName(), classifier).apply();
        Point3ByClassifier point3 = new Point3ByClassifier(collector, HighestPoint3ByPatient, sourceName(), classifier).apply();
        Point1 point1 = new Point1(collector, sourceName()).apply();

        Map<Long, Long> map = new HashMap<>(exam1.getResult().size());
        exam1.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam2.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        exam4.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));
        point3.getResult().forEach((keyId, point) -> map.compute(keyId, (key, value) -> ofNullable(value).orElse(0L) + point));

        Entry<Long, Long> entry = map.entrySet().stream().max(comparingByValue()).get();
        BigDecimal value;

        try {
            value = toPercentage(divide(entry.getValue(), point1.getResult()));
        } catch (ArithmeticException e) {
            value = ZERO;
        }

        return new HighestPatientDto(entry.getKey(), value);
    }

    @Override
    public MetaType metaType() {
        return MetaType.HighestPoint1ByPatient;
    }

}
