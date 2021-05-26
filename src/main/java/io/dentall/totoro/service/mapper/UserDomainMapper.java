package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Authority;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.service.dto.DoctorVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserDomainMapper {

    UserDomainMapper INSTANCE = Mappers.getMapper(UserDomainMapper.class);

    @Mappings({
        @Mapping(target = "authorities", source = "authorities", qualifiedByName = "authoritiesToAuthorityNamedList"),
        @Mapping(target = "nationalId", source ="user.extendUser.nationalId")
    })
    DoctorVM mapToDoctorVM(User user);

    @Named("authoritiesToAuthorityNamedList")
    static Set<String> authoritiesToAuthorityNamedList(Set<Authority> authoritySet) {
        return authoritySet.stream().map(Authority::getName).collect(Collectors.toSet());
    }
}
