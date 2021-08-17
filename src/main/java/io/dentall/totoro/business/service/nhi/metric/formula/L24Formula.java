package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoReTreatmentByTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatmentByTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 半年根管再治療率
 * date-2 Point-11 (90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C) / ＠date-2@(90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C)
 */
public class L24Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L24Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        MetaConfig config = new Tro1Config();
        EndoTreatmentByTooth endoTreatmentByTooth = new EndoTreatmentByTooth(collector, config, source.outputKey()).apply();
        EndoReTreatmentByTooth endoReTreatmentByTooth = new EndoReTreatmentByTooth(collector, config, source.outputKey()).apply();
        try {
            return divide(endoReTreatmentByTooth.getResult(), endoTreatmentByTooth.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
