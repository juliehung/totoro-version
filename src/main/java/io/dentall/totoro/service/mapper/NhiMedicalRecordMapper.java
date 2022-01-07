package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Authority;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.service.dto.DoctorVM;
import io.dentall.totoro.web.rest.vm.NhiMedicalRecordVM;
import io.dentall.totoro.web.rest.vm.NhiMedicalRecordVM2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface NhiMedicalRecordMapper {

    NhiMedicalRecordMapper INSTANCE = Mappers.getMapper(NhiMedicalRecordMapper.class);

    @Mapping(target = "nhiMedicalRecord.date", source = "date")
    @Mapping(target = "nhiMedicalRecord.nhiCategory", source = "nhiCategory")
    @Mapping(target = "nhiMedicalRecord.nhiCode", source = "nhiCode")
    @Mapping(target = "nhiMedicalRecord.part", source = "part")
    @Mapping(target = "nhiMedicalRecord.usage", source = "usage")
    @Mapping(target = "nhiMedicalRecord.note", source = "note")
    @Mapping(target = "nhiMedicalRecord.days", source = "days")
    @Mapping(target = "nhiMedicalRecord.total", source = "total")
    @Mapping(target = "mandarin", source = "mandarin")
    NhiMedicalRecordVM nhiMedicalRecordVM2ToNhiMedicalRecordVM(NhiMedicalRecordVM2 nhiMedicalRecordVM2);

}
