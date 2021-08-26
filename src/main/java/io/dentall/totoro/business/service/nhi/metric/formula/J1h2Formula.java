package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.DoctorCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1ByDaily;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro2Tro3Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterByDailyOfLastYearSource;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterOfLastYearSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 院所去年該季醫師月平均醫療費用點數(院所去年同季醫療費用總點數/醫師總人數後平均)
 * 去年 @date-10@@Point-1@ / 有出現在申報中的醫師不重複數量
 */
public class J1h2Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> quarterSource;
    private final Source<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> quarterByDailySource;

    public J1h2Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.quarterSource = new QuarterOfLastYearSource(metricConfig);
        this.quarterByDailySource = new QuarterByDailyOfLastYearSource(metricConfig);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro2Tro3Config config = new Tro2Tro3Config(metricConfig);
        Point1ByDaily point1ByDaily = new Point1ByDaily(metricConfig, config, quarterByDailySource).apply();
        DoctorCount doctorCount = new DoctorCount(metricConfig, config, quarterSource).apply();

        try {
            Long totalPoint1 = point1ByDaily.getResult().values().stream().reduce(Long::sum).orElse(0L);
            return divide(totalPoint1, doctorCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ONE;
        }
    }

}
