package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 *
 */
public class DisposalSummary extends AbstractMetaSummary<DisposalSummaryDto> {

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public DisposalSummary(MetricConfig metricConfig, Source<MetricDisposal, MetricDisposal> disposalSource) {
        super(metricConfig, null, new Source[]{disposalSource});
        this.disposalSource = disposalSource;
    }

    @Override
    public List<DisposalSummaryDto> doCalculate(MetricConfig metricConfig) {
        List<MetricDisposal> source = metricConfig.retrieveSource(disposalSource.key());

        return source.stream()
            .reduce(new ArrayList<DisposalSummaryDto>(),
                (list, disposal) -> {
                    DisposalSummaryDto result = new DisposalSummaryDto();
                    result.setDisposalId(disposal.getDisposalId());
                    result.setDisposalDate(disposal.getDisposalDate());
                    result.setPatientId(disposal.getPatientId());
                    result.setPatientName(disposal.getPatientName());
                    summaryByTreatment(result, disposal.getToothList());
                    summaryByDisposal(result, disposal);
                    list.add(result);
                    return list;
                },
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                })
            .stream()
            .sorted(comparing(dto -> dto.getDisposalDate().toString() + dto.getPatientId()))
            .collect(Collectors.toList());
    }

}
