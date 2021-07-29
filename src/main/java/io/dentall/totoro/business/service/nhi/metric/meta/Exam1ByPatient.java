package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.web.rest.vm.NhiIndexEndoVM;

import java.util.*;

import static io.dentall.totoro.business.service.nhi.util.ToothUtil.getToothCount;
import static java.lang.Long.valueOf;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 一般牙科門診診察費(不含Xray)
 */
public class Exam1ByPatient extends Exam1 {

    public Exam1ByPatient(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        Map<Long, Long> examPointByPatient = nhiMetricRawVMList.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(NhiMetricRawVM::getPatientId, groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId)))))
            .entrySet().stream()
            .reduce(new HashMap<Long, Long>(), (map, entry) -> {
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

        return null;


    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam1Patient;
    }

}
