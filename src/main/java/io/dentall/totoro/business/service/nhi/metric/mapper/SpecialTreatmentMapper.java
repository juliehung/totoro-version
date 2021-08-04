package io.dentall.totoro.business.service.nhi.metric.mapper;

import io.dentall.totoro.business.service.nhi.metric.dto.SpecialTreatmentItem;
import io.dentall.totoro.business.service.nhi.metric.vm.SpecialTreatmentItemVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpecialTreatmentMapper {

    SpecialTreatmentMapper INSTANCE = Mappers.getMapper(SpecialTreatmentMapper.class);

    SpecialTreatmentItemVM mapToVM(SpecialTreatmentItem vm);
}
