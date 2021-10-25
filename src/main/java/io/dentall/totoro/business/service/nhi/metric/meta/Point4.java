package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.isHoliday;

/**
 * 部分負擔點數
 */
public class Point4 extends SingleSourceMetaCalculator<Long> {

    public Point4(MetricConfig metricConfig, Source<MetricDisposal, MetricDisposal> source) {
        this(metricConfig, null, source);
    }

    public Point4(MetricConfig metricConfig, MetaConfig config, Source<MetricDisposal, MetricDisposal> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricDisposal> source = metricConfig.retrieveSource(source().key());

        return source.stream()
            .map(disposal -> {
                if (getConfig().isExcludeHolidayPoint() && isHoliday(disposal.getDisposalDate(), metricConfig.getHolidayMap())) {
                    return "0";
                } else {
                    return disposal.getPartialBurden();
                }
            })
            .filter(StringUtils::isNotBlank)
            .map(Long::valueOf)
            .reduce(Long::sum)
            .orElse(0L);
    }

}
