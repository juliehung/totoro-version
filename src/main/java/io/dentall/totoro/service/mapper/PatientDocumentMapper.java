package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Document;
import io.dentall.totoro.domain.PatientDocument;
import io.dentall.totoro.domain.PatientDocumentDisposal;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.web.rest.vm.DocumentVM;
import io.dentall.totoro.web.rest.vm.PatientDocumentDisposalVM;
import io.dentall.totoro.web.rest.vm.PatientDocumentVM;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(PatientDocumentMapperDecorator.class)
public interface PatientDocumentMapper {

    PatientDocumentMapper INSTANCE = Mappers.getMapper(PatientDocumentMapper.class);

    PatientDocumentVM mapToPatientDocumentVM(PatientDocument patientDocument);

    DocumentVM mapToDocumentVM(Document document);

    PatientDocumentDisposalVM mapToPatientDocumentDisposalVM(PatientDocumentDisposal patientDocumentDisposal);

    PatientDocumentDisposal mapToPatientDocumentDisposal(DisposalTable disposalTable);

}
