package io.dentall.totoro.business.service;

import com.google.cloud.storage.*;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ImageGcsBusinessService.class);

    private Storage storage = StorageOptions.getDefaultInstance().getService();

    public ImageGcsBusinessService(ImageRepository imageRepository) {
        super(imageRepository);
    }

    @Override
    public Map<String, String> getImageThumbnailsBySize(String host, Long id, String size) {
        Image image = getImageById(id);
        String url = GcpConstants.GCS_BASE_URL
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
        return GcpConstants.GCS_BASE_URL;
    }

    @Override
    public void uploadFile(String remotePath, String remoteFileName, InputStream inputStream, String contentType) throws IOException {
        BlobId blobId = BlobId.of(GcpConstants.BUCKET_NAME, remotePath.concat(remoteFileName));
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