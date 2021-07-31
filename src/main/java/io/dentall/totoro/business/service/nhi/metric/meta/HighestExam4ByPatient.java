package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Long.valueOf;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray) 病患點數(最高者)
 */
public class HighestExam4ByPatient extends Exam4 {

    public HighestExam4ByPatient(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        Map<Long, Long> examPointByPatient = nhiMetricRawVMList.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(NhiMetricRawVM::getPatientId, groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId)))))
            .entrySet().stream()
            .reduce(new HashMap<>(), (map, entry) -> {
                long patientId = entry.getKey();

                map.compute(patientId, (key, point) -> {
                    Map<Long, Optional<NhiMetricRawVM>> subMap = entry.getValue();
                    Long examPoint = subMap.values().stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(vm -> valueOf(vm.getExamPoint()))
                        .reduce(Long::sum)
                        .orElse(0L);
                    return ofNullable(point).orElse(0L) + examPoint;
                });

                return map;
            }, (accMap, map) -> {
                accMap.putAll(map);
                return accMap;
            });

        return examPointByPatient.values().stream().max(naturalOrder()).orElse(0L);
    }

    @Override
    public MetaType metaType() {
        return MetaType.HighestExam4Patient;
    }

}
