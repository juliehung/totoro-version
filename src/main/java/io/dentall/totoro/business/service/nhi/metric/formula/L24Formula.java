package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoReTreatmentByTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatmentByTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.HalfYearNearSource;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 半年根管再治療率
 * date-2 Point-11 (90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C) / ＠date-2@(90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C)
 */
public class L24Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public L24Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new HalfYearNearSource(metricConfig);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        EndoTreatmentByTooth endoTreatmentByTooth = new EndoTreatmentByTooth(metricConfig, config, source).apply();
        EndoReTreatmentByTooth endoReTreatmentByTooth = new EndoReTreatmentByTooth(metricConfig, config, source).apply();
        try {
            return toPercentage(endoReTreatmentByTooth.getResult(), endoTreatmentByTooth.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
