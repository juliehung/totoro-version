package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestDoctorDto;
import io.dentall.totoro.business.service.nhi.metric.meta.HighestPoint1Doctor;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

/**
 * 醫師最高總點數佔比 ＠date-15＠ 的 醫師合計點數(最高者)/院所合計點數
 */
public class L9Formula extends AbstractFormula<HighestDoctorDto> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L9Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public HighestDoctorDto doCalculate(Collector collector) {
        HighestPoint1Doctor highestPoint1Doctor = new HighestPoint1Doctor(collector, source).apply();
        return highestPoint1Doctor.getResult();
    }
}
