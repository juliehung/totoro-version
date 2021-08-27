package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt2;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro6;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * (季)就醫病患平均 OD 顆數
 *
 * @ date-10@ @OD-1@齒數/@date-10@@PT-2@
 */
public class K5Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    private final Source<NhiMetricRawVM, OdDto> odSource;

    public K5Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.odSource = new OdQuarterSource(metricConfig);
        this.source.setExclude(Tro6);
        this.odSource.setExclude(Tro6);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, odSource).apply();
        Pt2 pt2 = new Pt2(metricConfig, source).apply();
        try {
            return divide(od1ToothCount.getResult(), pt2.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
