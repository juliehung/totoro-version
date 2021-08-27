package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculatePt;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.isPreventionCardNumber;

/**
 * 總人數(有診察費)
 */
public class Pt2 extends SingleSourceMetaCalculator<Long> {

    public Pt2(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Pt2(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());

        return nhiMetricRawVMList.stream()
            .filter(vm -> !isPreventionCardNumber(vm.getCardNumber()))
            .reduce(0L, calculatePt(), Long::sum);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Pt2;
    }
}
