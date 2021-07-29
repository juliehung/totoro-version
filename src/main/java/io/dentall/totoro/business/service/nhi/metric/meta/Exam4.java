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
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray)
 */
public class Exam4 extends AbstractCalculator {

    public final List<String> codes = Arrays.asList(
        "00315C", "00316C", "00317C"
    );

    public Exam4(String sourceName) {
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
        return MetaType.Exam4;
    }

}
