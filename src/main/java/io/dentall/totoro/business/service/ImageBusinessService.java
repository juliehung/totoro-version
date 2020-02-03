package io.dentall.totoro.business.service;

import io.dentall.totoro.domain.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Profile("ftp")
@Service
public abstract class ImageBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImageBusinessService.class);

    private final FtpClientService ftpClientService;

    public ImageBusinessService(FtpClientService ftpClientService) {
        this.ftpClientService = ftpClientService;
    }

    public abstract String createImagePath(Long patientId);

    public abstract Image createImage(Long patientId, String filePath, String fileName);

    public abstract Image getImageById(Long id);

    public abstract Map<String, String> getImageThumbnailsBySize(Long id, String size);

    public abstract List<String> getImageSizes();

    public abstract String getImageThumbnailUrl();

    public void uploadFile(String remotePath, String remoteFileName, InputStream inputStream) throws IOException {
        ftpClientService.connect();
        int replyCode = ftpClientService.upload(remotePath, remoteFileName, inputStream);
        if (replyCode == FtpClientService.FTP_COULD_NOT_CREATE_FILE) {
            ftpClientService.mkdir(remotePath);
            ftpClientService.upload(remotePath, remoteFileName, inputStream);
        }

        ftpClientService.disconnect();
    }

    public int disconnect() {
        return ftpClientService.disconnect();
    }
}
