package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdQuarterSource extends OdSource {

    public OdQuarterSource(MetricConfig metricConfig) {
        super(new QuarterSource(metricConfig));
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> source) {
        return source
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> isNotBlank(vm.getTooth()))
            .collect(toList());
    }

}
