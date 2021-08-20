package io.dentall.totoro.business.service.nhi.metric.source;

public interface Source<S, R> extends Filter<S, R> {

    String inputKey();

    String outputKey();

}
