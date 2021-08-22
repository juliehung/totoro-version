package io.dentall.totoro.business.service.nhi.metric.source;

import java.util.List;

public interface Filter<S, R> {

    List<R> filter(List<S> source);

}
