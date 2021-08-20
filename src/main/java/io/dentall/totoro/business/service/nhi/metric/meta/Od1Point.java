package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyNewTreatmentPoint;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * @ OD-1@總點數
 */
public class Od1Point extends SingleSourceCalculator<Long> {

    public Od1Point(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Od1Point(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(sourceName());
        MetaConfig config = getConfig();
        Exclude exclude = getExclude();

        return odDtoList.stream().filter(applyExcludeByDto(exclude))
            // 因為資料是從Treatment層級，依牙齒切成多筆，所以需要先依 disposalId + code + treatmentSeq 做 group
            .collect(groupingBy(dto -> dto.getDisposalId() + dto.getCode() + dto.getTreatmentSeq(), maxBy(comparing(OdDto::getDisposalId))))
            .values().stream().filter(Optional::isPresent).map(Optional::get)
            .mapToLong(dto -> applyNewTreatmentPoint(dto, config))
            .sum();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1Point;
    }
}
