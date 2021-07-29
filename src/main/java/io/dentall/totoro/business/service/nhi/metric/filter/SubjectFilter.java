package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.Subject;

public abstract class SubjectFilter implements Filter {

    protected FilterKey filterKey = Subject;
}
