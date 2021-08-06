package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestDoctor;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.meta.MetaType.*;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;
import static java.util.Map.Entry.comparingByValue;
import static java.util.Optional.ofNullable;

/**
 * 診療費 醫師點數(最高者)
 */
public class HighestPoint1Doctor extends SingleSourceCalculator<HighestDoctor> {

    public HighestPoint1Doctor(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public HighestDoctor doCalculate(Collector collector) {
        Function<NhiMetricRawVM, Long> classifier = NhiMetricRawVM::getDoctorId;
        Exam1ByClassifier exam1 = new Exam1ByClassifier(collector, HighestExam1Doctor, sourceName(), classifier).apply();
        Exam2ByClassifier exam2 = new Exam2ByClassifier(collector, HighestExam2Doctor, sourceName(), classifier).apply();
        Exam3ByClassifier exam3 = new Exam3ByClassifier(collector, HighestExam3Doctor, sourceName(), classifier).apply();
        Exam4ByClassifier exam4 = new Exam4ByClassifier(collector, HighestExam4Doctor, sourceName(), classifier).apply();
        Point3ByClassifier point3 = new Point3ByClassifier(collector, HighestPoint3ByDoctor, sourceName(), classifier).apply();
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
            value = divide(entry.getValue(), point1.getResult());
        } catch (ArithmeticException e) {
            value = ZERO;
        }

        return new HighestDoctor(entry.getKey(), value);
    }

    @Override
    public MetaType metaType() {
        return MetaType.HighestPoint1ByDoctor;
    }

}
