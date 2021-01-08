package io.dentall.totoro.config;

import io.dentall.totoro.domain.*;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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
            cm.createCache(NhiExtendDisposal.class.getName(), jcacheConfiguration);
            cm.createCache(NhiExtendTreatmentProcedure.class.getName(), jcacheConfiguration);
            cm.createCache(NhiExtendTreatmentDrug.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiIcd9Cm.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiIcd9Cm.class.getName() + ".nhiIcd10Cms", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiIcd10Cm.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiProcedure.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiProcedureType.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiIcd10Pcs.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiProcedure.class.getName() + ".nhiIcd10Pcs", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiDayUpload.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiDayUpload.class.getName() + ".nhiDayUploadDetails", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiDayUploadDetails.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiExtendDisposal.class.getName() + ".nhiDayUploadDetails", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiExtendPatient.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiMedicalRecord.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiExtendPatient.class.getName() + ".nhiMedicalRecords", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Esign.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.MarriageOptions.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.CareerOptions.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.RelationshipOptions.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiMonthDeclaration.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiMonthDeclaration.class.getName() + ".nhiMonthDeclarationDetails", jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiMonthDeclarationDetails.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.DocNp.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.NhiAccumulatedMedicalRecord.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.Image.class.getName(), jcacheConfiguration);
            cm.createCache(io.dentall.totoro.domain.ImageRelation.class.getName(), jcacheConfiguration);
            cm.createCache(HomePageCover.class.getName(), jcacheConfiguration);
            cm.createCache(UserDayOff.class.getName(), jcacheConfiguration);
            cm.createCache(UserShift.class.getName(), jcacheConfiguration);
            cm.createCache(ConfigurationMap.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
