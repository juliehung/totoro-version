package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.function.Predicate;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.PredicateHolder.*;

public enum Exclude {
    Tro1(tro1), Tro2(tro2), Tro5(tro5), Tro6(tro6),
    NhiCategory1416Perio1Perio2(nhiCategory1416Perio1Perio2),
    NhiCategory1416(nhiCategory1416),
    N89013C(nhiCode89013C),
    NhiCategory_SpecificCode_Group1(nhiCategory1416SpecificCodeG9);

    private final Predicate<MetricTooth> predicate;

    Exclude(Predicate<MetricTooth> predicate) {
        this.predicate = predicate;
    }

    public boolean test(MetricTooth dto) {
        return predicate.test(dto);
    }

    static class PredicateHolder {
        // 案件分類
        static final Predicate<MetricTooth> nhiCategory14 = dto -> !"14".equals(dto.getNhiCategory());
        static final Predicate<MetricTooth> nhiCategory16 = dto -> !"16".equals(dto.getNhiCategory());
        static final Predicate<MetricTooth> nhiCategoryA3 = dto -> !"A3".equals(dto.getNhiCategory());
        static final Predicate<MetricTooth> nhiCategoryB6 = dto -> !"B6".equals(dto.getNhiCategory());
        static final Predicate<MetricTooth> nhiCategoryB7 = dto -> !"B7".equals(dto.getNhiCategory());
        static final Predicate<MetricTooth> nhiCategoryByTro1 = nhiCategory14.and(nhiCategory16).and(nhiCategoryA3).and(nhiCategoryB6).and(nhiCategoryB7);
        static final Predicate<MetricTooth> nhiCategoryByTro2 = nhiCategory14.and(nhiCategory16).and(nhiCategoryA3).and(nhiCategoryB6).and(nhiCategoryB7);
        static final Predicate<MetricTooth> nhiCategoryByTro5 = nhiCategory14.and(nhiCategory16).and(nhiCategoryA3);
        static final Predicate<MetricTooth> nhiCategoryByTro6 = nhiCategory14.and(nhiCategory16).and(nhiCategoryA3).and(nhiCategoryB6).and(nhiCategoryB7);

        // 特殊治療項目
        static final Predicate<MetricTooth> specificCodeG9 = dto -> !"G9".equals(dto.getTreatmentProcedureSpecificCode());
        static final Predicate<MetricTooth> specificCodeJA = dto -> !"JA".equals(dto.getTreatmentProcedureSpecificCode());
        static final Predicate<MetricTooth> specificCodeJB = dto -> !"JB".equals(dto.getTreatmentProcedureSpecificCode());
        static final Predicate<MetricTooth> specificCodeByTro1 = specificCodeG9.and(specificCodeJA).and(specificCodeJB);
        static final Predicate<MetricTooth> specificCodeByTro2 = specificCodeG9.and(specificCodeJA).and(specificCodeJB);
        static final Predicate<MetricTooth> specificCodeByTro5 = specificCodeG9.and(specificCodeJA);
        static final Predicate<MetricTooth> specificCodeByTro6 = specificCodeJA.and(specificCodeJB);

        // 健保代碼
        static final Predicate<MetricTooth> nhiCode92090C = dto -> !"92090C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<MetricTooth> nhiCode92091C = dto -> !"92091C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<MetricTooth> nhiCode92073C = dto -> !"92073C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<MetricTooth> nhiCode89013C = dto -> !"89013C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<MetricTooth> nhiCode91014C = dto -> !"91014C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<MetricTooth> nhiCodeByTro1 = nhiCode92090C.and(nhiCode92091C).and(nhiCode92073C);
        static final Predicate<MetricTooth> nhiCodeByTro2 = nhiCode92090C.and(nhiCode92091C).and(nhiCode92073C);
        static final Predicate<MetricTooth> nhiCodeByTro5 = nhiCode91014C;

        // 牙周病統合治療
        static final Predicate<MetricTooth> perio1 = dto ->
            !"P4001C".equals(dto.getTreatmentProcedureCode()) &&
                !"P4002C".equals(dto.getTreatmentProcedureCode()) &&
                !"P4003C".equals(dto.getTreatmentProcedureCode()) &&
                !"91021C".equals(dto.getTreatmentProcedureCode()) &&
                !"91022C".equals(dto.getTreatmentProcedureCode()) &&
                !"91023C".equals(dto.getTreatmentProcedureCode());

        // 特定牙周保存治療
        static final Predicate<MetricTooth> perio2 = dto ->
            !"91015C".equals(dto.getTreatmentProcedureCode()) &&
                !"91016C".equals(dto.getTreatmentProcedureCode()) &&
                !"91018C".equals(dto.getTreatmentProcedureCode());

        static final Predicate<MetricTooth> tro1 = nhiCategoryByTro1.and(perio1).and(perio2).and(specificCodeByTro1).and(nhiCodeByTro1);
        static final Predicate<MetricTooth> tro2 = nhiCategoryByTro2.and(perio1).and(perio2).and(specificCodeByTro2).and(nhiCodeByTro2);
        static final Predicate<MetricTooth> tro5 = nhiCategoryByTro5.and(perio1).and(perio2).and(specificCodeByTro5).and(nhiCodeByTro5);
        static final Predicate<MetricTooth> tro6 = nhiCategoryByTro6.and(perio1).and(specificCodeByTro6);
        static final Predicate<MetricTooth> nhiCategory1416SpecificCodeG9 = nhiCategory14.and(nhiCategory16).and(specificCodeG9);
        static final Predicate<MetricTooth> nhiCategory1416Perio1Perio2 = nhiCategory14.and(nhiCategory16).and(perio1).and(perio2);
        static final Predicate<MetricTooth> nhiCategory1416 = nhiCategory14.and(nhiCategory16);

        private PredicateHolder() {
        }
    }
}
