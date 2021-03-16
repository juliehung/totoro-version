package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.Blood;
import io.dentall.totoro.domain.enumeration.Gender;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient extends AbstractAuditingEntity implements Serializable, Avatar {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencePatientGenerator")
    @SequenceGenerator(name = "sequencePatientGenerator", sequenceName = "seq_patient", allocationSize = 1)
    private Long id;

    @Column(name = "display_name")
    private String displayName;

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

    @Column(name = "emergency_address")
    private String emergencyAddress;

    @Column(name = "emergency_relationship")
    private String emergencyRelationship;

    @Column(name = "delete_date")
    private Instant deleteDate;

    @Column(name = "scaling")
    private LocalDate scaling;

    @Column(name = "line_id")
    private String lineId;

    @Column(name = "fb_id")
    private String fbId;

    @Size(max = 5100)
    @Column(name = "note", length = 5100)
    private String note;

    @Size(max = 5100)
    @Column(name = "clinic_note", length = 5100)
    private String clinicNote;

    @Column(name = "write_ic_time")
    private Instant writeIcTime;

    @Column(name = "main_notice_channel")
    private String mainNoticeChannel;

    @Column(name = "career")
    private String career;

    @Column(name = "marriage")
    private String marriage;

    @JsonIgnore
    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @JsonIgnore
    @Column(name = "avatar_content_type")
    private String avatarContentType;

    @Column(name = "new_patient")
    private Boolean newPatient;

    @Column(name = "teeth_graph_permanent_switch")
    private String teethGraphPermanentSwitch;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();

    @Column(name = "introducer")
    private String introducer;

	@Column(name = "case_manager")
	private String caseManager;

    @Column(name = "vip_patient")
    private Boolean vipPatient;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_parent",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "parents_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Patient> parents = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_spouse1",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "spouse1s_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Patient> spouse1S = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_tag",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<Tag> tags = null;

    @ManyToMany(mappedBy = "parents", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Patient> children = new HashSet<>();

    @ManyToMany(mappedBy = "spouse1S", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Patient> spouse2S = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private PatientIdentity patientIdentity;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Todo> todos = new HashSet<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tooth> teeth = null;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @ManyToOne
    @JsonIgnoreProperties(value = {"lastPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments", "calendars"}, allowSetters = true)
    private ExtendUser lastDoctor;

    @ManyToOne
    @JsonIgnoreProperties(value = {"lastPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments", "calendars"}, allowSetters = true)
    private ExtendUser firstDoctor;

    @OneToOne(mappedBy = "patient")
    private NhiExtendPatient nhiExtendPatient;

    @Column(name = "due_date")
    private LocalDate dueDate;

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

    public Boolean isNewPatient() {
        return newPatient;
    }

    public Patient newPatient(Boolean newPatient) {
        this.newPatient = newPatient;
        return this;
    }

    public void setNewPatient(Boolean newPatient) {
        this.newPatient = newPatient;
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

    @ApiModelProperty(hidden = true)
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Patient appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getIntroducer() {
        return introducer;
    }

    public Patient introducer(String patient) {
        this.introducer = patient;
        return this;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getCaseManager() {
        return caseManager;
    }

    public void setCaseManager(String caseManager) {
        this.caseManager = caseManager;
    }

    public Patient caseMansger(String caseManager) {
        this.caseManager = caseManager;
        return this;
    }

    public Boolean getVipPatient() {
        return vipPatient;
    }

    public void setVipPatient(Boolean vipPatient) {
        this.vipPatient = vipPatient;
    }

    public Patient vipPatient(Boolean vipPatient) {
        this.vipPatient = vipPatient;
        return this;
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

    @ApiModelProperty(hidden = true)
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

    @ApiModelProperty(hidden = true)
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

    @ApiModelProperty(hidden = true)
    public Set<Todo> getTodos() {
        return todos;
    }

    public Patient todos(Set<Todo> todos) {
        this.todos = todos;
        return this;
    }

    public Patient addTodo(Todo todo) {
        this.todos.add(todo);
        todo.setPatient(this);
        return this;
    }

    public Patient removeTodo(Todo todo) {
        this.todos.remove(todo);
        todo.setPatient(null);
        return this;
    }

    public void setTodos(Set<Todo> todos) {
        this.todos = todos;
    }

    public Set<Tooth> getTeeth() {
        return teeth;
    }

    public Patient teeth(Set<Tooth> teeth) {
        this.teeth = teeth;
        return this;
    }

    public Patient addTooth(Tooth tooth) {
        this.teeth.add(tooth);
        tooth.setPatient(this);
        return this;
    }

    public Patient removeTooth(Tooth tooth) {
        this.teeth.remove(tooth);
        tooth.setPatient(null);
        return this;
    }

    public void setTeeth(Set<Tooth> teeth) {
        this.teeth = teeth;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ExtendUser getLastDoctor() {
        return lastDoctor;
    }

    public Patient lastDoctor(ExtendUser lastDoctor) {
        this.lastDoctor = lastDoctor;
        return this;
    }

    public void setLastDoctor(ExtendUser lastDoctor) {
        this.lastDoctor = lastDoctor;
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

    @ApiModelProperty(hidden = true)
    public NhiExtendPatient getNhiExtendPatient() {
        return nhiExtendPatient;
    }

    public Patient nhiExtendPatient(NhiExtendPatient nhiExtendPatient) {
        this.nhiExtendPatient = nhiExtendPatient;
        return this;
    }

    public void setNhiExtendPatient(NhiExtendPatient nhiExtendPatient) {
        this.nhiExtendPatient = nhiExtendPatient;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getEmergencyAddress() {
        return emergencyAddress;
    }

    public Patient emergencyAddress(String emergencyAddress) {
        this.emergencyAddress = emergencyAddress;
        return this;
    }

    public void setEmergencyAddress(String emergencyAddress) {
        this.emergencyAddress = emergencyAddress;
    }

    public String getEmergencyRelationship() {
        return emergencyRelationship;
    }

    public Patient emergencyRelationship(String emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
        return this;
    }

    public void setEmergencyRelationship(String emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
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

    public String getMainNoticeChannel() {
        return mainNoticeChannel;
    }

    public Patient mainNoticeChannel(String mainNoticeChannel) {
        this.mainNoticeChannel = mainNoticeChannel;
        return this;
    }

    public void setMainNoticeChannel(String mainNoticeChannel) {
        this.mainNoticeChannel = mainNoticeChannel;
    }

    public String getCareer() {
        return career;
    }

    public Patient career(String career) {
        this.career = career;
        return this;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getMarriage() {
        return marriage;
    }

    public Patient marriage(String marriage) {
        this.marriage = marriage;
        return this;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getTeethGraphPermanentSwitch() {
        return teethGraphPermanentSwitch;
    }

    public Patient teethGraphPermanentSwitch(String teethGraphPermanentSwitch) {
        this.teethGraphPermanentSwitch = teethGraphPermanentSwitch;
        return this;
    }

    public void setTeethGraphPermanentSwitch(String teethGraphPermanentSwitch) {
        this.teethGraphPermanentSwitch = teethGraphPermanentSwitch;
    }

    public Patient displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
            ", displayName='" + getDisplayName() + "'" +
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
            ", emergencyAddress='" + getEmergencyAddress() + "'" +
            ", emergencyRelationship='" + getEmergencyRelationship() + "'" +
            ", deleteDate='" + getDeleteDate() + "'" +
            ", scaling='" + getScaling() + "'" +
            ", lineId='" + getLineId() + "'" +
            ", fbId='" + getFbId() + "'" +
            ", note='" + getNote() + "'" +
            ", clinicNote='" + getClinicNote() + "'" +
            ", writeIcTime='" + getWriteIcTime() + "'" +
            ", avatarContentType='" + getAvatarContentType() + "'" +
            ", newPatient='" + isNewPatient() + "'" +
            ", mainNoticeChannel='" + getMainNoticeChannel() + "'" +
            ", career='" + getCareer() + "'" +
            ", marriage='" + getMarriage() + "'" +
            ", permanent='" + getTeethGraphPermanentSwitch() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }
}
