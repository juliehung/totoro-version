package io.dentall.totoro.aop;

import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import io.dentall.totoro.repository.ImageRelationRepository;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.repository.PatientDocumentRepository;
import io.dentall.totoro.service.ConfigurationMapService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Aspect
public class PatientDocumentAspect {

    private final static Logger log = LoggerFactory.getLogger(PatientDocumentAspect.class);

    private final ImageRelationBusinessService imageRelationBusinessService;

    private final ImageBusinessService imageBusinessService;

    private final ImageRepository imageRepository;

    private final ImageRelationRepository imageRelationRepository;

    private final PatientDocumentRepository patientDocumentRepository;

    private final ConfigurationMapService configurationMapService;

    private final String ClinicStorageUsedSizeKey = "clinic.storage.used-size";

    public PatientDocumentAspect(ImageRelationBusinessService imageRelationBusinessService, ImageBusinessService imageBusinessService, ImageRepository imageRepository, ImageRelationRepository imageRelationRepository, PatientDocumentRepository patientDocumentRepository, ConfigurationMapService configurationMapService) {
        this.imageRelationBusinessService = imageRelationBusinessService;
        this.imageBusinessService = imageBusinessService;
        this.imageRepository = imageRepository;
        this.imageRelationRepository = imageRelationRepository;
        this.patientDocumentRepository = patientDocumentRepository;
        this.configurationMapService = configurationMapService;
    }

    @Pointcut("execution(public io.dentall.totoro.domain.PatientDocument io.dentall.totoro.service.PatientDocumentService.createDocument(..))")
    public void createDocument() {
    }

    @Pointcut("execution(public io.dentall.totoro.domain.PatientDocument io.dentall.totoro.service.PatientDocumentService.deleteDocument(..))")
    public void deleteDocument() {
    }

    @Pointcut("execution(public io.dentall.totoro.domain.PatientDocument io.dentall.totoro.service.PatientDocumentService.updateDocument(..)) && args(patientId,patientDocumentModified)")
    public void updateDocument(Long patientId, PatientDocument patientDocumentModified) {
    }

    @AfterReturning(pointcut = "createDocument()", returning = "patientDocument")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createDocumentPostProcess(PatientDocument patientDocument) {
        log.debug(patientDocument.toString());
        try {
            ConfigurationMap clinicStorageUsedSize = configurationMapService.findByKeyOrCreated(ClinicStorageUsedSizeKey);
            String oldSizeStr = clinicStorageUsedSize.getConfigValue();
            long fileSize = patientDocument.getDocument().getFileSize();
            long newSize = 0;

            if (isNull(oldSizeStr) || isBlank(oldSizeStr)) {
                newSize = fileSize;
            } else {
                newSize = parseLong(oldSizeStr) + fileSize;
            }

            clinicStorageUsedSize.setConfigValue(valueOf(newSize));
            configurationMapService.save(clinicStorageUsedSize);
        } catch (Exception e) {
            log.error("Calculate clinic storage size fails", e);
        }
    }

    @AfterReturning(pointcut = "deleteDocument()", returning = "patientDocument")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteDocumentPostProcess(PatientDocument patientDocument){
        log.debug(patientDocument.toString());
        try {
            ConfigurationMap clinicStorageUsedSize = configurationMapService.findByKeyOrCreated(ClinicStorageUsedSizeKey);
            String oldSizeStr = clinicStorageUsedSize.getConfigValue();
            long fileSize = patientDocument.getDocument().getFileSize();
            long newSize = 0;

            if (!isNull(oldSizeStr) && !isBlank(oldSizeStr)) {
                long oldSize = parseLong(oldSizeStr);
                if (oldSize >= fileSize) {
                    newSize = oldSize - fileSize;
                }
            }

            clinicStorageUsedSize.setConfigValue(valueOf(newSize));
            configurationMapService.save(clinicStorageUsedSize);
        } catch (Exception e) {
            log.error("Calculate clinic storage size fails", e);
        }
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

        log.debug(image.toString());

        // 2022.01.11 新版檔案管理，允許檔案可以只綁定病人，所以當沒有disposalId時，就不回寫image_relation資料表
        if (nonNull(patientDocument.getDisposal()) && nonNull(patientDocument.getDisposal().getId())) {
            ImageRelation imageRelation = new ImageRelation();
            imageRelation.setImage(image);
            imageRelation.setDomain(ImageRelationDomain.DISPOSAL);
            imageRelation.setDomainId(patientDocument.getDisposal().getId());
            imageRelation = imageRelationBusinessService.createImageRelation(imageRelation);
            log.debug(imageRelation.toString());
        }
    }

    /**
     * 2021.12.13 一併將舊資料表(image、image_relation)的資料刪除
     */
    @AfterReturning(pointcut = "deleteDocument()", returning = "patientDocument")
    @Transactional
    public void deleteImageRelation(PatientDocument patientDocument) {
        log.debug(patientDocument.toString());
        Long patientId = patientDocument.getPatientId();

        String remotePath = patientDocument.getDocument().getFilePath();
        String remoteFileName = patientDocument.getDocument().getFileName();
        List<Image> images = imageRepository.findImagesByPatientIdAndFilePathAndFileName(patientId, remotePath, remoteFileName);

        // 只在找到單一筆資料下才刪除舊資料
        if (images.size() == 1) {
            Image image = images.get(0);

            if (nonNull(patientDocument.getDisposal()) && nonNull(patientDocument.getDisposal().getId())) {
                // 2022.01.11 新版檔案管理，允許檔案可以只綁定病人，所以當沒有disposalId時，就不用一併刪除image_relation相關資料
                Long disposalId = patientDocument.getDisposal().getId();
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
            } else {
                // 2022.01.11 沒有image_relation資料，就直接刪除image資料
                imageRepository.delete(image);
            }
        } else if (images.size() > 1) {
            String idList = images.stream().map(Image::getId).map(String::valueOf).collect(joining(","));
            log.warn("Multi Image record found, ids : {}", idList);
        }
    }

    @Around(value = "updateDocument(patientId, patientDocumentModified)")
    @Transactional
    public Object updateImageRelation(ProceedingJoinPoint joinPoint, Long patientId, PatientDocument patientDocumentModified) throws Throwable {
        log.debug(patientDocumentModified.toString());

        Optional<PatientDocument> patientDocumentOriginOptional =
            nonNull(patientDocumentModified.getId()) ? patientDocumentRepository.findById(patientDocumentModified.getId()) : Optional.empty();

        if (patientDocumentOriginOptional.isPresent()) {
            PatientDocument patientDocument = patientDocumentOriginOptional.get();
            String remotePath = patientDocument.getDocument().getFilePath();
            String remoteFileName = patientDocument.getDocument().getFileName();
            List<Image> images = imageRepository.findImagesByPatientIdAndFilePathAndFileName(patientId, remotePath, remoteFileName);

            if (images.size() == 1) {
                Image image = images.get(0);
                PatientDocumentDisposal patientDocumentDisposal = patientDocument.getDisposal();

                if (nonNull(patientDocumentModified.getDisposal()) &&
                    nonNull(patientDocumentModified.getDisposal().getId()) &&
                    nonNull(patientDocumentDisposal) &&
                    !patientDocumentModified.getDisposal().getId().equals(patientDocumentDisposal.getId())) {
                    // 原本有處置單，更新資料中有處置單且非原本的處置單，所以要把image_relation的domain_id更新為新的disposalId

                    long disposalId = patientDocumentDisposal.getId();
                    List<ImageRelation> imageRelations =
                        imageRelationRepository.findImageRelationsByDomainAndDomainIdAndImage_IdAndImage_Patient_Id(ImageRelationDomain.DISPOSAL, disposalId, image.getId(), patientId);

                    if (imageRelations.size() == 1) {
                        ImageRelation imageRelation = imageRelations.get(0);
                        imageRelation.setDomainId(patientDocumentModified.getDisposal().getId());
                        log.debug(imageRelation.toString());
                        log.debug(image.toString());
                        imageRelationRepository.save(imageRelation);
                    } else if (imageRelations.size() > 1) {
                        String idList = imageRelations.stream().map(ImageRelation::getId).map(String::valueOf).collect(joining(","));
                        log.warn("Multi ImageRelation record found, ids : {}", idList);
                    }
                } else if (nonNull(patientDocumentModified.getDisposal()) &&
                    nonNull(patientDocumentModified.getDisposal().getId()) &&
                    isNull(patientDocumentDisposal)) {
                    // 原本沒有處置單，更新資料中有處置單，所以新增image_relation資料

                    ImageRelation imageRelation = new ImageRelation();
                    imageRelation.setImage(image);
                    imageRelation.setDomain(ImageRelationDomain.DISPOSAL);
                    imageRelation.setDomainId(patientDocumentModified.getDisposal().getId());
                    imageRelation = imageRelationBusinessService.createImageRelation(imageRelation);
                    log.debug(imageRelation.toString());
                } else if ((isNull(patientDocumentModified.getDisposal()) || isNull(patientDocumentModified.getDisposal().getId())) &&
                    nonNull(patientDocumentDisposal)) {
                    // 原本有處置單，更新資料中沒有處置單，所以刪除image_relation資料

                    long disposalId = patientDocumentDisposal.getId();
                    List<ImageRelation> imageRelations =
                        imageRelationRepository.findImageRelationsByDomainAndDomainIdAndImage_IdAndImage_Patient_Id(ImageRelationDomain.DISPOSAL, disposalId, image.getId(), patientId);

                    if (imageRelations.size() == 1) {
                        ImageRelation imageRelation = imageRelations.get(0);
                        imageRelationRepository.delete(imageRelation);
                    } else if (imageRelations.size() > 1) {
                        String idList = imageRelations.stream().map(ImageRelation::getId).map(String::valueOf).collect(joining(","));
                        log.warn("Multi ImageRelation record found, ids : {}", idList);
                    }
                }
            } else if (images.size() > 1) {
                String idList = images.stream().map(Image::getId).map(String::valueOf).collect(joining(","));
                log.warn("Multi Image record found, ids : {}", idList);
            }
        }

        return joinPoint.proceed();
    }
}
