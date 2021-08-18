package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;

/**
 * 90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C tooth
 */
public class EndoTreatmentByTooth extends EndoTreatment {

    public EndoTreatmentByTooth(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();

        // 加總所有病人的所有處置的牙數數目
        return nhiMetricRawVMList.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(applyExcludeByVM(exclude))
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
