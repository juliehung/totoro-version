package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.valueOf;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(不含Xray)
 */
public class Exam3 extends AbstractCalculator {

    public final List<String> codes = Arrays.asList(
        "00305C", "00306C", "00307C", "00308C", "00309C", "00310C", "00311C", "00312C", "00313C", "00314C"
    );

    public Exam3(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        return nhiMetricRawVMList.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId))))
            .values().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(vm -> valueOf(vm.getExamPoint()))
            .reduce(Long::sum)
            .orElse(0L);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam3;
    }

}
