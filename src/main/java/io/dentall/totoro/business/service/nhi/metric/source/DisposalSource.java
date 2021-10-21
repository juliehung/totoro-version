package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;

import java.util.List;
import java.util.stream.Stream;

public class DisposalSource extends AbstractDisposalSource<MetricDisposal> {

    public DisposalSource() {
        super(null);
    }

    @Override
    public List<MetricDisposal> doFilter(Stream<MetricDisposal> source) {
        throw new RuntimeException("Should not be called.");
    }
}
