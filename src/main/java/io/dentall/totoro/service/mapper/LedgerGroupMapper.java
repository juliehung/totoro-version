package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.LedgerGroup;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface LedgerGroupMapper {

    LedgerGroupMapper INSTANCE = Mappers.getMapper( LedgerGroupMapper.class );

    void patching(
        @MappingTarget LedgerGroup origin,
        LedgerGroup patch
    );

    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "displayName", source = "displayName")
    @Mapping(target = "projectCode", source = "projectCode")
    @Mapping(target = "type", source = "type")
    void copyLedgerGroupToLedgerVM(
        LedgerGroup ledgerGroup,
        @MappingTarget Ledger ledger
    );
}
