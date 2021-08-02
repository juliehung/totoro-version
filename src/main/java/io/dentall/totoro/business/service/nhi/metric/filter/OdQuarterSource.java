package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper;
import io.dentall.totoro.business.service.nhi.metric.util.OdDto;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Collection;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdQuarter;
import static io.dentall.totoro.business.service.nhi.util.ToothUtil.splitA74;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdQuarterSource extends OdSource<NhiMetricRawVM> {

    @Override
    public List<OdDto> doFilter(List<NhiMetricRawVM> source) {
        return source.stream().parallel()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> isNotBlank(vm.getTreatmentProcedureTooth()))
            .map(vm -> {
                    List<String> teeth = splitA74(vm.getTreatmentProcedureTooth());
                    return teeth.stream().map(tooth -> {
                            OdDto odDto = NhiMetricRawMapper.INSTANCE.mapToOdDto(vm);
                            odDto.setTooth(tooth);
                            return odDto;
                        }
                    ).collect(toList());
                }
            ).flatMap(Collection::stream)
            .collect(toList());
    }

    @Override
    public String inputKey() {
        return OdQuarter.input();
    }

    @Override
    public String outputKey() {
        return OdQuarter.output();
    }
}
