package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.LedgerGroup;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupVM;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface LedgerGroupMapper {

    LedgerGroupMapper INSTANCE = Mappers.getMapper( LedgerGroupMapper.class );

    void patching(
        @MappingTarget LedgerGroup origin,
        LedgerGroup patch
    );

    @Mapping(target = "gid", source = "ledgerGroup.id")
    LedgerUnwrapGroupVM convertLedgerToLedgerUnwrapGroupVM(Ledger l);


    Ledger convertLedgerUnwrapGroupVMToLedger(LedgerUnwrapGroupVM vm);
}
