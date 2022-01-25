package io.dentall.totoro.service.mapper;

import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.web.rest.vm.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(LedgerGroupMapperDecorator.class)
public interface LedgerGroupMapper {

    LedgerGroupMapper INSTANCE = Mappers.getMapper( LedgerGroupMapper.class );

    @Mapping(target = "patientId", ignore = true)
    void patchLedgerGroup(
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
    @Mapping(target = "doctorId", source = "ledgerGroup.doctorId")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    LedgerVM convertLedgerFromDomainToVM(Ledger l);

    Ledger convertLedgerUnwrapGroupVMToLedger(LedgerUnwrapGroupVM vm);

    LedgerReceiptVM convertLedgerReceiptFromDomainToVM(LedgerReceipt domain);

    LedgerReceipt convertLedgerReceiptFromCreateVMToDomain(LedgerReceiptCreateVM ledgerReceiptCreateVM);

    @Mapping(target = "displayName", source = "ledgerGroup.displayName")
    @Mapping(target = "projectCode", source = "ledgerGroup.projectCode")
    @Mapping(target = "ledgerGroupType", source = "ledgerGroup.projectCode")
    @Mapping(target = "receiptRangeType", source = "rangeType")
    LedgerReceiptExcelVM ledgerReceiptToLedgerReceiptExcelVM(LedgerReceipt domain);

    List<LedgerReceiptExcelVM> ledgerReceiptListToLedgerReceiptExcelVMList(List<LedgerReceipt> domains);

}