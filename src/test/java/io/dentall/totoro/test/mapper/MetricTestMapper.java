package io.dentall.totoro.test.mapper;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTreatment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Mapper
public interface MetricTestMapper {

    MetricTestMapper INSTANCE = Mappers.getMapper(MetricTestMapper.class);

    @Mappings({
        @Mapping(source = "map", target = "patientName", qualifiedByName = "PatientName"),
        @Mapping(source = "map", target = "patientBirth", qualifiedByName = "PatientBirth"),
        @Mapping(source = "map", target = "disposalId", qualifiedByName = "DisposalId"),
        @Mapping(source = "map", target = "disposalDate", qualifiedByName = "DisposalDate"),
        @Mapping(source = "map", target = "cardNumber", qualifiedByName = "CardNumber"),
        @Mapping(source = "map", target = "nhiCategory", qualifiedByName = "NhiCategory"),
        @Mapping(source = "map", target = "partialBurden", qualifiedByName = "PartialBurden"),
        @Mapping(source = "map", target = "examCode", qualifiedByName = "ExamCode"),
        @Mapping(source = "map", target = "examPoint", qualifiedByName = "ExamPoint"),
        @Mapping(source = "map", target = "patientIdentity", qualifiedByName = "PatientIdentity"),
        @Mapping(source = "map", target = "serialNumber", qualifiedByName = "SerialNumber"),
        @Mapping(source = "map", target = "treatmentProcedureCode", qualifiedByName = "Code"),
        @Mapping(source = "map", target = "treatmentProcedureTooth", qualifiedByName = "Tooth"),
        @Mapping(source = "map", target = "treatmentProcedureSurface", qualifiedByName = "Surface"),
        @Mapping(source = "map", target = "treatmentProcedureTotal", qualifiedByName = "Point"),
        @Mapping(source = "map", target = "nhiOriginPoint", qualifiedByName = "OriginPoint"),
        @Mapping(source = "map", target = "treatmentProcedureSpecificCode", qualifiedByName = "SpecificCode"),
        @Mapping(source = "map", target = "doctorName", qualifiedByName = "DoctorName")
    })
    MetricTreatment mapToMetricTreatment(Map<String, String> map);


    @Named("PatientName")
    default String toPatientName(Map<String, String> map) {
        return map.get("PatientName");
    }

    @Named("PatientBirth")
    default LocalDate toPatientBirth(Map<String, String> map) {
        return ofNullable(map.get("PatientBirth")).map(LocalDate::parse).orElse(null);
    }

    @Named("DisposalId")
    default String toDisposalId(Map<String, String> map) {
        return map.get("DisposalId");
    }

    @Named("DisposalDate")
    default LocalDate toDisposalDate(Map<String, String> map) {
        return ofNullable(map.get("DisposalDate")).map(LocalDate::parse).orElse(null);
    }

    @Named("CardNumber")
    default String toCardNumber(Map<String, String> map) {
        return map.get("CardNumber");
    }

    @Named("NhiCategory")
    default String toNhiCategory(Map<String, String> map) {
        return map.get("NhiCategory");
    }

    @Named("PartialBurden")
    default String toPartialBurden(Map<String, String> map) {
        return map.get("PartialBurden");
    }

    @Named("ExamCode")
    default String toExamCode(Map<String, String> map) {
        return map.get("ExamCode");
    }

    @Named("ExamPoint")
    default String toExamPoint(Map<String, String> map) {
        return map.get("ExamPoint");
    }

    @Named("PatientIdentity")
    default String toPatientIdentity(Map<String, String> map) {
        return map.get("PatientIdentity");
    }

    @Named("SerialNumber")
    default String toSerialNumber(Map<String, String> map) {
        return map.get("SerialNumber");
    }

    @Named("Code")
    default String toCode(Map<String, String> map) {
        return map.get("Code");
    }

    @Named("Tooth")
    default String toTooth(Map<String, String> map) {
        return map.get("Tooth");
    }

    @Named("Surface")
    default String toSurface(Map<String, String> map) {
        return map.get("Surface");
    }

    @Named("Point")
    default String toPoint(Map<String, String> map) {
        return map.get("Point");
    }

    @Named("OriginPoint")
    default String toOriginPoint(Map<String, String> map) {
        return map.get("OriginPoint");
    }

    @Named("SpecificCode")
    default String toSpecificCode(Map<String, String> map) {
        return map.get("SpecificCode");
    }

    @Named("DoctorName")
    default String toDoctorName(Map<String, String> map) {
        return map.get("DoctorName");
    }
}
