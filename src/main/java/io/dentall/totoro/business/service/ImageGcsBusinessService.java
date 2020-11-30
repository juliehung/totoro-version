package io.dentall.totoro.business.service;

import com.google.cloud.storage.*;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Profile("img-gcs")
@Service
public class ImageGcsBusinessService extends ImageBusinessService {

    private final Logger logger = LoggerFactory.getLogger(ImageGcsBusinessService.class);

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    private final String BUCKET_NAME;

    private final String GCS_BASE_URL;

    public ImageGcsBusinessService(ImageRepository imageRepository, @Value("${gcp.bucket-name}") String bucketName) {
        super(imageRepository);
        BUCKET_NAME = bucketName;
        GCS_BASE_URL = "https://storage.googleapis.com/" + bucketName + "/";
    }


    /**
     * 假的 thumbnail 回傳都一統一為 gcsUrl + path + fileName
     * e.g.
     * size = "median,giant"
     * {
     *     "original": "https://his.dentall.some.gcp.storage/cp/123/hello.png",
     * }
     * @param host 保持 null, img-host 專用
     * @param id image id
     * @param size 保持 null, img-host 專用
     */
    @Override
    public Map<String, String> getImageThumbnailsBySize(String host, Long id, String size) {
        Image image = getImageById(id);
        String url = GCS_BASE_URL
            .concat(image.getFilePath())
            .concat(image.getFileName());

        if (size == null) {
            // default thumbnail size
            return Collections.singletonMap(Size.original.toString(), url);
        } else {
            return Arrays.stream(size.toLowerCase().split(","))
                .collect(Collectors.toMap(Function.identity(), it -> url));
        }
    }

    @Override
    public List<String> getImageSizes() {
        return Arrays.stream(ImageGcsBusinessService.Size.values()).map(Enum::toString).collect(Collectors.toList());
    }

    @Override
    public String getImageThumbnailUrl(String host) {
        return GCS_BASE_URL;
    }

    @Override
    public void uploadFile(String remotePath, String remoteFileName, InputStream inputStream, String contentType) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, remotePath.concat(remoteFileName));
        BlobInfo blobInfo = BlobInfo
            .newBuilder(blobId)
            .setContentType(contentType)
            .setAcl(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
            .build();
        storage.create(blobInfo, IOUtils.toByteArray(inputStream));
    }

    @Override
    public int disconnect() {
        return 200;
    }

    private enum Size {
        original
    }
}
