package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentProcedureDel;
import io.dentall.totoro.repository.TreatmentProcedureDelRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TreatmentProcedureDelMapper {

    TreatmentProcedureDelMapper INSTANCE = Mappers.getMapper( TreatmentProcedureDelMapper.class );

    void transformTreatmentProcedureToTreatmentProcedureDel(TreatmentProcedure treatmentProcedure, @MappingTarget TreatmentProcedureDel treatmentProcedureDel);

    TreatmentProcedure convertTreatmentProcedureDelToTreatmentProcedure(TreatmentProcedureDel treatmentProcedureDel);

}
