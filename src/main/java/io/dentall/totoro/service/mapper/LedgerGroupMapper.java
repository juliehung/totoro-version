package io.dentall.totoro.service.mapper;

import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.web.rest.vm.LedgerReceiptPrintedRecordVM;
import io.dentall.totoro.web.rest.vm.LedgerReceiptVM;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupVM;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
    imports = { ImageGcsBusinessService.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LedgerGroupMapper {

    LedgerGroupMapper INSTANCE = Mappers.getMapper( LedgerGroupMapper.class );

    void patching(
        @MappingTarget LedgerGroup origin,
        LedgerGroup patch
    );

    LedgerReceiptPrintedRecordVM ledgerReceiptPrintedRecordToLedgerReceiptPrintedRecordVM(LedgerReceiptPrintedRecord ledgerReceiptPrintedRecord);

    @Mapping(target = "gid", source = "ledgerGroup.id")
    @Mapping(target = "type", source = "ledgerGroup.type")
    @Mapping(target = "projectCode", source = "ledgerGroup.projectCode")
    @Mapping(target = "displayName", source = "ledgerGroup.displayName")
    @Mapping(target = "patientId", source = "ledgerGroup.patientId")
    @Mapping(target = "amount", source = "ledgerGroup.amount")
    LedgerUnwrapGroupVM convertLedgerToLedgerUnwrapGroupVM(Ledger l);

    Ledger convertLedgerUnwrapGroupVMToLedger(LedgerUnwrapGroupVM vm);

    LedgerReceiptVM convertLedgerReceiptFromDomainToVM(LedgerReceipt domain);

}
