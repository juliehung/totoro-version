package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.Gender;

import io.dentall.totoro.domain.enumeration.Blood;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "national_id", nullable = false)
    private String nationalId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "medical_id")
    private String medicalId;

    @Column(name = "zip")
    private String zip;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "photo")
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood")
    private Blood blood;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "vip")
    private String vip;

    @ManyToOne
    @JsonIgnoreProperties({"dominantPatients", "firstPatients"})
    private ExtendUser dominantDoctor;

    @ManyToOne
    @JsonIgnoreProperties({"dominantPatients", "firstPatients"})
    private ExtendUser firstDoctor;

    @ManyToOne
    @JoinColumn(name = "introducer_id")
    @JsonIgnoreProperties("introducer")
    private Patient introducer;

    @ManyToOne
    @JoinColumn(name = "update_user_id")
    @JsonIgnoreProperties({"dominantPatients", "firstPatients"})
    private ExtendUser updateUser;

    @Column(name = "emergency_name")
    private String emergencyName;

    @Column(name = "emergency_phone")
    private String emergencyPhone;

    @Column(name = "delete_date")
    private ZonedDateTime deleteDate;

    @Column(name = "scaling")
    private LocalDate scaling;

    @Column(name = "allergy")
    private Boolean allergy;

    @Column(name = "inconvenience")
    private Boolean inconvenience;

    @Column(name = "serious_disease")
    private Boolean seriousDisease;

    @Column(name = "line_id")
    private String lineId;

    @Column(name = "fb_id")
    private String fbId;

    @Column(name = "reminder")
    private String reminder;

    @Column(name = "last_modified_time")
    private ZonedDateTime lastModifiedTime;

    @Column(name = "write_ic_time")
    private ZonedDateTime writeIcTime;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Patient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public Patient nationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Gender getGender() {
        return gender;
    }

    public Patient gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public Patient birth(LocalDate birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public Patient phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public Patient medicalId(String medicalId) {
        this.medicalId = medicalId;
        return this;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
    }

    public String getZip() {
        return zip;
    }

    public Patient zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public Patient address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public Patient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public Patient photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Blood getBlood() {
        return blood;
    }

    public Patient blood(Blood blood) {
        this.blood = blood;
        return this;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public String getCardId() {
        return cardId;
    }

    public Patient cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getVip() {
        return vip;
    }

    public Patient vip(String vip) {
        this.vip = vip;
        return this;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public ExtendUser getDominantDoctor() {
        return dominantDoctor;
    }

    public Patient dominantDoctor(ExtendUser dominantDoctor) {
        this.dominantDoctor = dominantDoctor;
        return this;
    }

    public void setDominantDoctor(ExtendUser dominantDoctor) {
        this.dominantDoctor = dominantDoctor;
    }

    public ExtendUser getFirstDoctor() {
        return firstDoctor;
    }

    public Patient firstDoctor(ExtendUser firstDoctor) {
        this.firstDoctor = firstDoctor;
        return this;
    }

    public void setFirstDoctor(ExtendUser firstDoctor) {
        this.firstDoctor = firstDoctor;
    }

    public Patient getIntroducer() {
        return introducer;
    }

    public Patient introducer(Patient introducer) {
        this.introducer = introducer;
        return this;
    }

    public void setIntroducer(Patient introducer) {
        this.introducer = introducer;
    }

    public ExtendUser getUpdateUser() {
        return updateUser;
    }

    public Patient updateUser(ExtendUser updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(ExtendUser updateUser) {
        this.updateUser = updateUser;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public Patient emergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
        return this;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public Patient emergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
        return this;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public ZonedDateTime getDeleteDate() {
        return deleteDate;
    }

    public Patient deleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public void setDeleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public LocalDate getScaling() {
        return scaling;
    }

    public Patient scaling(LocalDate scaling) {
        this.scaling = scaling;
        return this;
    }

    public void setScaling(LocalDate scaling) {
        this.scaling = scaling;
    }

    public Boolean isAllergy() {
        return allergy;
    }

    public Patient allergy(Boolean allergy) {
        this.allergy = allergy;
        return this;
    }

    public void setAllergy(Boolean allergy) {
        this.allergy = allergy;
    }

    public Boolean isInconvenience() {
        return inconvenience;
    }

    public Patient inconvenience(Boolean inconvenience) {
        this.inconvenience = inconvenience;
        return this;
    }

    public void setInconvenience(Boolean inconvenience) {
        this.inconvenience = inconvenience;
    }

    public Boolean isSeriousDisease() {
        return seriousDisease;
    }

    public Patient seriousDisease(Boolean seriousDisease) {
        this.seriousDisease = seriousDisease;
        return this;
    }

    public void setSeriousDisease(Boolean seriousDisease) {
        this.seriousDisease = seriousDisease;
    }

    public String getLineId() {
        return lineId;
    }

    public Patient lineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getFbId() {
        return fbId;
    }

    public Patient fbId(String fbId) {
        this.fbId = fbId;
        return this;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getReminder() {
        return reminder;
    }

    public Patient reminder(String reminder) {
        this.reminder = reminder;
        return this;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public ZonedDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public Patient lastModifiedTime(ZonedDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public void setLastModifiedTime(ZonedDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public ZonedDateTime getWriteIcTime() {
        return writeIcTime;
    }

    public Patient writeIcTime(ZonedDateTime writeIcTime) {
        this.writeIcTime = writeIcTime;
        return this;
    }

    public void setWriteIcTime(ZonedDateTime writeIcTime) {
        this.writeIcTime = writeIcTime;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Patient appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Patient addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setPatient(this);
        return this;
    }

    public Patient removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setPatient(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            ", gender='" + getGender() + "'" +
            ", birth='" + getBirth() + "'" +
            ", phone='" + getPhone() + "'" +
            ", medicalId='" + getMedicalId() + "'" +
            ", zip='" + getZip() + "'" +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", blood='" + getBlood() + "'" +
            ", cardId='" + getCardId() + "'" +
            ", vip='" + getVip() + "'" +
            ", dominantDoctor=" + getDominantDoctor() +
            ", firstDoctor=" + getFirstDoctor() +
            ", introducer=" + getIntroducer() +
            ", updateUser=" + getUpdateUser() +
            ", emergencyName='" + getEmergencyName() + "'" +
            ", emergencyPhone='" + getEmergencyPhone() + "'" +
            ", deleteDate='" + getDeleteDate() + "'" +
            ", scaling='" + getScaling() + "'" +
            ", allergy='" + isAllergy() + "'" +
            ", inconvenience='" + isInconvenience() + "'" +
            ", seriousDisease='" + isSeriousDisease() + "'" +
            ", lineId='" + getLineId() + "'" +
            ", fbId='" + getFbId() + "'" +
            ", reminder='" + getReminder() + "'" +
            ", lastModifiedTime='" + getLastModifiedTime() + "'" +
            ", writeIcTime='" + getWriteIcTime() + "'" +
            "}";
    }
}
