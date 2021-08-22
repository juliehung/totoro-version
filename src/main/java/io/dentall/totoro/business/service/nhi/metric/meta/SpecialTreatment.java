package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.service.nhi.metric.dto.SpecialTreatmentAnalysisDto;
import io.dentall.totoro.business.service.nhi.metric.dto.SpecialTreatmentItemDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.NhiSpecialCode.*;
import static java.util.Optional.ofNullable;

/**
 * 特定治療項目
 */
public class SpecialTreatment extends SingleSourceMetaCalculator<SpecialTreatmentAnalysisDto> {

    public SpecialTreatment(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, source);
    }

    @Override
    public SpecialTreatmentAnalysisDto doCalculate(MetricConfig metricConfig) {
        List<Map<NhiSpecialCode, List<NhiMetricRawVM>>> source = metricConfig.retrieveSource(source().key());
        SpecialTreatmentItemDto summary = new SpecialTreatmentItemDto(0, 0);

        Map<NhiSpecialCode, SpecialTreatmentItemDto> itemMap =
            source.get(0).entrySet().stream()
                .reduce(new HashMap<>(), (map, entry) -> {
                        SpecialTreatmentItemDto item = summary(entry.getValue());
                        map.put(entry.getKey(), item);
                        summary.setCaseCount(summary.getCaseCount() + item.getCaseCount());
                        summary.setPoints(summary.getPoints() + item.getPoints());
                        return map;
                    },
                    (map1, map2) -> {
                        map1.putAll(map2);
                        return map1;
                    });

        SpecialTreatmentAnalysisDto analysis = new SpecialTreatmentAnalysisDto();
        analysis.setSummary(summary);
        applyItem(analysis, itemMap);
        combineItem(analysis);
        percentage(analysis);

        return analysis;
    }

    private SpecialTreatmentItemDto summary(List<NhiMetricRawVM> list) {
        return list.stream().reduce(new SpecialTreatmentItemDto(0, 0L),
            (item, vm) -> {
                item.setPoints(item.getPoints() + ofNullable(vm.getTreatmentProcedureTotal()).orElse(0L));
                item.setCaseCount(item.getCaseCount() + 1);
                return item;
            },
            (item1, item2) -> {
                item1.setCaseCount(item1.getCaseCount() + item2.getCaseCount());
                item1.setPoints(item1.getPoints() + item2.getPoints());
                return item1;
            }
        );
    }

    private void applyItem(SpecialTreatmentAnalysisDto analysis, Map<NhiSpecialCode, SpecialTreatmentItemDto> itemMap) {
        ofNullable(itemMap.get(P1)).ifPresent(analysis::setP1);
        ofNullable(itemMap.get(P2)).ifPresent(analysis::setP2);
        ofNullable(itemMap.get(P3)).ifPresent(analysis::setP3);
        ofNullable(itemMap.get(P4)).ifPresent(analysis::setP4);
        ofNullable(itemMap.get(P5)).ifPresent(analysis::setP5);
        ofNullable(itemMap.get(P6)).ifPresent(analysis::setP6);
        ofNullable(itemMap.get(P7)).ifPresent(analysis::setP7);
        ofNullable(itemMap.get(P8)).ifPresent(analysis::setP8);
        ofNullable(itemMap.get(OTHER)).ifPresent(analysis::setOther);
    }

    private void combineItem(SpecialTreatmentAnalysisDto analysis) {
        SpecialTreatmentItemDto p1p5 = new SpecialTreatmentItemDto(
            analysis.getP1().getCaseCount() + analysis.getP5().getCaseCount(),
            analysis.getP1().getPoints() + analysis.getP5().getPoints());
        analysis.setP1p5(p1p5);

        SpecialTreatmentItemDto p2p3 = new SpecialTreatmentItemDto(
            analysis.getP2().getCaseCount() + analysis.getP3().getCaseCount(),
            analysis.getP2().getPoints() + analysis.getP3().getPoints());
        analysis.setP2p3(p2p3);

        SpecialTreatmentItemDto p4p8 = new SpecialTreatmentItemDto(
            analysis.getP4().getCaseCount() + analysis.getP8().getCaseCount(),
            analysis.getP4().getPoints() + analysis.getP8().getPoints());
        analysis.setP4p8(p4p8);

        SpecialTreatmentItemDto p6p7AndOther = new SpecialTreatmentItemDto(
            analysis.getP6().getCaseCount() + analysis.getP7().getCaseCount() + analysis.getOther().getCaseCount(),
            analysis.getP6().getPoints() + analysis.getP7().getPoints() + analysis.getOther().getPoints());
        analysis.setP6p7AndOther(p6p7AndOther);
    }

    private void percentage(SpecialTreatmentAnalysisDto analysis) {
        long totalPoints = analysis.getSummary().getPoints();
        int totalCaseNumber = analysis.getSummary().getCaseCount();

        analysis.getP1().calculatePercentageOfPoints(totalPoints);
        analysis.getP1().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP2().calculatePercentageOfPoints(totalPoints);
        analysis.getP2().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP3().calculatePercentageOfPoints(totalPoints);
        analysis.getP3().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP4().calculatePercentageOfPoints(totalPoints);
        analysis.getP4().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP5().calculatePercentageOfPoints(totalPoints);
        analysis.getP5().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP6().calculatePercentageOfPoints(totalPoints);
        analysis.getP6().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP7().calculatePercentageOfPoints(totalPoints);
        analysis.getP7().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP8().calculatePercentageOfPoints(totalPoints);
        analysis.getP8().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getOther().calculatePercentageOfPoints(totalPoints);
        analysis.getOther().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getSummary().calculatePercentageOfPoints(totalPoints);
        analysis.getSummary().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP1p5().calculatePercentageOfPoints(totalPoints);
        analysis.getP1p5().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP2p3().calculatePercentageOfPoints(totalPoints);
        analysis.getP2p3().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP4p8().calculatePercentageOfPoints(totalPoints);
        analysis.getP4p8().calculatePercentageOfCaseCount(totalCaseNumber);
        analysis.getP6p7AndOther().calculatePercentageOfPoints(totalPoints);
        analysis.getP6p7AndOther().calculatePercentageOfCaseCount(totalCaseNumber);
    }


    @Override
    public MetaType metaType() {
        return MetaType.SpecialTreatment;
    }
}
