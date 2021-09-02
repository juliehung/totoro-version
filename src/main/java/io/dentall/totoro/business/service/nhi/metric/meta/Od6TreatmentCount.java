package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

/**
 * @ OD-6@醫令數
 */
public class Od6TreatmentCount extends SingleSourceMetaCalculator<Long> {

    private static final List<String> codes = unmodifiableList(asList("89003C", "89010C", "89012C"));

    public Od6TreatmentCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od6TreatmentCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<OdDto> odDtoList = metricConfig.retrieveSource(source().key());
        return (long) odDtoList.stream()
            .filter(dto -> codes.contains(dto.getTreatmentProcedureCode()))
            // 因為資料是從Treatment層級，依牙齒切成多筆，所以需要先依 disposalId + code + treatmentSeq 做 group
            .collect(groupingBy(dto -> dto.getDisposalId() + dto.getTreatmentProcedureCode() + dto.getTreatmentSeq()))
            .size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od6TreatmentCount;
    }
}
