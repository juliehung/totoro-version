package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyNewTreatmentPoint;
import static io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil.isExaminationCodeAtSalary;

/**
 * 診療費
 */
public class Point3 extends SingleSourceMetaCalculator<Long> {

    public Point3(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Point3(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());
        MetaConfig config = getConfig();

        return nhiMetricRawVMList.stream()
            .filter(vm -> !isExaminationCodeAtSalary(vm.getTreatmentProcedureCode()))
            .map(vm -> applyNewTreatmentPoint(vm, config))
            .filter(Objects::nonNull)
            .reduce(Long::sum)
            .orElse(0L);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point3;
    }

}
