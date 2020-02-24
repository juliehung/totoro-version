package io.dentall.totoro.business.service;

import io.dentall.totoro.repository.ImageRepository;
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
public class ImageFtpBusinessService extends ImageBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImageFtpBusinessService.class);

    private final FtpClientService ftpClientService;

    public ImageFtpBusinessService(ImageRepository imageRepository, FtpClientService ftpClientService) {
        super(imageRepository);
        this.ftpClientService = ftpClientService;
    }

    @Override
    public Map<String, String> getImageThumbnailsBySize(Long id, String size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getImageSizes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getImageThumbnailUrl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void uploadFile(String remotePath, String remoteFileName, InputStream inputStream) throws IOException {
        ftpClientService.connect();
        int replyCode = ftpClientService.upload(remotePath, remoteFileName, inputStream);
        if (replyCode == FtpClientService.FTP_COULD_NOT_CREATE_FILE) {
            ftpClientService.mkdir(remotePath);
            ftpClientService.upload(remotePath, remoteFileName, inputStream);
        }

        ftpClientService.disconnect();
    }

    @Override
    public int disconnect() {
        return ftpClientService.disconnect();
    }
}
