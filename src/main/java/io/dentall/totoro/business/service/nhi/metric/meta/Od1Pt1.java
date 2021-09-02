package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculatePt;

/**
 * @ OD-1@@PT-1@
 */
public class Od1Pt1 extends SingleSourceMetaCalculator<Long> {

    public Od1Pt1(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od1Pt1(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<OdDto> odDtoList = metricConfig.retrieveSource(source().key());

        return odDtoList.stream()
            .reduce(0L, calculatePt(), Long::sum);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1Pt1;
    }
}
