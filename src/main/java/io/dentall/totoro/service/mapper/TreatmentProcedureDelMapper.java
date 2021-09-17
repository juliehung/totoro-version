package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentProcedureDel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TreatmentProcedureDelMapper {

    TreatmentProcedureDelMapper INSTANCE = Mappers.getMapper( TreatmentProcedureDelMapper.class );

    @Mapping(source = "disposal.id", target = "disposalId")
    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "treatmentTask.id", target = "treatmentTaskId")
    @Mapping(source = "procedure.id", target = "procedureId")
    @Mapping(source = "nhiProcedure.id", target = "nhiProcedureId")
    void transformTreatmentProcedureToTreatmentProcedureDel(TreatmentProcedure treatmentProcedure, @MappingTarget TreatmentProcedureDel treatmentProcedureDel);

    TreatmentProcedure convertTreatmentProcedureDelToTreatmentProcedure(TreatmentProcedureDel treatmentProcedureDel);

}
