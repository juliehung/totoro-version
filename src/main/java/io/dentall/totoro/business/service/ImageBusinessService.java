package io.dentall.totoro.business.service;

import com.atlassian.core.util.thumbnail.Thumber;
import com.atlassian.core.util.thumbnail.Thumbnail;
import io.dentall.totoro.domain.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Profile("ftp")
@Service
@Transactional
public abstract class ImageBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImageBusinessService.class);

    private static final int MEDIUM_THUMBNAIL_WIDTH_HEIGHT = 200;

    private static final Thumber pngThumber = new Thumber(Thumbnail.MimeType.PNG);

    private final FtpClientService ftpClientService;

    public ImageBusinessService(FtpClientService ftpClientService) {
        this.ftpClientService = ftpClientService;
    }

    public abstract String createImagePath(Long patientId);

    public abstract String createOriginImageName(String fileName);

    public abstract String createMediumImageName(String fileName);

    public abstract Image createImage(Long patientId, String filePath, String fileName, String size, Long groupId);

    public abstract String getSession();

    public abstract void releaseSession();

    public InputStream getMediumImageInputStream(InputStream inputStream) throws IOException {
        BufferedImage thumbnail = pngThumber.scaleImage(MEDIUM_THUMBNAIL_WIDTH_HEIGHT, MEDIUM_THUMBNAIL_WIDTH_HEIGHT, inputStream);
        ByteArrayOutputStream thumbnailOutputStream = new ByteArrayOutputStream();
        ImageIO.write(thumbnail, "png", new ByteArrayOutputStream());

        return new ByteArrayInputStream(thumbnailOutputStream.toByteArray());
    }

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

    public enum Size {
        ORIGIN, MEDIUM
    }
}
