package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.code.NhiCodeHashSet;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByExt;

public abstract class ExtSource extends AbstractSource<MetricTooth> {

    protected final NhiCodeHashSet codes = CodesByExt;

    public ExtSource(Source<MetricTooth, ?> inputSource) {
        super(inputSource);
    }
}
