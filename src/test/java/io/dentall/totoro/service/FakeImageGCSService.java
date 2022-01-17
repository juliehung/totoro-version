package io.dentall.totoro.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import static java.util.Optional.ofNullable;

@Service
public class FakeImageGCSService extends ImageGcsBusinessService {
    public FakeImageGCSService(ImageRepository imageRepository) {
        super(imageRepository, "fakeBucket", "fakeClinicName");
    }

    @Override
    public String createImagePath(Long patientId) {
        return ofNullable(patientId).map(Object::toString).orElse("");
    }

    @Override
    public void uploadFile(String remotePath, String remoteFileName, InputStream inputStream, String contentType) throws IOException {
        // do nothing
    }

    @Override
    public Blob uploadFile(String remotePath, String remoteFileName, byte[] content, String contentType) {
        return null;
    }

    public void deleteFile(String remotePath, String remoteFileName) {
        // do nothing
    }

    @Override
    public String getUrlForDownload() {
        return "http://fake.url/fakeBucket/";
    }

    @Override
    public Page<Blob> listFileByPrefix(String prefix) {
        return new Page<Blob>() {
            @Override
            public boolean hasNextPage() {
                return false;
            }

            @Override
            public String getNextPageToken() {
                return null;
            }

            @Override
            public Page<Blob> getNextPage() {
                return null;
            }

            @Override
            public Iterable<Blob> iterateAll() {
                return Collections.emptyList();
            }

            @Override
            public Iterable<Blob> getValues() {
                return null;
            }
        };
    }

    @Override
    public Blob getFile(String name) {
        return null;
    }
}
