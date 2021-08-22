package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * 總人數(有診察費)
 */
public class Od1Pt2 extends SingleSourceMetaCalculator<Long> {

    public Od1Pt2(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od1Pt2(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<OdDto> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());
        Exclude exclude = getExclude();

        return nhiMetricRawVMList.stream()
            .filter(applyExcludeByDto(exclude))
            .filter(vm -> !isPreventionCardNumber(vm.getCardNumber()))
            // 因為資料是從Treatment層級，依牙齒切成多筆，所以需要先依 disposalId + code + treatmentSeq 做 group
            .collect(groupingBy(dto -> dto.getDisposalId() + dto.getCode() + dto.getTreatmentSeq(), maxBy(Comparator.comparing(OdDto::getDisposalId))))
            .values().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce(0L, calculateOdPt(), Long::sum);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1Pt2;
    }
}
