package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.DentauthToken;
import io.dentall.totoro.web.rest.vm.DentauthTokenVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DentauthTokenMapper {

    DentauthTokenMapper mapper = Mappers.getMapper(DentauthTokenMapper.class);

    DentauthTokenVM mapToVM(DentauthToken dentauthToken);
}
