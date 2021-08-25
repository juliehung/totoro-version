package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

/**
 * @ OD-4@醫令數
 */
public class Od4TreatmentCount extends SingleSourceMetaCalculator<Long> {

    private static final List<String> codes = unmodifiableList(asList("89001C", "89004C", "89008C", "89011C"));

    public Od4TreatmentCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od4TreatmentCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<OdDto> odDtoList = metricConfig.retrieveSource(source().key());
        Exclude exclude = getExclude();
        return (long) odDtoList.stream()
            .filter(dto -> codes.contains(dto.getCode()))
            .filter(applyExcludeByDto(exclude))
            // 因為資料是從Treatment層級，依牙齒切成多筆，所以需要先依 disposalId + code + treatmentSeq 做 group
            .collect(groupingBy(dto -> dto.getDisposalId() + dto.getCode() + dto.getTreatmentSeq()))
            .size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od4TreatmentCount;
    }
}
