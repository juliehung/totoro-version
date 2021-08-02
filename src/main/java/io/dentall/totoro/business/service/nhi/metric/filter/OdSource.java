package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.util.OdDto;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public abstract class OdSource<S> extends AbstractSource<S, OdDto> {

    protected final List<String> codes = unmodifiableList(asList("89001C", "89002C", "89003C", "89004C", "89005C", "89008C", "89009C", "89010C", "89011C", "89012C"));

}
