package io.dentall.totoro.test.mapper;

import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.step_definitions.StepDefinitionUtil;
import io.dentall.totoro.test.dao.NhiTreatment;
import org.joda.time.DateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static io.dentall.totoro.step_definitions.StepDefinitionUtil.*;

@Mapper
public interface NhiTreatmentTestMapper {

    NhiTreatmentTestMapper INSTANCE = Mappers.getMapper(NhiTreatmentTestMapper.class);

    @Mappings({
        @Mapping(source = "netp.treatmentProcedure.disposal.id", target = "disposalId"),
        @Mapping(constant = HIS_SourceType, target = "sourceType"),
        @Mapping(source = "a71", target = "datetime"),
        @Mapping(source = "a73", target = "code"),
        @Mapping(source = "a74", target = "tooth"),
        @Mapping(source = "a75", target = "surface")
    })
    NhiTreatment netpToNhiTreatment(NhiExtendTreatmentProcedure netp);

    @Mappings({
        @Mapping(constant = IC_SourceType, target = "sourceType"),
        @Mapping(source = "date", target = "datetime"),
        @Mapping(source = "nhiCode", target = "code"),
        @Mapping(source = "part", target = "tooth"),
        @Mapping(source = "usage", target = "surface")
    })
    NhiTreatment medicalToNhiTreatment(NhiMedicalRecord medical);

    @Mappings({
        @Mapping(constant = Snapshot_SourceType, target = "sourceType"),
        @Mapping(target = "datetime", expression = "java(io.dentall.totoro.service.util.DateTimeUtil.transformLocalDateToRocDate(java.time.Instant.now()))"),
        @Mapping(source = "id", target = "nhiExtendTreatmentProcedureId"),
        @Mapping(source = "nhiCode", target = "code"),
        @Mapping(source = "teeth", target = "tooth"),
        @Mapping(source = "surface", target = "surface")
    })
    NhiTreatment snapshotToNhiTreatment(NhiRuleCheckTxSnapshot nhiRuleCheckTxSnapshot);

}
