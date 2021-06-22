package io.dentall.totoro.service;

import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.PatientRepository;
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
 * Service class for managing avatar.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AvatarService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final UserRepository userRepository;

    private final PatientRepository patientRepository;

    public AvatarService(
        UserRepository userRepository,
        PatientRepository patientRepository
    ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    public Optional<String> storeUserAvatar(MultipartFile file) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .flatMap(user -> {
                try {
                    user.getExtendUser().setAvatar(file.getBytes());
                    user.getExtendUser().setAvatarContentType(file.getContentType());
                    log.debug("Set Avatar for ExtendUser: {}", user.getExtendUser());

                    user.setImageUrl(file.getOriginalFilename());
                    log.debug("Set ImageUrl for User: {}", user);

                    return Optional.of(user.getImageUrl());
                } catch (IOException e) {
                    log.warn("ImageUrl could not be set for User({}): {}", user.getLogin(), e.getMessage());

                    return Optional.empty();
                }
            });
    }

    public Optional<Patient> storePatientAvatar(Long id, MultipartFile file) throws IOException {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patient.get().setAvatar(file.getBytes());
            patient.get().setAvatarContentType(file.getContentType());
            log.debug("Set Avatar for Patient");

            return patient;
        } else {
            return Optional.empty();
        }
    }
}