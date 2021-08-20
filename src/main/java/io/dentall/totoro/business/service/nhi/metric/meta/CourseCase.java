package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.NhiCategory.AA;
import static io.dentall.totoro.business.service.nhi.NhiCategory.AB;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;

/**
 * 就醫類別為 AA, AB 的處置單數量總和
 */
public class CourseCase extends SingleSourceCalculator<Long> {

    public CourseCase(Collector collector, Source<?, ?> source) {
        super(collector, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(source());

        return nhiMetricRawVMList.stream()
            .filter(vm -> AA.getValue().equals(vm.getNhiCategory()) || AB.getValue().equals(vm.getNhiCategory()))
            .collect(Collectors.groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId))))
            .values().stream()
            .filter(Optional::isPresent)
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.CourseCase;
    }
}
