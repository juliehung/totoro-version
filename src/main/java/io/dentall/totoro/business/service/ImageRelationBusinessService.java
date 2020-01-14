package io.dentall.totoro.business.service;

import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.ImageRelation;
import io.dentall.totoro.repository.ImageRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class ImageRelationBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImageRelationBusinessService.class);

    private final EntityManager entityManager;

    private final ImageRelationRepository imageRelationRepository;

    public ImageRelationBusinessService(EntityManager entityManager, ImageRelationRepository imageRelationRepository) {
        this.entityManager = entityManager;
        this.imageRelationRepository = imageRelationRepository;
    }

    public ImageRelation createImageRelation(ImageRelation imageRelation) {
        Image image = entityManager.getReference(Image.class, imageRelation.getImage().getId());

        return imageRelationRepository.save(new ImageRelation()
            .domain(imageRelation.getDomain())
            .domainId(imageRelation.getDomainId())
            .image(image)
        );
    }
}
