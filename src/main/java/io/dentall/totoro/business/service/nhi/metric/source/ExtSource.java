package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByExt;

public abstract class ExtSource<S> extends AbstractSource<S, OdDto> {

    protected final List<String> codes = CodesByExt;

    public ExtSource(Source<?, ?> inputSource) {
        super(inputSource);
    }
}
