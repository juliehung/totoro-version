package io.dentall.totoro.test.mapper;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentProcedureTestOnly;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TreatmentProcedureTestMapper {

    TreatmentProcedureTestMapper INSTANCE = Mappers.getMapper(TreatmentProcedureTestMapper.class);

    TreatmentProcedureTestOnly map(TreatmentProcedure treatmentProcedure);
}
