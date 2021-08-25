package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;

/**
 * @ OD-1@齒數
 */
public class Od1ToothCount extends SingleSourceMetaCalculator<Long> {

    public Od1ToothCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od1ToothCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<OdDto> odDtoList = metricConfig.retrieveSource(source().key());
        Exclude exclude = getExclude();
        return odDtoList.stream().filter(applyExcludeByDto(exclude)).count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1ToothCount;
    }
}
