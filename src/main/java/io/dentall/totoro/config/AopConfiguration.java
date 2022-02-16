package io.dentall.totoro.config;

import io.dentall.totoro.aop.PatientNhiStatusAspect;
import io.dentall.totoro.aop.ReportAspect;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.repository.ReportRecordRepository;
import io.dentall.totoro.aop.PatientDocumentAspect;
import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.business.service.ImageRelationBusinessService;
import io.dentall.totoro.repository.ImageRelationRepository;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.repository.PatientDocumentRepository;
import io.dentall.totoro.service.ConfigurationMapService;
import io.dentall.totoro.service.PatientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Optional;

@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

    @Bean
    public ReportAspect uploadAspect(
        Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional,
        ReportRecordRepository reportRecordRepository
    ) {
        return new ReportAspect(imageGcsBusinessServiceOptional, reportRecordRepository);
    }

    @Bean
    public PatientDocumentAspect patientDocumentAspect(
        ImageRelationBusinessService imageRelationBusinessService,
        ImageBusinessService imageBusinessService,
        ImageRepository imageRepository,
        ImageRelationRepository imageRelationRepository,
        PatientDocumentRepository patientDocumentRepository,
        ConfigurationMapService configurationMapService
    ) {
        return new PatientDocumentAspect(
            imageRelationBusinessService,
            imageBusinessService,
            imageRepository,
            imageRelationRepository,
            patientDocumentRepository,
            configurationMapService);
    }

    @Bean
    public PatientNhiStatusAspect patientNhiStatusAspect(
        PatientService patientService
    ) {
       return new PatientNhiStatusAspect(
           patientService
       );
    }
}
