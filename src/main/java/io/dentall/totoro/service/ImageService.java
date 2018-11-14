package io.dentall.totoro.service;

import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                try {
                    user.getExtendUser().setAvatar(file.getBytes());
                    user.getExtendUser().setAvatarContentType(file.getContentType());
                    log.debug("Set Avatar for ExtendUser");

                    user.setImageUrl(file.getOriginalFilename());
                    log.debug("Set ImageUrl for User: {}", user);

                    return Optional.of(user.getImageUrl());
                } catch (IOException e) {
                    log.warn("ImageUrl could not be set for User({}): {}", user.getLogin(), e.getMessage());

                    return Optional.empty();
                }
            });
    }
}
