package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByOd;

public abstract class OdSource extends AbstractSource<MetricTooth> {

    protected final List<String> codes = CodesByOd;

    public OdSource(Source<?, ?> inputSource) {
        super(inputSource);
    }
}
