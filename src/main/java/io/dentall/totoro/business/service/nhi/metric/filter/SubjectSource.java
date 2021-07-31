package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.Subject;

public abstract class SubjectSource<S, R> extends AbstractSource<S, R> {

    protected FilterKey filterKey = Subject;

    public SubjectSource(Collector collector) {
        super(collector);
    }
}
