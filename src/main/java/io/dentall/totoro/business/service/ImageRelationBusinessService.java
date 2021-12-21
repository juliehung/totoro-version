package io.dentall.totoro.business.service;

import io.dentall.totoro.business.vm.ImageRelationPathVM;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import io.dentall.totoro.repository.ImageRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 2021.12.02 改使用 {@link io.dentall.totoro.service.PatientDocumentService} 進行操作
 */
@Deprecated
@Service
public class ImageRelationBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImageRelationBusinessService.class);

    private final EntityManager entityManager;

    private final ImageRelationRepository imageRelationRepository;

    public ImageRelationBusinessService(EntityManager entityManager, ImageRelationRepository imageRelationRepository) {
        this.entityManager = entityManager;
        this.imageRelationRepository = imageRelationRepository;
    }

    /**
     * 2021.12.02 改使用 {@link io.dentall.totoro.service.PatientDocumentService#createDocument(Long, Long, MultipartFile, String)}
     */
    @Deprecated
    @Transactional
    public ImageRelation createImageRelation(ImageRelation imageRelation) {
        Image image = entityManager.getReference(Image.class, imageRelation.getImage().getId());

        return imageRelationRepository.save(new ImageRelation()
            .domain(imageRelation.getDomain())
            .domainId(imageRelation.getDomainId())
            .image(image)
        );
    }

    /**
     * 2021.12.02 改使用 {@link io.dentall.totoro.service.PatientDocumentService#findDocument(Long, Long, String, Pageable)}
     */
    @Deprecated
    @Transactional(readOnly = true)
    public List<ImageRelationPathVM> getImageRelationPathsByDomain(ImageRelationDomain domain, Long domainId) {
        try (Stream<ImageRelation> imageRelations = imageRelationRepository.findDistinctImageByDomainAndDomainId(domain, domainId)) {
            return imageRelations
                .map(ImageRelationPathVM::new)
                .collect(Collectors.toList());
        }
    }

    /**
     * 2021.12.02 改使用 {@link io.dentall.totoro.service.PatientDocumentService#deleteDocument(Long, Long)}
     */
    @Deprecated
    @Transactional
    public void deleteById(Long id) {
        imageRelationRepository.deleteById(id);
    }
}
