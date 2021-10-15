package io.dentall.totoro.service.mapper;

import io.dentall.totoro.service.dto.MonthDisposalDTO;
import io.dentall.totoro.web.rest.vm.MonthDisposalVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MonthDisposalVMMapper {

    MonthDisposalVMMapper INSTANCE = Mappers.getMapper(MonthDisposalVMMapper.class);

    @Mapping(target = "date", expression = "java( dto.getDisposalDateTime().atOffset(io.dentall.totoro.config.TimeConfig.ZONE_OFF_SET).toLocalDate() )")
    MonthDisposalVM convertMonthDisposalDTO(MonthDisposalDTO dto);
}
