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
            cm.createCache(io.dentall.totoro.domain.TreatmentTask.class.getName() + ".teeth", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.TreatmentProcedure.class.getName() + ".teeth", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Treatment.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Patient.class.getName() + ".treatments", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.TreatmentPlan.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.TreatmentPlan.class.getName() + ".treatmentTasks", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Treatment.class.getName() + ".treatmentPlans", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Drug.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Todo.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Todo.class.getName() + ".treatmentProcedures", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Patient.class.getName() + ".todos", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Prescription.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Prescription.class.getName() + ".treatmentDrugs", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.TreatmentDrug.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Disposal.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Disposal.class.getName() + ".treatmentProcedures", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.RegistrationDel.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ConditionType.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Disposal.class.getName() + ".teeth", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Patient.class.getName() + ".teeth", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHIExtendDisposal.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHIProcedureType.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHIExtendTreatmentProcedure.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NHIExtendTreatmentDrug.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry

            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".lastPatients", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".firstPatients", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".appointments", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".treatmentProcedures", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".treatmentTasks", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".procedures", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".treatments", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ExtendUser.class.getName() + ".calendars", jcacheConfiguration);
        };
    }
}
