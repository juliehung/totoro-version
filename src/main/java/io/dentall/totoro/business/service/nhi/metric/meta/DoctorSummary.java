package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

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
public class DoctorSummary extends AbstractSummary<DoctorSummaryDto> {

    public DoctorSummary(Collector collector, Source<?, ?> source) {
        super(collector, source);
    }

    @Override
    public List<DoctorSummaryDto> doCalculate(Collector collector) {
        List<NhiMetricRawVM> source = collector.retrieveSource(source());
        Map<Long, List<NhiMetricRawVM>> sourceByDoctor = source.stream().collect(groupingBy(NhiMetricRawVM::getDoctorId));

        return sourceByDoctor.entrySet().stream()
            .reduce(new ArrayList<>(),
                (list, entry) -> {
                    List<NhiMetricRawVM> subSource = entry.getValue();
                    Map<Long, Optional<NhiMetricRawVM>> disposalList =
                        subSource.stream().collect(groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId))));

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

    @Override
    public MetaType metaType() {
        return MetaType.DoctorSummary;
    }

}
