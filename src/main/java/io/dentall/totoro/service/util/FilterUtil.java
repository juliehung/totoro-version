package io.dentall.totoro.service.util;

import io.github.jhipster.service.filter.Filter;

import java.util.function.Predicate;

public final class FilterUtil {

    // entity's field is null
    public static Predicate<Filter> predicateIsNull = filter -> filter != null && filter.getSpecified() != null && !filter.getSpecified();
}
