package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper;
import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdThreeYearNear;
import static io.dentall.totoro.business.service.nhi.util.ToothUtil.splitA74;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdThreeYearNearSource extends OdSource<NhiMetricRawVM> {

    private final LocalDate endDate; // 這邊通常都應該是要拿到當季的第一天，如1/1、4/1、7/1、10/1

    public OdThreeYearNearSource(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public List<OdDto> doFilter(List<NhiMetricRawVM> source) {
        return source.stream().parallel()
            // 因為資料來源是ThreeYearNearSource，它是用季的最後一天或是當天當基準點往前推1095天，這邊所以要排除該季的所有處置單
            .filter(vm -> endDate.isAfter(vm.getDisposalDate()))
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
        return OdThreeYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdThreeYearNear.output();
    }
}
