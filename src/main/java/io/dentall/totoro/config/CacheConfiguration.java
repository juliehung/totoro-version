package io.dentall.totoro.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.dentall.totoro.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.dentall.totoro.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Patient.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Patient.class.getName() + ".appointments", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Appointment.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Registration.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Tag.class.getName() + ".patients", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Questionnaire.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.TreatmentTask.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.TreatmentTask.class.getName() + ".treatmentProcedures", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.TreatmentProcedure.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHIProcedure.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHICategory.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Hospital.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Accounting.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Calendar.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHIUnusalIncident.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHIUnusalContent.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.PatientIdentity.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.CalendarSetting.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Tooth.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Ledger.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Procedure.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ProcedureType.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Appointment.class.getName() + ".treatmentProcedures", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Registration.class.getName() + ".treatmentProcedures", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry

            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".dominantPatients", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".firstPatients", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".appointments", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".treatmentProcedures", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".treatmentTasks", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".procedures", jcacheConfiguration);
        };
    }
}
