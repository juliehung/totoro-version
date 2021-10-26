package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.code.NhiCodeHashSet;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByOd;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class OdSource extends AbstractSource<MetricTooth> {

    protected final NhiCodeHashSet codes = CodesByOd;

    public OdSource(Source<MetricTooth, ?> inputSource) {
        super(inputSource);
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> source) {
        return source
            .filter(dto -> codes.contains(dto.getTreatmentProcedureCode()))
            .filter(vm -> isNotBlank(vm.getTooth()))
            .collect(toList());
    }
}
