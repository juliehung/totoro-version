package io.dentall.totoro.service;

import io.dentall.totoro.domain.Image;
import io.dentall.totoro.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Save a image.
     *
     * @param image the entity to save
     * @return the persisted entity
     */
    public Image save(Image image) {
        log.debug("Request to save Image : {}", image);
        return imageRepository.save(image);
    }

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Image> findAll() {
        log.debug("Request to get all Images");
        return imageRepository.findAll();
    }


    /**
     * Get one image by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Image> findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        return imageRepository.findById(id);
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.deleteById(id);
    }
}
