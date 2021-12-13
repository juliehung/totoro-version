package io.dentall.totoro.aop;

import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.domain.PatientDocument;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import io.dentall.totoro.repository.ImageRelationRepository;
import io.dentall.totoro.repository.ImageRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Aspect
public class PatientDocumentAspect {

    private final static Logger log = LoggerFactory.getLogger(PatientDocumentAspect.class);

    private final ImageRelationBusinessService imageRelationBusinessService;

    private final ImageBusinessService imageBusinessService;

    private final ImageRepository imageRepository;

    private final ImageRelationRepository imageRelationRepository;

    public PatientDocumentAspect(ImageRelationBusinessService imageRelationBusinessService, ImageBusinessService imageBusinessService, ImageRepository imageRepository, ImageRelationRepository imageRelationRepository) {
        this.imageRelationBusinessService = imageRelationBusinessService;
        this.imageBusinessService = imageBusinessService;
        this.imageRepository = imageRepository;
        this.imageRelationRepository = imageRelationRepository;
    }

    @Pointcut("execution(public io.dentall.totoro.domain.PatientDocument io.dentall.totoro.service.PatientDocumentService.createDocument(..))")
    public void createDocument() {
    }

    @Pointcut("execution(public io.dentall.totoro.domain.PatientDocument io.dentall.totoro.service.PatientDocumentService.deleteDocument(..))")
    public void deleteDocument() {

    }

    /**
     * 2021.12.13 將檔案資料一併寫回舊資料表(image、image_relation)，如果退版後，使用舊API能繼續拿到退版前使用新API寫入的資料
     */
    @AfterReturning(pointcut = "createDocument()", returning = "patientDocument")
    @Transactional
    public void createImageRelation(PatientDocument patientDocument) {
        log.debug(patientDocument.toString());
        Long patientId = patientDocument.getPatientId();
        String remotePath = patientDocument.getDocument().getFilePath();
        String remoteFileName = patientDocument.getDocument().getFileName();
        Image image = imageBusinessService.createImage(patientId, remotePath, remoteFileName);

        ImageRelation imageRelation = new ImageRelation();
        imageRelation.setImage(image);
        imageRelation.setDomain(ImageRelationDomain.DISPOSAL);
        imageRelation.setDomainId(patientDocument.getDisposal().getId());
        imageRelation = imageRelationBusinessService.createImageRelation(imageRelation);
        log.debug(image.toString());
        log.debug(imageRelation.toString());
    }

    /**
     * 2021.12.13 一併將舊資料表(image、image_relation)的資料刪除
     */
    @AfterReturning(pointcut = "deleteDocument()", returning = "patientDocument")
    @Transactional
    public void deleteImageRelation(PatientDocument patientDocument) {
        log.debug(patientDocument.toString());
        Long patientId = patientDocument.getPatientId();
        Long disposalId = patientDocument.getDisposal().getId();
        String remotePath = patientDocument.getDocument().getFilePath();
        String remoteFileName = patientDocument.getDocument().getFileName();
        List<Image> images = imageRepository.findImagesByPatientIdAndFilePathAndFileName(patientId, remotePath, remoteFileName);

        // 只在找到單一筆資料下才刪除舊資料
        if (images.size() == 1) {
            Image image = images.get(0);

            List<ImageRelation> imageRelations =
                imageRelationRepository.findImageRelationsByDomainAndDomainIdAndImage_IdAndImage_Patient_Id(ImageRelationDomain.DISPOSAL, disposalId, image.getId(), patientId);

            if (imageRelations.size() == 1) {
                ImageRelation imageRelation = imageRelations.get(0);
                log.debug(imageRelation.toString());
                log.debug(image.toString());
                imageRelationRepository.delete(imageRelation);
                imageRepository.delete(image);
            } else if (imageRelations.size() > 1) {
                String idList = imageRelations.stream().map(ImageRelation::getId).map(String::valueOf).collect(joining(","));
                log.warn("Multi ImageRelation record found, ids : {}", idList);
            }
        } else if (images.size() > 1) {
            String idList = images.stream().map(Image::getId).map(String::valueOf).collect(joining(","));
            log.warn("Multi Image record found, ids : {}", idList);
        }
    }
}
