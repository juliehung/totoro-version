package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.Subject;

public abstract class SubjectSource<S, R> extends AbstractSource<S, R> {

    protected SourceId sourceId = Subject;

}
