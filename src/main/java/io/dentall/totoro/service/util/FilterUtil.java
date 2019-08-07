package io.dentall.totoro.service.util;

import io.github.jhipster.service.filter.Filter;

import java.util.function.Predicate;

public final class FilterUtil {

    // check entity's field is null
    public final static Predicate<Filter> predicateIsNull = filter -> filter != null && filter.getSpecified() != null && !filter.getSpecified();
}
