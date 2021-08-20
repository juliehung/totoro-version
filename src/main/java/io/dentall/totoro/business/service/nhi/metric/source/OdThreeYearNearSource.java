package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.dentall.totoro.business.service.nhi.util.ToothUtil.splitA74;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdThreeYearNearSource extends OdSource<NhiMetricRawVM> {

    public OdThreeYearNearSource(InputSource<NhiMetricRawVM> inputSource) {
        super(inputSource);
    }

    @Override
    public List<OdDto> doFilter(List<NhiMetricRawVM> source) {
        AtomicInteger i = new AtomicInteger();
        return source.stream().parallel()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> isNotBlank(vm.getTreatmentProcedureTooth()))
            .map(vm -> {
                    List<String> teeth = splitA74(vm.getTreatmentProcedureTooth());
                    int seq = i.getAndIncrement();
                    return teeth.stream().map(tooth -> {
                            OdDto odDto = NhiMetricRawMapper.INSTANCE.mapToOdDto(vm);
                            odDto.setTooth(tooth);
                            odDto.setTreatmentSeq(seq);
                            return odDto;
                        }
                    ).collect(toList());
                }
            ).flatMap(Collection::stream)
            .collect(toList());
    }

}
