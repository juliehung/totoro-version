package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.List;
import java.util.stream.Stream;

public class ToothSource extends AbstractSource<MetricTooth> {

    public ToothSource() {
        super(null);
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> source) {
        throw new RuntimeException("Should not be called.");
    }

}
