package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
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
public class CourseCase extends SingleSourceMetaCalculator<Long> {

    public CourseCase(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public CourseCase(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());

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
