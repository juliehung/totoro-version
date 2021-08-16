package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.Long.parseLong;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ExamHelper {

    private ExamHelper() {
    }

    public static final List<String> codesByExam1 = unmodifiableList(asList(
        "00121C", "00122C", "00123C", "00124C", "00125C", "00126C", "00128C", "00129C", "00130C", "00133C", "00134C", "00301C", "00302C", "00303C", "00304C"
    ));

    public static final List<String> codesByExam2 = unmodifiableList(asList(
        "01271C", "01272C", "01273C"
    ));

    public static final List<String> codesByExam3 = unmodifiableList(asList(
        "00305C", "00306C", "00307C", "00308C", "00309C", "00310C", "00311C", "00312C", "00313C", "00314C"
    ));

    public static final List<String> codesByExam4 = unmodifiableList(asList(
        "00315C", "00316C", "00317C"
    ));

    private static Long doCalculate(Stream<Optional<NhiMetricRawVM>> stream, boolean use00121CPoint) {
        return stream.filter(Optional::isPresent)
            .map(Optional::get)
            .map(vm -> use00121CPoint ? 230L : parseLong(vm.getExamPoint()))
            .reduce(Long::sum)
            .orElse(0L);
    }

    public static Long calculate(List<NhiMetricRawVM> source, List<String> codes, boolean use00121CPoint) {
        Stream<Optional<NhiMetricRawVM>> stream = source.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId))))
            .values().stream();
        return doCalculate(stream, use00121CPoint);
    }

    public static Map<Long, Long> calculateByClassifier(List<NhiMetricRawVM> source, List<String> codes, Function<NhiMetricRawVM, Long> classifier, boolean use00121CPoint) {
        return source.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(classifier, groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId)))))
            .entrySet().stream()
            .reduce(new HashMap<>(),
                (map, entry) -> {
                    long keyId = entry.getKey();

                    map.compute(keyId, (key, point) -> {
                        Map<Long, Optional<NhiMetricRawVM>> subMap = entry.getValue();
                        Stream<Optional<NhiMetricRawVM>> stream = subMap.values().stream();
                        Long examPoint = doCalculate(stream, use00121CPoint);
                        return ofNullable(point).orElse(0L) + examPoint;
                    });

                    return map;
                },
                (accMap, map) -> {
                    accMap.putAll(map);
                    return accMap;
                });
    }
}
