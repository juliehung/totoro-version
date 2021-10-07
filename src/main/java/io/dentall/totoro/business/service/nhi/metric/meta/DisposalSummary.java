package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 *
 */
public class DisposalSummary extends AbstractMetaSummary<DisposalSummaryDto> {

    public DisposalSummary(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, source);
    }

    @Override
    public List<DisposalSummaryDto> doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        Map<Long, List<MetricTooth>> sourceByDisposal = source.stream().collect(groupingBy(MetricTooth::getDisposalId));

        return sourceByDisposal.entrySet().stream()
            .reduce(new ArrayList<DisposalSummaryDto>(),
                (list, entry) -> {
                    List<MetricTooth> subSource = entry.getValue();
                    Map<Long, Optional<MetricTooth>> disposalList =
                        subSource.stream().collect(groupingBy(MetricTooth::getDisposalId, maxBy(comparing(MetricTooth::getDisposalId))));

                    DisposalSummaryDto result = new DisposalSummaryDto();
                    summaryByTreatment(result, subSource);

                    disposalList.values().stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(vm -> {
                                result.setDisposalId(vm.getDisposalId());
                                result.setDisposalDate(vm.getDisposalDate());
                                result.setPatientId(vm.getPatientId());
                                result.setPatientName(vm.getPatientName());
                                summaryByDisposal(result, vm);
                            }
                        );

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
