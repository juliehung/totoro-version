package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Document;
import io.dentall.totoro.domain.PatientDocument;
import io.dentall.totoro.web.rest.vm.DocumentVM;
import io.dentall.totoro.web.rest.vm.PatientDocumentVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public abstract class PatientDocumentMapperDecorator implements PatientDocumentMapper {

    @Autowired
    @Qualifier("delegate")
    private PatientDocumentMapper delegate;

    @Value("${gcp.gcs-base-url}")
    String gcsBaseUrl;

    @Value("${gcp.bucket-name}")
    String bucketName;

    @Override
    public PatientDocumentVM mapToPatientDocumentVM(PatientDocument patientDocument) {
        PatientDocumentVM patientDocumentVM = delegate.mapToPatientDocumentVM(patientDocument);
        patientDocumentVM.setDocument(mapToDocumentVM(patientDocument.getDocument()));
        return patientDocumentVM;
    }

    @Override
    public DocumentVM mapToDocumentVM(Document document) {
        DocumentVM documentVM = delegate.mapToDocumentVM(document);
        documentVM.setUrl(getUrl());
        return documentVM;
    }

    String getUrl() {
        return gcsBaseUrl + "/" + bucketName + "/";
    }
}
