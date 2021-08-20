package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

/**
 * @ OD-5@醫令數
 */
public class Od5TreatmentCount extends SingleSourceCalculator<Long> {

    private static final List<String> codes = unmodifiableList(asList("89002C", "89005C", "89009C"));

    public Od5TreatmentCount(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Od5TreatmentCount(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(source());
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
        return MetaType.Od5TreatmentCount;
    }
}
