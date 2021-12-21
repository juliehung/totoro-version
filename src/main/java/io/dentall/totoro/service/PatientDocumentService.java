package io.dentall.totoro.service;

import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.domain.Document;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.PatientDocument;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.mapper.PatientDocumentMapper;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static io.dentall.totoro.service.util.FileNameUtil.getExtension;
import static io.dentall.totoro.service.util.FileNameUtil.normalizeFileName;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Transactional
public class PatientDocumentService {
    private static final String ENTITY_NAME = "PATIENT_DOCUMENT";

    private final UserService userService;

    private final DisposalRepository disposalRepository;

    private final PatientDocumentRepository patientDocumentRepository;

    private final DocumentRepository documentRepository;

    private final PatientRepository patientRepository;

    private final HashtagService hashtagService;

    private final HashtagRepository hashtagRepository;

    private final PatientDocumentMapper patientDocumentMapper;

    private final Optional<ImageBusinessService> imageBusinessServiceOptional;

    public PatientDocumentService(
        UserService userService,
        DisposalRepository disposalRepository,
        PatientDocumentRepository patientDocumentRepository,
        DocumentRepository documentRepository,
        PatientRepository patientRepository,
        HashtagService hashtagService,
        HashtagRepository hashtagRepository,
        PatientDocumentMapper patientDocumentMapper,
        Optional<ImageBusinessService> imageBusinessServiceOptional) {

        this.userService = userService;
        this.disposalRepository = disposalRepository;
        this.patientDocumentRepository = patientDocumentRepository;
        this.documentRepository = documentRepository;
        this.patientRepository = patientRepository;
        this.hashtagService = hashtagService;
        this.hashtagRepository = hashtagRepository;
        this.patientDocumentMapper = patientDocumentMapper;
        this.imageBusinessServiceOptional = imageBusinessServiceOptional;
    }

    public PatientDocument createDocument(Long patientId, Long disposalId, MultipartFile file, String userName) throws IOException {
        if (isNull(patientId)) {
            throw new BadRequestAlertException("patientId does not provided", ENTITY_NAME, "patient.id.not.provided");
        }

        if (isNull(disposalId)) {
            throw new BadRequestAlertException("disposalId does not provided", ENTITY_NAME, "disposal.id.not.provided");
        }

        if (isNull(file) || file.isEmpty() || file.getSize() <= 0) {
            throw new BadRequestAlertException("Upload not include file or it is empty", ENTITY_NAME, "file.not.provided");
        }

        Optional<Patient> patientOptional = patientRepository.findById(patientId);

        if (!patientOptional.isPresent()) {
            throw new BadRequestAlertException("patient does not found", ENTITY_NAME, "patient.not.found");
        }

        Optional<DisposalTable> disposalTableOptional = disposalRepository.findDisposalByIdAndRegistration_Appointment_Patient_Id(disposalId, patientId);

        if (!disposalTableOptional.isPresent()) {
            throw new BadRequestAlertException("disposal is not found or disposal is not belong to patient", ENTITY_NAME, "disposal.not.found");
        }

        Instant now = Instant.now();
        Document document = new Document();
        document.setTitle(file.getOriginalFilename());
        document.setFilePath(getFilePath(patientId));
        document.setFileName(normalizeFileName(file.getOriginalFilename()));
        document.setFileRealName(file.getOriginalFilename());
        document.setFileExtension(getExtension(file.getOriginalFilename()));
        document.setFileSize(file.getSize());
        document.setUploadUser(userName);
        document.setUploadTime(now);
        document.setModifiedUser(userName);
        document.setModifiedTime(now);

        document = documentRepository.save(document);
        PatientDocument patientDocument = new PatientDocument();
        patientDocument.setPatientId(patientId);
        patientDocument.setDisposal(patientDocumentMapper.mapToPatientDocumentDisposal(disposalTableOptional.get()));
        patientDocument.setDocument(document);

        uploadFile(document, file);

        return patientDocumentRepository.save(patientDocument);
    }

    private void uploadFile(Document document, MultipartFile file) throws IOException {
        if (imageBusinessServiceOptional.isPresent()) {
            ImageBusinessService imageBusinessService = imageBusinessServiceOptional.get();
            try (InputStream is = file.getInputStream()) {
                imageBusinessService.uploadFile(document.getFilePath(), document.getFileName(), is, file.getContentType());
            }
        }
    }

    private String getFilePath(Long patientId) {
        return imageBusinessServiceOptional.map(service -> service.createImagePath(patientId)).orElse(String.valueOf(patientId));
    }

    @Transactional(readOnly = true)
    public List<PatientDocument> findDocument(Long patientId, Long disposalId, String search, Pageable pageable) {
        boolean searchForTag = !isBlank(search) && hashtagRepository.existsPatientDocumentTagByTagName(search);
        return patientDocumentRepository.findAll(patientDocumentRepository.specification(patientId, disposalId, search, searchForTag), pageable).getContent();
    }

    public PatientDocument updateDocument(Long patientId, PatientDocument patientDocumentModified) {
        if (isNull(patientDocumentModified.getId())) {
            throw new BadRequestAlertException("patient document id is not provided", ENTITY_NAME, "patientDocumentId.not.provided");
        }

        if (isNull(patientDocumentModified.getDisposal())) {
            throw new BadRequestAlertException("disposal is not provided", ENTITY_NAME, "disposal.not.provided");
        }

        if (isNull(patientDocumentModified.getDisposal().getId())) {
            throw new BadRequestAlertException("disposal id is not provided", ENTITY_NAME, "disposalId.not.provided");
        }

        Optional<PatientDocument> patientDocumentOptional = patientDocumentRepository.findById(patientDocumentModified.getId());

        if (!patientDocumentOptional.isPresent()) {
            throw new BadRequestAlertException("patient document is not found", ENTITY_NAME, "patientDocument.not.found");
        }

        PatientDocument patientDocumentOrigin = patientDocumentOptional.get();
        Document documentOrigin = patientDocumentOrigin.getDocument();

        if (!patientDocumentOrigin.getPatientId().equals(patientId)) {
            throw new BadRequestAlertException("patient id is not match", ENTITY_NAME, "patientId.not.match");
        }

        Optional<DisposalTable> disposalTable = disposalRepository.findDisposalByIdAndRegistration_Appointment_Patient_Id(patientDocumentModified.getDisposal().getId(), patientId);

        if (!disposalTable.isPresent()) {
            throw new BadRequestAlertException("disposal is not found or disposal is not belong to patient", ENTITY_NAME, "disposal.not.found");
        }

        String userName = userService.getUserWithAuthorities()
            .map(User::getFirstName)
            .orElseThrow(() -> new BadRequestAlertException("user id is not found", ENTITY_NAME, "userId.not.found"));

        // update PatientDocument
        patientDocumentOrigin.setDisposal(patientDocumentMapper.mapToPatientDocumentDisposal(disposalTable.get()));
        patientDocumentOrigin.setLastModifiedDate(Instant.now());

        // update Document
        ofNullable(patientDocumentModified.getDocument()).ifPresent(documentModified -> {
            String[] hashtagsOrigin = documentOrigin.getHashtags();
            String[] hashtagsModified = documentModified.getHashtags();

            hashtagService.processPatientDocumentTag(asList(hashtagsOrigin), asList(hashtagsModified));

            ofNullable(documentModified.getTitle()).ifPresent(documentOrigin::setTitle);
            ofNullable(documentModified.getDescription()).ifPresent(documentOrigin::setDescription);
            ofNullable(documentModified.getHashtags()).ifPresent(documentOrigin::setHashtags);
            documentOrigin.setModifiedUser(userName);
            documentOrigin.setModifiedTime(Instant.now());
        });

        return patientDocumentOrigin;
    }

    public PatientDocument deleteDocument(Long patientId, Long patientDocumentId) {
        if (patientDocumentId == null) {
            throw new BadRequestAlertException("patient document id is not provided", ENTITY_NAME, "patientDocumentId.not.provided");
        }

        Optional<PatientDocument> patientDocumentOptional = patientDocumentRepository.findById(patientDocumentId);

        if (!patientDocumentOptional.isPresent()) {
            throw new BadRequestAlertException("patient document is not found", ENTITY_NAME, "patientDocument.not.found");
        }

        PatientDocument patientDocument = patientDocumentOptional.get();

        if (!patientDocument.getPatientId().equals(patientId)) {
            throw new BadRequestAlertException("patient id is not match", ENTITY_NAME, "patientId.not.match");
        }

        patientDocumentRepository.deleteById(patientDocument.getId());
        documentRepository.deleteById(patientDocument.getDocument().getId());
        hashtagService.reducePatientDocumentTagReference(asList(patientDocument.getDocument().getHashtags()));
        deleteFile(patientDocument.getDocument());

        return patientDocument;
    }

    private void deleteFile(Document document) {
        if (imageBusinessServiceOptional.isPresent()) {
            ImageBusinessService imageBusinessService = imageBusinessServiceOptional.get();
            imageBusinessService.deleteFile(document.getFilePath(), document.getFileName());
        }
    }
}
