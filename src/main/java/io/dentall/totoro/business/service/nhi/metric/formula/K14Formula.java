package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoAndOdToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.ExtToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro6;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 院所該季(@date-10@)已申報拔牙之牙齒，回溯同顆牙自家醫療院所前 180 天(@date-2@)所申報牙體復形(@OD-1@)或根管治療項目(@Endo-1@)支付點數加總
 * ÷
 * 院所該季(@date-10@)申報拔牙(@Ext-1@)(@Ext-2@)處置之總齒數
 */
public class K14Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, OdDto> extQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> extQuarterByPatientSource;
    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> endoAndOdHalfYearByPatientSource;

    public K14Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.extQuarterSource = new ExtQuarterSource(metricConfig);
        this.extQuarterByPatientSource = new ExtQuarterByPatientSource(metricConfig);
        this.endoAndOdHalfYearByPatientSource = new EndoAndOdHalfYearNearByPatientSource(metricConfig);
        this.extQuarterSource.setExclude(Tro6);
        this.extQuarterByPatientSource.setExclude(Tro6);
        this.endoAndOdHalfYearByPatientSource.setExclude(Tro6);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        ExtToothCount extToothCount = new ExtToothCount(metricConfig, extQuarterSource).apply();
        EndoAndOdToothCount endoAndOdToothCount = new EndoAndOdToothCount(metricConfig, extQuarterByPatientSource, endoAndOdHalfYearByPatientSource, 1, 180).apply();
        try {
            return divide(endoAndOdToothCount.getResult(), extToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
