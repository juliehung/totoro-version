package io.dentall.totoro.business.service;

import io.dentall.totoro.business.vm.SynoNasFileStation;
import io.dentall.totoro.business.vm.SynoNasSessionV6;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.Status;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Profile({"ftp", "synoNas"})
@Service(value = "synoNasService")
public class SynoNasService extends ImageFtpBusinessService {

    private Logger logger = LoggerFactory.getLogger(SynoNasService.class);

    private static final String SYNO_NAS_AUTH_URL = System.getenv("SYNO_NAS_AUTH_URL");

    private static final String SYNO_NAS_FETCH_URL = System.getenv("SYNO_NAS_FETCH_URL");

    private static final String SYNO_FILESTATION_INFO = System.getenv("SYNO_FILESTATION_INFO");

    private final RestTemplate restTemplate;

    private String sid;

    public SynoNasService(ImageRepository imageRepository, FtpClientService ftpClientService, RestTemplate restTemplate) {
        super(imageRepository, ftpClientService);
        this.restTemplate = restTemplate;
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

    @Override
    public String getImageThumbnailUrl() {
        return SYNO_NAS_FETCH_URL.concat("&_sid=").concat(getSession());
    }

    private String getSession() {
        if (sid == null || !isSessionAlive()) {
            SynoNasSessionV6 s = restTemplate.getForObject(SYNO_NAS_AUTH_URL, SynoNasSessionV6.class);
            if (s == null ||
                s.getData() == null ||
                s.getData().getSid() == null
            ) {
                throw new BadRequestAlertException("Can not get session id from syno nas", "IMAGE_BUSINESS", "nonassession");
            }

            return s.getData().getSid();
        }

        return sid;
    }

    private boolean isSessionAlive() {
        SynoNasFileStation s = restTemplate.getForObject(SYNO_FILESTATION_INFO.concat("&_sid=").concat(sid), SynoNasFileStation.class);
        if (s == null) {
            throw new ProblemUtil("SynoNasFileStation object is null", Status.INTERNAL_SERVER_ERROR);
        }

        return s.isSuccess();
    }

    private enum Size {
        small, medium, large, original
    }
}
