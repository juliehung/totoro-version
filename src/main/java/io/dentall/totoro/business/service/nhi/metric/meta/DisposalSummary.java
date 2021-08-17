package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

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
public class DisposalSummary extends AbstractSummary<DisposalSummaryDto> {

    public DisposalSummary(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public List<DisposalSummaryDto> doCalculate(Collector collector) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        Map<Long, List<NhiMetricRawVM>> sourceByDisposal = source.stream().collect(groupingBy(NhiMetricRawVM::getDisposalId));

        return sourceByDisposal.entrySet().stream()
            .reduce(new ArrayList<DisposalSummaryDto>(),
                (list, entry) -> {
                    List<NhiMetricRawVM> subSource = entry.getValue();
                    Map<Long, Optional<NhiMetricRawVM>> disposalList =
                        subSource.stream().collect(groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId))));

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

    @Override
    public MetaType metaType() {
        return MetaType.DisposalSummary;
    }

}
