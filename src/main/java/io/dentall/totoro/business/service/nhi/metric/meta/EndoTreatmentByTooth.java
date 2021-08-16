package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper.INSTANCE;
import static java.util.Optional.ofNullable;

/**
 * 90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C tooth
 */
public class EndoTreatmentByTooth extends EndoTreatment {

    public EndoTreatmentByTooth(Collector collector, Exclude exclude, String sourceName) {
        super(collector, exclude, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();

        // 加總所有病人的所有處置的牙數數目
        return nhiMetricRawVMList.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> ofNullable(exclude).map(exclude1 -> exclude1.test(INSTANCE.mapToExcludeDto(vm))).orElse(true))
            .map(NhiMetricRawVM::getTreatmentProcedureTooth)
            .filter(StringUtils::isNotBlank)
            .map(ToothUtil::splitA74)
            .mapToLong(Collection::size)
            .sum();
    }

    @Override
    public MetaType metaType() {
        return MetaType.EndoTreatmentByTooth;
    }
}
