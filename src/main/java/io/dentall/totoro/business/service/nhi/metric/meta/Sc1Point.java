package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.code.NhiCodeHashSet;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesBySc1;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyNewTreatmentPoint;

/**
 * 洗牙醫令點數
 */
public class Sc1Point extends SingleSourceMetaCalculator<Long> {

    private static final NhiCodeHashSet codes = CodesBySc1;

    public Sc1Point(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Sc1Point(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        MetaConfig config = getConfig();

        return source.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .mapToLong(vm -> applyNewTreatmentPoint(vm, config, metricConfig.getHolidayMap()))
            .sum();
    }

}