package io.dentall.totoro.business.service.report.context;

import io.dentall.totoro.domain.ReportRecord;
import io.dentall.totoro.web.rest.vm.FollowupReportVM;
import io.dentall.totoro.web.rest.vm.TreatmentReportVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Mapper
public interface ReportVmMapper {

    ReportVmMapper INSTANCE = Mappers.getMapper(ReportVmMapper.class);

    @Mapping(target = "date", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"date\"))")
    @Mapping(target = "item", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"item\"))")
    @Mapping(target = "doctor", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"doctor\"))")
    @Mapping(target = "exportTime", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"exportTime\"))")
    FollowupReportVM mapToFollowupReportVM(ReportRecord record);

    List<FollowupReportVM> mapToFollowupReportVM(List<ReportRecord> record);

    @Mapping(target = "date", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"date\"))")
    @Mapping(target = "item", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"item\"))")
    @Mapping(target = "doctor", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"doctor\"))")
    @Mapping(target = "exportTime", expression = "java(io.dentall.totoro.business.service.report.context.ReportVmMapper.getAttrValue(record.getAttrs(), \"exportTime\"))")
    TreatmentReportVM mapToTreatmentReportVM(ReportRecord record);

    List<TreatmentReportVM> mapToTreatmentReportVM(List<ReportRecord> record);

    static String getAttrValue(Map<String, String> attrs, String key) {
        return ofNullable(attrs).map(map -> map.get(key)).orElse("");
    }
}
