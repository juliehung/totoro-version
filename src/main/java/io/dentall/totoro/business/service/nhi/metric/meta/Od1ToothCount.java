package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;

/**
 * @ OD-1@齒數
 */
public class Od1ToothCount extends SingleSourceCalculator<Long> {

    public Od1ToothCount(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Od1ToothCount(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(source());
        Exclude exclude = getExclude();
        return odDtoList.stream().filter(applyExcludeByDto(exclude)).count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1ToothCount;
    }
}
