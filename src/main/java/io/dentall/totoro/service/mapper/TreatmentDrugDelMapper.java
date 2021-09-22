package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.domain.TreatmentDrugDel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TreatmentDrugDelMapper {

    TreatmentDrugDelMapper INSTANCE = Mappers.getMapper( TreatmentDrugDelMapper.class );

    @Mapping(source = "prescription.id", target = "prescriptionId")
    @Mapping(source = "drug.id", target = "drugId")
    void transformTreatmentDrugToTreatmentDrugDel(TreatmentDrug treatmentDrug, @MappingTarget TreatmentDrugDel treatmentDrugDel);

    TreatmentDrug convertTreatmentDrugDelToTreatmentDrug(TreatmentDrugDel treatmentDrugDel);

}
