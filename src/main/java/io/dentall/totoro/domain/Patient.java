package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class Patient extends AbstractAuditingEntity implements Serializable, Avatar {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "medical_id")
    private String medicalId;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood")
    private Blood blood;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "vip")
    private String vip;

    @Column(name = "emergency_name")
    private String emergencyName;

    @Column(name = "emergency_phone")
    private String emergencyPhone;

    @Column(name = "delete_date")
    private Instant deleteDate;

    @Column(name = "scaling")
    private LocalDate scaling;

    @Column(name = "line_id")
    private String lineId;

    @Column(name = "fb_id")
    private String fbId;

    @Column(name = "note")
    private String note;

    @Column(name = "clinic_note")
    private String clinicNote;

    @Column(name = "write_ic_time")
    private Instant writeIcTime;

    @JsonIgnore
    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @JsonIgnore
    @Column(name = "avatar_content_type")
    private String avatarContentType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private Patient introducer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_parent",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "parents_id", referencedColumnName = "id"))
    private Set<Patient> parents = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_spouse1",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "spouse1s_id", referencedColumnName = "id"))
    private Set<Patient> spouse1S = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_tag",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(mappedBy = "parents", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Patient> children = new HashSet<>();

    @ManyToMany(mappedBy = "spouse1S", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Patient> spouse2S = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private PatientIdentity patientIdentity;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Treatment> treatments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @ManyToOne
    @JsonIgnoreProperties({"dominantPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments"})
    private ExtendUser dominantDoctor;

    @ManyToOne
    @JsonIgnoreProperties({"dominantPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments"})
    private ExtendUser firstDoctor;

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

    public String getMedicalId() {
        if (medicalId == null) {
            return birth == null ? null : birth.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return medicalId;
    }

    public Patient medicalId(String medicalId) {
        this.medicalId = medicalId;
        return this;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
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

    public Instant getDeleteDate() {
        return deleteDate;
    }

    public Patient deleteDate(Instant deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public void setDeleteDate(Instant deleteDate) {
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

    public String getNote() {
        return note;
    }

    public Patient note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClinicNote() {
        return clinicNote;
    }

    public Patient clinicNote(String clinicNote) {
        this.clinicNote = clinicNote;
        return this;
    }

    public void setClinicNote(String clinicNote) {
        this.clinicNote = clinicNote;
    }

    public Instant getWriteIcTime() {
        return writeIcTime;
    }

    public Patient writeIcTime(Instant writeIcTime) {
        this.writeIcTime = writeIcTime;
        return this;
    }

    public void setWriteIcTime(Instant writeIcTime) {
        this.writeIcTime = writeIcTime;
    }

    @Override
    public byte[] getAvatar() {
        return avatar;
    }

    public Patient avatar(byte[] avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getAvatarContentType() {
        return avatarContentType;
    }

    public Patient avatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
        return this;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public Patient questionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
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

    public Patient getIntroducer() {
        return introducer;
    }

    public Patient introducer(Patient patient) {
        this.introducer = patient;
        return this;
    }

    public void setIntroducer(Patient patient) {
        this.introducer = patient;
    }

    public Set<Patient> getParents() {
        return parents;
    }

    public Patient parents(Set<Patient> patients) {
        this.parents = patients;
        return this;
    }

    public Patient addParent(Patient patient) {
        this.parents.add(patient);
        patient.getChildren().add(this);
        return this;
    }

    public Patient removeParent(Patient patient) {
        this.parents.remove(patient);
        patient.getChildren().remove(this);
        return this;
    }

    public void setParents(Set<Patient> patients) {
        this.parents = patients;
    }

    public Set<Patient> getSpouse1S() {
        return spouse1S;
    }

    public Patient spouse1S(Set<Patient> patients) {
        this.spouse1S = patients;
        return this;
    }

    public Patient addSpouse1(Patient patient) {
        this.spouse1S.add(patient);
        patient.getSpouse2S().add(this);
        return this;
    }

    public Patient removeSpouse1(Patient patient) {
        this.spouse1S.remove(patient);
        patient.getSpouse2S().remove(this);
        return this;
    }

    public void setSpouse1S(Set<Patient> patients) {
        this.spouse1S = patients;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Patient tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Patient addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPatients().add(this);
        return this;
    }

    public Patient removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPatients().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Patient> getChildren() {
        return children;
    }

    public Patient children(Set<Patient> patients) {
        this.children = patients;
        return this;
    }

    public Patient addChild(Patient patient) {
        this.children.add(patient);
        patient.getParents().add(this);
        return this;
    }

    public Patient removeChild(Patient patient) {
        this.children.remove(patient);
        patient.getParents().remove(this);
        return this;
    }

    public void setChildren(Set<Patient> patients) {
        this.children = patients;
    }

    public Set<Patient> getSpouse2S() {
        return spouse2S;
    }

    public Patient spouse2S(Set<Patient> patients) {
        this.spouse2S = patients;
        return this;
    }

    public Patient addSpouse2(Patient patient) {
        this.spouse2S.add(patient);
        patient.getSpouse1S().add(this);
        return this;
    }

    public Patient removeSpouse2(Patient patient) {
        this.spouse2S.remove(patient);
        patient.getSpouse1S().remove(this);
        return this;
    }

    public void setSpouse2S(Set<Patient> patients) {
        this.spouse2S = patients;
    }

    public PatientIdentity getPatientIdentity() {
        return patientIdentity;
    }

    public Patient patientIdentity(PatientIdentity patientIdentity) {
        this.patientIdentity = patientIdentity;
        return this;
    }

    public void setPatientIdentity(PatientIdentity patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public Patient treatments(Set<Treatment> treatments) {
        this.treatments = treatments;
        return this;
    }

    public Patient addTreatment(Treatment treatment) {
        this.treatments.add(treatment);
        treatment.setPatient(this);
        return this;
    }

    public Patient removeTreatmentProcedure(Treatment treatment) {
        this.treatments.remove(treatment);
        treatment.setPatient(null);
        return this;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public String getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

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
            ", phone='" + getPhone() + "'" +
            ", gender='" + getGender() + "'" +
            ", birth='" + getBirth() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            ", medicalId='" + getMedicalId() + "'" +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", blood='" + getBlood() + "'" +
            ", cardId='" + getCardId() + "'" +
            ", vip='" + getVip() + "'" +
            ", emergencyName='" + getEmergencyName() + "'" +
            ", emergencyPhone='" + getEmergencyPhone() + "'" +
            ", deleteDate='" + getDeleteDate() + "'" +
            ", scaling='" + getScaling() + "'" +
            ", lineId='" + getLineId() + "'" +
            ", fbId='" + getFbId() + "'" +
            ", note='" + getNote() + "'" +
            ", clinicNote='" + getClinicNote() + "'" +
            ", writeIcTime='" + getWriteIcTime() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", avatarContentType='" + getAvatarContentType() + "'" +
            "}";
    }
}
