package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 *
 */
public class DoctorSummary extends AbstractMetaSummary<DoctorSummaryDto> {

    public DoctorSummary(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, source);
    }

    @Override
    public List<DoctorSummaryDto> doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        Map<Long, List<MetricTooth>> sourceByDoctor = source.stream().collect(groupingBy(MetricTooth::getDoctorId));

        return sourceByDoctor.entrySet().stream()
            .reduce(new ArrayList<>(),
                (list, entry) -> {
                    List<MetricTooth> subSource = entry.getValue();
                    Map<Long, Optional<MetricTooth>> disposalList =
                        subSource.stream().collect(groupingBy(MetricTooth::getDisposalId, maxBy(comparing(MetricTooth::getDisposalId))));

                    DoctorSummaryDto result = new DoctorSummaryDto();
                    summaryByTreatment(result, subSource);

                    disposalList.values().stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(vm -> {
                                result.setDoctorId(vm.getDoctorId());
                                result.setDoctorName(vm.getDoctorName());
                                result.setTotalDisposal(result.getTotalDisposal() + 1);
                                summaryByDisposal(result, vm);
                            }
                        );

                    list.add(result);
                    return list;
                },
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
    }

}
