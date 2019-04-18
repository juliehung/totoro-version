package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
public abstract class AbstractDoctorAndAuditingEntity<ENTITY extends AbstractDoctorAndAuditingEntity<ENTITY>> extends AbstractAuditingEntity implements Serializable {

    @ManyToOne
    @JsonIgnoreProperties(value = {"lastPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments", "calendars"}, allowSetters = true)
    private ExtendUser doctor;

    public ExtendUser getDoctor() {
        return doctor;
    }

    public ENTITY doctor(ExtendUser doctor) {
        this.doctor = doctor;
        return (ENTITY) this;
    }

    public void setDoctor(ExtendUser doctor) {
        this.doctor = doctor;
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getCreatedDate() {
        return super.getCreatedDate();
    }
}
