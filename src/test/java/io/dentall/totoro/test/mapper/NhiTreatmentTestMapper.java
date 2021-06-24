package io.dentall.totoro.test.mapper;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.test.dao.NhiTreatment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhiTreatmentTestMapper {

    NhiTreatmentTestMapper INSTANCE = Mappers.getMapper(NhiTreatmentTestMapper.class);

    @Mappings({
        @Mapping(source = "netp.treatmentProcedure.disposal.id", target = "disposalId"),
        @Mapping(constant = "HIS", target = "sourceType"),
        @Mapping(source = "a71", target = "datetime"),
        @Mapping(source = "a73", target = "code"),
        @Mapping(source = "a74", target = "tooth"),
        @Mapping(source = "a75", target = "surface")
    })
    NhiTreatment netpToNhiTreatment(NhiExtendTreatmentProcedure netp);

    @Mappings({
        @Mapping(constant = "IC", target = "sourceType"),
        @Mapping(source = "date", target = "datetime"),
        @Mapping(source = "nhiCode", target = "code"),
        @Mapping(source = "part", target = "tooth"),
        @Mapping(source = "usage", target = "surface")
    })
    NhiTreatment medicalToNhiTreatment(NhiMedicalRecord medical);
}
