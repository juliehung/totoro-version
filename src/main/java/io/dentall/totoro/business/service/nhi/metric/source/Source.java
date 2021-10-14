package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;

public interface Source<S, R> extends Filter<S, R> {

    SourceKey key();

    Source<?, ?> getInputSource();

    void setExclude(Exclude exclude);

    Exclude getExclude();

}
