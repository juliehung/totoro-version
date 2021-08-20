package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculateOdPt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * @ OD-1@@PT-1@
 */
public class Od1Pt1 extends SingleSourceCalculator<Long> {

    public Od1Pt1(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(sourceName());

        return odDtoList.stream()
            // 因為資料是從Treatment層級，依牙齒切成多筆，所以需要先依 disposalId + code + treatmentSeq 做 group
            .collect(groupingBy(dto -> dto.getDisposalId() + dto.getCode() + dto.getTreatmentSeq(), maxBy(Comparator.comparing(OdDto::getDisposalId))))
            .values().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce(0L, calculateOdPt(), Long::sum);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1Pt1;
    }
}
