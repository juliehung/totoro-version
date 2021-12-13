package io.dentall.totoro.config;

import io.dentall.totoro.aop.PatientDocumentAspect;
import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.repository.ImageRelationRepository;
import io.dentall.totoro.repository.ImageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

    @Bean
    public PatientDocumentAspect patientDocumentAspect(
        ImageRelationBusinessService imageRelationBusinessService,
        ImageBusinessService imageBusinessService,
        ImageRepository imageRepository,
        ImageRelationRepository imageRelationRepository) {
        return new PatientDocumentAspect(imageRelationBusinessService, imageBusinessService, imageRepository, imageRelationRepository);
    }

}
