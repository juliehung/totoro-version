package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyNewTreatmentPoint;
import static io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil.isExaminationCodeAtSalary;

/**
 * 診療費
 */
public class Point3 extends SingleSourceCalculator<Long> {

    public Point3(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Point3(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();
        MetaConfig config = getConfig();

        return nhiMetricRawVMList.stream()
            .filter(applyExcludeByVM(exclude))
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
