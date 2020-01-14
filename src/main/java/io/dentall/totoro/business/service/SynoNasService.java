package io.dentall.totoro.business.service;

import io.dentall.totoro.business.vm.SynoNasSessionV6;
import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.time.Instant;

@Profile({"ftp", "synoNas"})
@Service(value = "synoNasService")
@Transactional
public class SynoNasService extends ImageBusinessService {

    private Logger logger = LoggerFactory.getLogger(SynoNasService.class);

    private static final String SYNO_NAS_AUTH_URL = System.getenv("TTR_IMAGE_REPOSITORY_SYNO_NAS_AUTH_URL");

    private static final String SYNO_NAS_FETCH_URL = System.getenv("TTR_IMAGE_REPOSITORY_SYNO_NAS_FETCH_URL");

    private static final String SYNO_NAS_LOGOUT_URL = System.getenv("TTR_IMAGE_REPOSITORY_SYNO_NAS_LOGOUT_URL");

    private static final String REMOTE_ORIGIN_FILE_SUFFIX = "_origin";

    private static final String REMOTE_MEDIUM_FILE_SUFFIX = "_medium";

    private static final String REMOTE_ORIGIN_FILE_EXTENSION = ".png";

    private static final String REMOTE_MEDIUM_FILE_EXTENSION = ".png";

    private final ImageRepository imageRepository;

    private final RestTemplate restTemplate;

    private final EntityManager entityManager;

    public SynoNasService(
        FtpClientService ftpClientService,
        ImageRepository imageRepository,
        RestTemplate restTemplate,
        EntityManager entityManager
    ) {
        super(ftpClientService);
        this.imageRepository = imageRepository;
        this.restTemplate = restTemplate;
        this.entityManager = entityManager;
    }

    @Override
    public String createImagePath(Long patientId) {
        return ImageRepositoryConfiguration
            .BASIC_FOLDER_PATH
            .concat("/")
            .concat(patientId.toString())
            .concat("/");
    }

    @Override
    public String createOriginImageName(String fileName) {
        String basicFileName = Instant.now().toString();
        basicFileName = fileName.isEmpty()?basicFileName :basicFileName.concat("_").concat(fileName);

        return basicFileName
                .concat(REMOTE_ORIGIN_FILE_SUFFIX)
                .concat(REMOTE_ORIGIN_FILE_EXTENSION);
    }

    @Override
    public String createMediumImageName(String fileName) {
        String basicFileName = Instant.now().toString();
        basicFileName = fileName.isEmpty()?basicFileName :basicFileName.concat("_").concat(fileName);

        return basicFileName
                .concat(REMOTE_MEDIUM_FILE_SUFFIX)
                .concat(REMOTE_MEDIUM_FILE_EXTENSION);
    }

    @Override
    public Image createImage(Long patientId, String filePath, String fileName, String size, Long groupId) {
        Patient patient = entityManager.getReference(Patient.class, patientId);

        return imageRepository.save(new Image()
            .filePath(filePath)
            .fileName(fileName)
            .fetchUrl(SYNO_NAS_FETCH_URL
                .concat("&path=")
                .concat(filePath)
                .concat(fileName)
            )
            .size(size)
            .groupId(groupId)
            .patient(patient)
        );
    }

    @Override
    public String getSession() {
        SynoNasSessionV6 s = restTemplate.getForObject(SYNO_NAS_AUTH_URL, SynoNasSessionV6.class);

        if (s == null ||
            s.getData() == null ||
            s.getData().getSid() == null
        ) {
            throw new BadRequestAlertException("Can get session id from syno nas", "IMAGE_BUSINESS", "nonassession");
        }

        return s.getData().getSid();
    }

    @Override
    public void releaseSession() {
        restTemplate.getForObject(SYNO_NAS_LOGOUT_URL, SynoNasSessionV6.class);
    }
}
