package io.dentall.totoro.business.service.report.context;

import io.dentall.totoro.business.service.report.dto.*;
import io.dentall.totoro.service.dto.table.AppointmentTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static io.dentall.totoro.business.service.report.context.ReportHelper.toAge;
import static io.dentall.totoro.service.util.DateTimeUtil.convertToTaipeiTime;

@Mapper
public interface ReportMapper {

    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(target = "patientAge", source = "dto", qualifiedByName = "toPatientAge")
    DisposalVo mapToDisposalVo(DisposalDto dto);

    @Mapping(target = "patientAge", source = "dto", qualifiedByName = "toPatientAge")
    NhiVo mapToNhiVo(NhiDto dto);

    @Mapping(target = "patientAge", source = "dto", qualifiedByName = "toPatientAge")
    OwnExpenseVo mapToOwnExpenseVo(OwnExpenseDto dto);

    @Mapping(target = "patientAge", source = "dto", qualifiedByName = "toPatientAge")
    DrugVo mapToDrugVo(DrugDto dto);

    @Mapping(target = "patientAge", source = "dto", qualifiedByName = "toPatientAge")
    TeethCleaningVo mapToTeethCleaningVo(NhiDto dto);

    @Mapping(target = "patientAge", source = "dto", qualifiedByName = "toPatientAge")
    FluoridationVo mapToFluoridationVo(NhiDto dto);

    @Mapping(target = "patientAge", source = "dto", qualifiedByName = "toPatientAge")
    PeriodontalVo mapToPeriodontalVo(NhiDto dto);

    ExtractTeethVo mapToExtractTeethVo(NhiVo dto);

    EndoVo mapToEndoVo(NhiVo dto);

    @Mapping(target = "expectedArrivalTime", source = "expectedArrivalTime", qualifiedByName = "toTaipeiTime")
    FutureAppointmentVo mapToFutureAppointmentVo(AppointmentTable table);

    List<FutureAppointmentVo> mapToFutureAppointmentVo(List<AppointmentTable> table);

    @Named("toTaipeiTime")
    default LocalDateTime toTaipeiTime(Instant instant) {
        return convertToTaipeiTime(instant);
    }

    @Named("toPatientAge")
    default Period toPatientAge(DisposalDto dto) {
        return toAge(dto.getPatientBirth(), dto.getDisposalDate());
    }

}
