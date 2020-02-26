package io.dentall.totoro.business.service;

import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public abstract class ImageBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImageBusinessService.class);

    private final String sep = System.getProperty("file.separator");

    @PersistenceContext
    private EntityManager entityManager;

    private final ImageRepository imageRepository;

    public ImageBusinessService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public abstract Map<String, String> getImageThumbnailsBySize(String host, Long id, String size);

    public abstract List<String> getImageSizes();

    public abstract String getImageThumbnailUrl(String host);

    public abstract void uploadFile(String remotePath, String remoteFileName, InputStream inputStream) throws IOException;

    public abstract int disconnect();

    public String createImagePath(Long patientId) {
        return ImageRepositoryConfiguration
            .BASIC_FOLDER_PATH
            .concat(sep)
            .concat(patientId.toString())
            .concat(sep);
    }

    @Transactional
    public Image createImage(Long patientId, String filePath, String fileName) {
        Patient patient = null;
        if (patientId != null) {
            patient = entityManager.getReference(Patient.class, patientId);
        }

        return imageRepository.save(new Image()
            .filePath(filePath)
            .fileName(fileName)
            .patient(patient)
        );
    }

    @Transactional(readOnly = true)
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> ProblemUtil.notFoundException("Image id: " + id));
    }
}
