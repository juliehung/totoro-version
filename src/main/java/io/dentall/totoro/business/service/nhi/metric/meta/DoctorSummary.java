package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

/**
 *
 */
public class DoctorSummary extends AbstractMetaSummary<DoctorSummaryDto> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public DoctorSummary(MetricConfig metricConfig, Source<MetricTooth, MetricTooth> source, Source<MetricDisposal, MetricDisposal> disposalSource) {
        super(metricConfig, null, new Source[]{source, disposalSource});
        this.source = source;
        this.disposalSource = disposalSource;
    }

    @Override
    public List<DoctorSummaryDto> doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(this.source.key());
        List<MetricDisposal> disposalSource = metricConfig.retrieveSource(this.disposalSource.key());
        Map<Long, List<MetricTooth>> sourceByDoctor = source.stream().collect(groupingBy(MetricTooth::getDoctorId));
        Map<Long, List<MetricDisposal>> disposalSourceByDoctor = disposalSource.stream().collect(groupingBy(MetricDisposal::getDoctorId));

        return disposalSourceByDoctor.entrySet().stream()
            .reduce(new ArrayList<>(),
                (list, entry) -> {
                    List<MetricDisposal> disposalList = entry.getValue();
                    List<MetricTooth> toothList = Optional.ofNullable(sourceByDoctor.get(entry.getKey())).orElse(emptyList());

                    DoctorSummaryDto result = new DoctorSummaryDto();
                    summaryByTreatment(result, toothList);

                    disposalList.forEach(vm -> {
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
