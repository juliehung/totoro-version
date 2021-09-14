package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByOd;

public abstract class OdSource<S extends NhiMetricRawVM> extends AbstractSource<S, MetricTooth> {

    protected final List<String> codes = CodesByOd;

    public OdSource(Source<?, ?> inputSource) {
        super(inputSource);
    }
}
