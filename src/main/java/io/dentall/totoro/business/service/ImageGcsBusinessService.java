package io.dentall.totoro.business.service;

import com.google.cloud.storage.*;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
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

    /**
     * GOOGLE API url for make api call to communicate with GCS
     */
    private final String GCS_BASE_URL;

    /**
     * GOOGLE url for direct access files from GCS with authentication.
     * This mechanism is unsafe need to impl with new method to do so.
     * Or maybe there is a way to authenticate user to access files.
     */
    private final String READ_GCS_BASE_URL;

    private final String CLINIC_NAME;

    private final String AVATAR_FILE_PATH_FORMAT;

    public ImageGcsBusinessService(
        ImageRepository imageRepository,
        @Value("${gcp.bucket-name}") String bucketName,
        @Value("#{environment.IMAGE_BASIC_FOLDER_PATH}") String clinicName
    ) {
        super(imageRepository);
        BUCKET_NAME = bucketName;
        GCS_BASE_URL = "https://storage.googleapis.com/" + bucketName + "/";
        READ_GCS_BASE_URL = "https://storage.cloud.google.com/" + bucketName + "/";
        CLINIC_NAME = clinicName;
        AVATAR_FILE_PATH_FORMAT = clinicName.concat("/").concat("users/%d/");
    }


    /**
     * 並不會產生任何 thumbnail，其結果皆回傳都一統一為 gcsUrl + path + fileName
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

    public void uploadFile(String remotePath, String remoteFileName, byte[] content, String contentType) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, remotePath.concat(remoteFileName));
        BlobInfo blobInfo = BlobInfo
            .newBuilder(blobId)
            .setContentType(contentType)
            .setAcl(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
            .build();
        storage.create(blobInfo, content);
    }

    public void deleteFile(String remoteFilePath, String remoteFileName) throws Exception {
        BlobId blobId = BlobId.of(BUCKET_NAME, remoteFilePath.concat(remoteFileName));
        storage.delete(blobId);
    }

    @Override
    public int disconnect() {
        return 200;
    }

    private enum Size {
        original
    }

    public String getUrlForUpload() {
        return this.GCS_BASE_URL;
    }

    public String getUrlForDownload() {
        return this.GCS_BASE_URL;
    }

    public String getClinicName() {
        return this.CLINIC_NAME;
    }

    public String uploadUserAvatar(
        Long id,
        byte[] content
    ) throws Exception {
        String filePath = String.format(
            AVATAR_FILE_PATH_FORMAT,
            id
        );
        String fileName = this.getAvatarFileName();

        this.uploadFile(
            filePath,
            fileName,
            content,
            MediaType.IMAGE_PNG_VALUE
        );

        return fileName;
    }

    // id must be user's id
    public String getAvatarFilePath(Long id) {
        return String.format(
            AVATAR_FILE_PATH_FORMAT,
            id
        );
    }

    public String getAvatarFileName() {
        return "avatar_".concat(Instant.now().toString()).concat(".png");
    }
}
