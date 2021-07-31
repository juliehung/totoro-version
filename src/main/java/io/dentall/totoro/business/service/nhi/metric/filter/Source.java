package io.dentall.totoro.business.service.nhi.metric.filter;

public interface Source<S, R> extends Filter<S, R> {

    String inputKey();

    String outputKey();

}
