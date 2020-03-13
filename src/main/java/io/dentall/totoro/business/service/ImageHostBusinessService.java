package io.dentall.totoro.business.service;

import io.dentall.totoro.domain.Image;
import io.dentall.totoro.repository.ImageRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Profile("img-host")
@Service
public class ImageHostBusinessService extends ImageBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImageHostBusinessService.class);

    private final Environment env;

    public ImageHostBusinessService(ImageRepository imageRepository, Environment env) {
        super(imageRepository);
        this.env = env;
    }

    @Override
    public Map<String, String> getImageThumbnailsBySize(String host, Long id, String size) {
        Image image = getImageById(id);
        String url = getImageThumbnailUrl(host)
            .concat("path=")
            .concat(image.getFilePath())
            .concat(image.getFileName())
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
        return Arrays.stream(ImageHostBusinessService.Size.values()).map(Enum::toString).collect(Collectors.toList());
    }

    @Override
    public String getImageThumbnailUrl(String host) {
        if (host == null) {
            try {
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                String serverPort = env.getProperty("server.port");

                return "http://".concat(hostAddress).concat(":").concat(Objects.requireNonNull(serverPort)).concat("/api/images/host?");
            } catch (UnknownHostException e) {
                logger.warn("The host name could not be determined");

                return null;
            }
        } else {
            int index = host.lastIndexOf(":");
            if (index > -1) {
                return "http://".concat(host.substring(0, index)).concat(":").concat(host.substring(index + 1)).concat("/api/images/host?");
            } else {
                return "http://".concat(host).concat("/api/images/host?");
            }
        }
    }

    @Override
    public void uploadFile(String remotePath, String remoteFileName, InputStream inputStream, String contentType) throws IOException {
        Path dir = Paths.get(remotePath);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
            logger.debug("new directory created: {}", dir);
        }

        Path targetFile = Paths.get(remotePath.concat(remoteFileName));
        Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public int disconnect() {
        return 200;
    }

    public byte[] getImageByPathAndSize(String path, String size) throws IOException {
        Path targetFile = Paths.get(path);
        if (size != null && size.toLowerCase().equals(Size.medium.toString())) {
            BufferedImage bufferedImage = Thumbnails.of(targetFile.toFile()).scale(0.5).asBufferedImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String ext = FilenameUtils.getExtension(path);
            ImageIO.write(bufferedImage, ext, baos);

            return baos.toByteArray();
        }

        // original
        return Files.readAllBytes(targetFile);
    }

    private enum Size {
        medium, original
    }
}
