package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;

/**
 * 恆牙拔牙醫令齒數 + 乳牙拔牙醫令齒數
 */
public class ExtToothCount extends SingleSourceMetaCalculator<Long> {

    public ExtToothCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public ExtToothCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
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
        return MetaType.ExtToothCount;
    }
}
