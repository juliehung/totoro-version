package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.AllPoint1Doctor;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro2Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro2;

/**
 * 院所之醫師個人每月醫療費用點數超過 52.5 萬
 * 列出 @date-10@ 醫師 @Point-1@ > 525000 點的醫師名字
 */
public class J1h1Formula extends AbstractFormula<Map<Long, Long>> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public J1h1Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.source.setExclude(Tro2);
    }

    @Override
    protected Map<Long, Long> doCalculate(MetricConfig metricConfig) {
        Tro2Config config = new Tro2Config(metricConfig);
        AllPoint1Doctor point1 = new AllPoint1Doctor(metricConfig, config, source).apply();
        Map<Long, Long> allDoctorPoint1 = point1.getResult();
        Map<Long, Long> qualified = new HashMap<>();

        allDoctorPoint1.entrySet().stream()
            .filter(entry -> entry.getValue() > 525000)
            .forEach(entry -> {
                qualified.put(entry.getKey(), entry.getValue());
            });

        return qualified;
    }

}
