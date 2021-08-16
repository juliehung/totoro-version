package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.ExcludeDto;

import java.util.function.Predicate;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.PredicateHolder.*;
import static java.util.Optional.ofNullable;

public enum Exclude {
    Tro1(tro1), N89013C(nhiCode89013C), NhiCategory_SpecificCode_Group1(nhiCategory1416SpecificCodeG9);

    private final Predicate<ExcludeDto> predicate;

    Exclude(Predicate<ExcludeDto> predicate) {
        this.predicate = predicate;
    }

    public boolean test(ExcludeDto dto) {
        return predicate.test(dto);
    }

    static class PredicateHolder {
        // 案件分類
        static final Predicate<ExcludeDto> nhiCategory14 = dto -> !"14".equals(dto.getNhiCategory());
        static final Predicate<ExcludeDto> nhiCategory16 = dto -> !"16".equals(dto.getNhiCategory());
        static final Predicate<ExcludeDto> nhiCategoryA3 = dto -> !"A3".equals(dto.getNhiCategory());
        static final Predicate<ExcludeDto> nhiCategoryB6 = dto -> !"B6".equals(dto.getNhiCategory());
        static final Predicate<ExcludeDto> nhiCategoryB7 = dto -> !"B7".equals(dto.getNhiCategory());
        static final Predicate<ExcludeDto> nhiCategoryByTro1 = nhiCategory14.and(nhiCategory16).and(nhiCategoryA3).and(nhiCategoryB6).and(nhiCategoryB7);

        // 特殊治療項目
        static final Predicate<ExcludeDto> specificCodeG9 = dto -> !"G9".equals(ofNullable(dto.getTreatmentProcedureSpecificCode()).map(Enum::name).orElse(null));
        static final Predicate<ExcludeDto> specificCodeJA = dto -> !"JA".equals(ofNullable(dto.getTreatmentProcedureSpecificCode()).map(Enum::name).orElse(null));
        static final Predicate<ExcludeDto> specificCodeJB = dto -> !"JB".equals(ofNullable(dto.getTreatmentProcedureSpecificCode()).map(Enum::name).orElse(null));
        static final Predicate<ExcludeDto> specificCodeByTro1 = specificCodeG9.and(specificCodeJA).and(specificCodeJB);

        // 健保代碼
        static final Predicate<ExcludeDto> nhiCode92090C = dto -> !"92090C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<ExcludeDto> nhiCode92091C = dto -> !"92091C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<ExcludeDto> nhiCode92073C = dto -> !"92073C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<ExcludeDto> nhiCode89013C = dto -> !"89013C".equals(dto.getTreatmentProcedureCode());
        static final Predicate<ExcludeDto> nhiCodeByTro1 = nhiCode92090C.and(nhiCode92091C).and(nhiCode92073C);

        // 牙周病統合治療
        static final Predicate<ExcludeDto> perio1 = dto ->
            !"P4001C".equals(dto.getTreatmentProcedureCode()) &&
                !"P4002C".equals(dto.getTreatmentProcedureCode()) &&
                !"P4003C".equals(dto.getTreatmentProcedureCode()) &&
                !"91021C".equals(dto.getTreatmentProcedureCode()) &&
                !"91022C".equals(dto.getTreatmentProcedureCode()) &&
                !"91023C".equals(dto.getTreatmentProcedureCode());

        // 特定牙周保存治療
        static final Predicate<ExcludeDto> perio2 = dto ->
            !"91015C".equals(dto.getTreatmentProcedureCode()) &&
                !"91016C".equals(dto.getTreatmentProcedureCode()) &&
                !"91018C".equals(dto.getTreatmentProcedureCode());

        static final Predicate<ExcludeDto> tro1 = nhiCategoryByTro1.and(perio1).and(perio2).and(specificCodeByTro1).and(nhiCodeByTro1);
        static final Predicate<ExcludeDto> nhiCategory1416SpecificCodeG9 = nhiCategory14.and(nhiCategory16).and(specificCodeG9);

        private PredicateHolder() {
        }
    }
}
