package io.dentall.totoro.business.service.nhi.metric.source;

import java.util.List;

public interface Filter<S, R> extends InputSource<S> {

    List<R> doFilter(List<S> source);

}
