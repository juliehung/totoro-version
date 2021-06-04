package io.dentall.totoro.service.mapper;


import io.dentall.totoro.business.vm.PatientSearchVM;
import io.dentall.totoro.service.dto.PatientSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientDomainMapper {

    PatientDomainMapper INSTANCE = Mappers.getMapper(PatientDomainMapper.class);

    PatientSearchVM mapToSearchVM(PatientSearchDTO dto);
}
