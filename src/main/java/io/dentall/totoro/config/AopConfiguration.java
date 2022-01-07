package io.dentall.totoro.config;

import io.dentall.totoro.aop.ReportAspect;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.repository.ReportRecordRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Optional;


@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

    @Bean
    public ReportAspect uploadAspect(Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional, ReportRecordRepository reportRecordRepository) {
        return new ReportAspect(imageGcsBusinessServiceOptional, reportRecordRepository);
    }

}
