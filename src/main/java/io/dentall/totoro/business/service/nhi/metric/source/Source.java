package io.dentall.totoro.business.service.nhi.metric.source;

public interface Source<S, R> extends Filter<S, R> {

    String name();

    InputSource<S> getInputSource();

    InputSource<R> asInputSource();

}
