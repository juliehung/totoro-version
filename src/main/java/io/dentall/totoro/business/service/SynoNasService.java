package io.dentall.totoro.business.service;

import io.dentall.totoro.business.vm.SynoNasSessionV6;
import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Profile({"ftp", "synoNas"})
@Service(value = "synoNasService")
public class SynoNasService extends ImageBusinessService {

    private Logger logger = LoggerFactory.getLogger(SynoNasService.class);

    private static final String SYNO_NAS_AUTH_URL = System.getenv("TTR_IMAGE_REPOSITORY_SYNO_NAS_AUTH_URL");

    private static final String SYNO_NAS_FETCH_URL = System.getenv("TTR_IMAGE_REPOSITORY_SYNO_NAS_FETCH_URL");

    private final ImageRepository imageRepository;

    private final RestTemplate restTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public SynoNasService(
        FtpClientService ftpClientService,
        ImageRepository imageRepository,
        RestTemplate restTemplate
    ) {
        super(ftpClientService);
        this.imageRepository = imageRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public String createImagePath(Long patientId) {
        return ImageRepositoryConfiguration
            .BASIC_FOLDER_PATH
            .concat("/")
            .concat(patientId.toString())
            .concat("/");
    }

    @Transactional
    @Override
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
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> ProblemUtil.notFoundException("Image id: " + id));
    }

    @Override
    public Map<String, String> getImageThumbnailsBySize(Long id, String size) {
        Image image = getImageById(id);
        String url = SYNO_NAS_FETCH_URL
            .concat("&path=")
            .concat(image.getFilePath())
            .concat(image.getFileName())
            .concat("&_sid=")
            .concat(getSession())
            .concat("&size=");

        if (size == null) {
            // default thumbnail size
            return Collections.singletonMap(Size.medium.toString(), url.concat(Size.medium.toString()));
        } else {
            return Arrays.stream(size.toLowerCase().split(","))
                .collect(Collectors.toMap(Function.identity(), url::concat));
        }
    }

    @Override
    public List<String> getImageSizes() {
        return Arrays.stream(Size.values()).map(Enum::toString).collect(Collectors.toList());
    }

    private String getSession() {
        SynoNasSessionV6 s = restTemplate.getForObject(SYNO_NAS_AUTH_URL, SynoNasSessionV6.class);

        if (s == null ||
            s.getData() == null ||
            s.getData().getSid() == null
        ) {
            throw new BadRequestAlertException("Can get session id from syno nas", "IMAGE_BUSINESS", "nonassession");
        }

        return s.getData().getSid();
    }

    private enum Size {
        small, medium, large, original
    }
}
