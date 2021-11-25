package io.dentall.totoro.service;

import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Optional.ofNullable;

@Service
public class FakeImageGCSService extends ImageGcsBusinessService {
    public FakeImageGCSService(ImageRepository imageRepository) {
        super(null, "fakeBucket", "fakeClinicName");
    }

    @Override
    public String createImagePath(Long patientId) {
        return ofNullable(patientId).map(Object::toString).orElse("");
    }

    @Override
    public void uploadFile(String remotePath, String remoteFileName, byte[] content, String contentType) throws IOException {
        // do nothing
    }

    @Override
    public void uploadFile(String remotePath, String remoteFileName, InputStream inputStream, String contentType) throws IOException {
        // do nothing
    }

    @Override
    public void deleteFile(String remotePath, String remoteFileName) {
        // do nothing
    }

    @Override
    public String getUrlForDownload() {
        return "http://fake.url/fakeBucket/";
    }
}
