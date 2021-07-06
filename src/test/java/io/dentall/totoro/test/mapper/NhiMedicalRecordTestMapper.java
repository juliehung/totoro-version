package io.dentall.totoro.test.mapper;

import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.domain.NhiMedicalRecordTestOnly;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhiMedicalRecordTestMapper {

    NhiMedicalRecordTestMapper INSTANCE = Mappers.getMapper(NhiMedicalRecordTestMapper.class);

    NhiMedicalRecordTestOnly map(NhiMedicalRecord nhiMedicalREcord);
}
