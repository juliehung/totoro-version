package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.NhiMonthDeclarationRuleCheckReportVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.NhiMonthDeclarationRuleCheckReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhiRuleCheckMapper {
    NhiRuleCheckMapper INSTANCE = Mappers.getMapper(NhiRuleCheckMapper.class);

    @Mapping(target = "id", source = "treatmentProcedureId")
    NhiRuleCheckTxSnapshot convertToNhiRuleCheckTxSnapshot(NhiRuleCheckMonthDeclarationTx a);

    @Mapping(target = "txSnapshots", ignore = true)
    NhiRuleCheckBody convertToNhiRuleCheckBody(NhiRuleCheckMonthDeclarationTx a);

    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    @Mapping(target = "lastModifiedDate", source = "lastModifiedDate")
    NhiMonthDeclarationRuleCheckReportVM convertToNhiMonthDeclarationRuleCheckReport(NhiMonthDeclarationRuleCheckReport a);

}
