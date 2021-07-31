package io.dentall.totoro.business.service.nhi.metric.filter;

import java.util.List;

public interface Filter<S, R> {

    List<R> doFilter(List<S> source);

}
