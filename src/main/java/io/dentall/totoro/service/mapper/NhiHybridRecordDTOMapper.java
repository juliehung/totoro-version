package io.dentall.totoro.service.mapper;

import io.dentall.totoro.business.service.nhi.NhiHybridRecord;
import io.dentall.totoro.business.service.nhi.NhiHybridRecordDTO;
import io.dentall.totoro.domain.NhiMedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhiHybridRecordDTOMapper {

    NhiHybridRecordDTOMapper INSTANCE = Mappers.getMapper( NhiHybridRecordDTOMapper.class );

    @Mapping(constant = "IC", target = "recordSource")
    @Mapping(constant = "0", target = "disposalId")
    @Mapping(constant = "0", target = "doctorId")
    @Mapping(target = "recordDateTime", expression = "java( io.dentall.totoro.service.util.DateTimeUtil.transformROCDateToLocalDate(nmr.getDate()) )")
    @Mapping(source = "nhiCode", target = "code")
    @Mapping(source = "part", target = "tooth")
    @Mapping(constant = "", target = "surface")
    NhiHybridRecordDTO transformFromNhiMedicalRecord(NhiMedicalRecord nmr);

    @Mapping(target = "recordDateTime", expression = "java( io.dentall.totoro.service.util.DateTimeUtil.transformROCDateToLocalDate(nhr.getRecordDateTime()) )")
    NhiHybridRecordDTO transformFromNhiHybridRecord(NhiHybridRecord nhr);
}
