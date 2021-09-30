package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 因為要計算OD重補關係，需要的資料是季加上前三年的資料，所以直接拿SubjectSource使用
 * SubjectSource的資料區間都是取最大區間，季加上三年或二年，端看使用情況要加上多少年
 * <p>
 * 注意：被拿來計算OD重補率的Source，無法被拿去計算其他OD相關指標如 OdPoint1
 * 因為被拿來計算OD重補率的Source是有包含整季與年份的，直接拿來算其他指標會有問題
 * 所以要算其他OD其他非重補率指標，要使用獨立過濾好的Source，像OdQuarterSource是用QuarterSource過濾的
 * 而OdThreeYearNearSource是用SubjectSource過濾的，所以是三年加上季的資料
 */
public class OdThreeYearNearSource extends OdSource {

    private final LocalDate begin;

    private final LocalDate end;

    public OdThreeYearNearSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectSource());
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minus(1095, DAYS);
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd());
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> source) {
        return source
            .filter(vm -> (begin.isBefore(vm.getDisposalDate()) && end.isAfter(vm.getDisposalDate()))
                || begin.isEqual(vm.getDisposalDate())
                || end.isEqual(vm.getDisposalDate()))
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> isNotBlank(vm.getTooth()))
            .collect(toList());
    }

}
