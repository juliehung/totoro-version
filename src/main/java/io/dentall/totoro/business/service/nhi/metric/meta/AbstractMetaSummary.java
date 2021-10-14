package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.dto.SummaryDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.purgeExamPoint;
import static io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil.*;
import static java.lang.Long.parseLong;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class AbstractMetaSummary<T> extends SingleSourceMetaCalculator<List<T>> {

    public AbstractMetaSummary(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, source);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void summaryByTreatment(SummaryDto summaryDto, List<MetricTooth> source) {
        source.stream().reduce(summaryDto,
            (dto, vm) -> {
                String code = vm.getTreatmentProcedureCode();
                Long points = vm.getTreatmentProcedureTotal();

                if (isPeriodAtSalary(code)) {
                    dto.setPerioPoint(dto.getPerioPoint() + points);
                } else if (isEndoAtSalary(code)) {
                    dto.setEndoPoint(dto.getEndoPoint() + points);
                }

                if (!isExaminationCodeAtSalary(code)) {
                    dto.setTreatmentPoint(dto.getTreatmentPoint() + points);
                    dto.setTotal(dto.getTotal() + points);
                }

                return dto;
            },
            (dto1, dto2) -> {
                dto1.setPerioPoint(dto1.getPerioPoint() + dto2.getPerioPoint());
                dto1.setEndoPoint(dto1.getEndoPoint() + dto2.getEndoPoint());
                dto1.setTreatmentPoint(dto1.getTreatmentPoint() + dto2.getTreatmentPoint());
                return dto1;
            });
    }

    protected void summaryByDisposal(SummaryDto summaryDto, MetricTooth vm) {
        if (isNotBlank(vm.getExamPoint())) {
            long examPoint = parseLong(vm.getExamPoint());
            if (isInfectionExaminationCodeAtSalary(vm.getExamCode())) {
                summaryDto.setInfectionExaminationPoint(summaryDto.getInfectionExaminationPoint() + examPoint);
            } else {
                summaryDto.setRegularExaminationPoint(summaryDto.getRegularExaminationPoint() + examPoint);
            }
            summaryDto.setPureExaminationPoint(summaryDto.getPureExaminationPoint() + purgeExamPoint(vm.getExamCode(), examPoint));
            summaryDto.setTotal(summaryDto.getTotal() + examPoint);
        }

        if (isNotBlank(vm.getPartialBurden())) {
            summaryDto.setCopayment(summaryDto.getCopayment() + parseLong(vm.getPartialBurden()));
        }
    }
}
