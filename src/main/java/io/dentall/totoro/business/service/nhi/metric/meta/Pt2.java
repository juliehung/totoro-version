package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculatePt;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.isPreventionCardNumber;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());

        return source.stream()
            .filter(vm -> !isPreventionCardNumber(vm.getCardNumber()))
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> !"0".equals(vm.getExamCode()))
            .reduce(0L, calculatePt(), Long::sum);
    }

}
