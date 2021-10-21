package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.DoctorCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1ByDaily;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro2AndTro3Config;
import io.dentall.totoro.business.service.nhi.metric.source.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro2;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 院所去年該季醫師月平均醫療費用點數(院所去年同季醫療費用總點數/醫師總人數後平均)
 * 去年 @date-10@@Point-1@ / 有出現在申報中的醫師不重複數量
 */
public class J1h2Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricDisposal, MetricDisposal> quarterDisposalSource;

    private final Source<MetricTooth, Map<LocalDate, List<MetricTooth>>> quarterByDailySource;

    private final Source<MetricDisposal, Map<LocalDate, List<MetricDisposal>>> quarterByDailyDisposalSource;

    public J1h2Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.quarterDisposalSource = new QuarterOfLastYearDisposalSource(metricConfig);
        this.quarterByDailySource = new QuarterByDailyOfLastYearSource(metricConfig);
        this.quarterByDailyDisposalSource = new QuarterByDailyOfLastYearDisposalSource(metricConfig);
        this.quarterDisposalSource.setExclude(Tro2);
        this.quarterByDailyDisposalSource.setExclude(Tro2);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro2AndTro3Config config = new Tro2AndTro3Config();
        Point1ByDaily point1ByDaily = new Point1ByDaily(metricConfig, config, quarterByDailySource, quarterByDailyDisposalSource).apply();
        DoctorCount doctorCount = new DoctorCount(metricConfig, config, quarterDisposalSource).apply();

        try {
            Long totalPoint1 = point1ByDaily.getResult().values().stream().reduce(Long::sum).orElse(0L);
            return divide(totalPoint1, doctorCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }

}
