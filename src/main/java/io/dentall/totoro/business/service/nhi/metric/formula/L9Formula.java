package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestDoctor;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.HighestPoint1Doctor;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

/**
 * 醫師最高總點數佔比 ＠date-15＠ 的 醫師合計點數(最高者)/院所合計點數
 */
public class L9Formula extends AbstractFormula<HighestDoctor> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L9Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public HighestDoctor doCalculate(Collector collector) {
        HighestPoint1Doctor highestPoint1Doctor = new HighestPoint1Doctor(collector, source.outputKey()).apply();
        return highestPoint1Doctor.getResult();
    }
}
