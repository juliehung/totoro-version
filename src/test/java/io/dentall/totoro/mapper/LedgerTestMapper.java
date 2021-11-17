package io.dentall.totoro.mapper;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.LedgerGroup;
import io.dentall.totoro.domain.LedgerReceipt;
import io.dentall.totoro.domain.LedgerReceiptPrintedRecord;
import io.dentall.totoro.web.rest.vm.LedgerReceiptVM;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupVM;
import io.dentall.totoro.web.rest.vm.LedgerVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Map;

@Mapper
public interface LedgerTestMapper {

    LedgerTestMapper INSTANCE = Mappers.getMapper( LedgerTestMapper.class );

    @Mapping(target="id", expression="java( Long.parseLong(map.get(\"id\")) )")
    @Mapping(target="amount", expression="java( Double.parseDouble(map.get(\"amount\")) )")
    @Mapping(target="date", expression="java( java.time.Instant.parse(map.get(\"date\")) )")
    @Mapping(target="type", expression="java( map.get(\"type\") )")
    @Mapping(target="projectCode", expression="java( map.get(\"projectCode\") )")
    @Mapping(target="displayName", expression="java( map.get(\"displayName\") )" )
    LedgerGroup mapToLedgerGroup(Map<String, String> map);

    @Mapping(target="charge", expression="java( Double.parseDouble(map.get(\"charge\")) )")
    @Mapping(target="date", expression="java( java.time.Instant.parse(map.get(\"date\")) )")
    @Mapping(target="gid", expression="java( Long.parseLong(map.get(\"gid\")) )")
    @Mapping(target="includeStampTax", expression="java( Boolean.parseBoolean(map.get(\"includeStampTax\")) )")
    @Mapping(target="note", expression="java( map.get(\"note\") )")
    LedgerUnwrapGroupVM mapToLedger(Map<String, String> map);

    @Mapping(target="amount", expression="java( Double.parseDouble(map.get(\"amount\")) )")
    @Mapping(target="type", expression="java( map.get(\"type\") )")
    @Mapping(target="projectCode", expression="java( map.get(\"projectCode\") )")
    @Mapping(target="displayName", expression="java( map.get(\"displayName\") )" )
    @Mapping(target="charge", expression="java( Double.parseDouble(map.get(\"charge\")) )")
    @Mapping(target="date", expression="java( java.time.Instant.parse(map.get(\"date\")) )")
    @Mapping(target="gid", expression="java( Long.parseLong(map.get(\"gid\")) )")
    @Mapping(target="includeStampTax", expression="java( Boolean.parseBoolean(map.get(\"includeStampTax\")) )")
    @Mapping(target="note", expression="java( map.get(\"note\") )")
    LedgerVM mapToLedgerVM(Map<String, String> map);

    @Mapping(target="type", expression="java( io.dentall.totoro.domain.enumeration.LedgerReceiptType.valueOf(map.get(\"type\")) )")
    @Mapping(target="rangeType", expression="java( io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType.valueOf(map.get(\"rangeType\")) )")
    @Mapping(target="signed", expression="java( Boolean.parseBoolean(map.get(\"signed\")) )")
    @Mapping(target="stampTax", expression="java( Boolean.parseBoolean(map.get(\"stampTax\")) )")
    LedgerReceiptVM mapToLedgerReceiptVM(Map<String, String> map);

    LedgerReceipt mapToLedgerReceipt(Map<String, String> map);

    LedgerReceiptPrintedRecord mapToLedgerReceiptPrintedRecord(Map<String, String> map);

}
