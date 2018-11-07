package io.dentall.totoro.service;

import io.dentall.totoro.config.ResourceConfiguration;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.security.SecurityUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

/**
 * Service class for managing images.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final UserRepository userRepository;

    public ImageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<String> storeAvatar(MultipartFile file) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .flatMap(user -> {
                Path imageDir = Paths.get(ResourceConfiguration.IMAGE_FILE_BASE);
                String filename = StringUtils.cleanPath(user.getLogin() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
                try {
                    Files.copy(file.getInputStream(), imageDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                    user.setImageUrl(ResourceConfiguration.IMAGE_URL_BASE + filename);
                    log.debug("Set ImageUrl for User: {}", user);

                    return Optional.of(user.getImageUrl());
                } catch (IOException e) {
                    log.warn("ImageUrl could not be set for User({}): {}", user.getLogin(), e.getMessage());

                    return Optional.empty();
                }
            });
    }
}
