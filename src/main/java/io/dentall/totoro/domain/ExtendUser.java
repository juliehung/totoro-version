package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * A ExtendUser.
 */
@Entity
@Table(name = "extend_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExtendUser implements Serializable, Avatar {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "first_login")
    private Boolean firstLogin;

    @Column(name = "gmail")
    private String gmail;

    @Column(name = "calendar_id")
    private String calendarId;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "avatar_content_type")
    private String avatarContentType;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "dominantDoctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Patient> dominantPatients = new HashSet<>();

    @OneToMany(mappedBy = "firstDoctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Patient> firstPatients = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentProcedure> treatmentProcedures = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentTask> treatmentTasks = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Procedure> procedures = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Treatment> treatments = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Calendar> calendars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Boolean isFirstLogin() {
        return firstLogin;
    }

    public ExtendUser firstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
        return this;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    @Override
    public byte[] getAvatar() {
        return avatar;
    }

    public ExtendUser avatar(byte[] avatar) {
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

    public ExtendUser avatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
        return this;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public ExtendUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Patient> getDominantPatients() {
        return dominantPatients;
    }

    public ExtendUser dominantPatients(Set<Patient> dominantPatients) {
        this.dominantPatients = dominantPatients;
        return this;
    }

    public ExtendUser addDominantPatient(Patient dominantPatient) {
        this.dominantPatients.add(dominantPatient);
        dominantPatient.setDominantDoctor(this);
        return this;
    }

    public ExtendUser removeAppointment(Patient dominantPatient) {
        this.dominantPatients.remove(dominantPatient);
        dominantPatient.setDominantDoctor(null);
        return this;
    }

    public void setDominantPatients(Set<Patient> dominantPatients) {
        this.dominantPatients = dominantPatients;
    }

    public Set<Patient> getFirstPatients() {
        return firstPatients;
    }

    public ExtendUser firstPatients(Set<Patient> firstPatients) {
        this.firstPatients = firstPatients;
        return this;
    }

    public ExtendUser addFirstPatient(Patient firstPatient) {
        this.firstPatients.add(firstPatient);
        firstPatient.setFirstDoctor(this);
        return this;
    }

    public ExtendUser removeFirstPatient(Patient firstPatient) {
        this.firstPatients.remove(firstPatient);
        firstPatient.setFirstDoctor(null);
        return this;
    }

    public void setFirstPatients(Set<Patient> firstPatients) {
        this.firstPatients = firstPatients;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public ExtendUser appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public ExtendUser addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setDoctor(this);
        return this;
    }

    public ExtendUser removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setDoctor(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<TreatmentProcedure> getTreatmentProcedures() {
        return treatmentProcedures;
    }

    public ExtendUser treatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
        return this;
    }

    public ExtendUser addTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.add(treatmentProcedure);
        treatmentProcedure.setDoctor(this);
        return this;
    }

    public ExtendUser removeTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.remove(treatmentProcedure);
        treatmentProcedure.setDoctor(null);
        return this;
    }

    public void setTreatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
    }

    public Set<TreatmentTask> getTreatmentTasks() {
        return treatmentTasks;
    }

    public ExtendUser treatmentTasks(Set<TreatmentTask> treatmentTasks) {
        this.treatmentTasks = treatmentTasks;
        return this;
    }

    public ExtendUser addTreatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTasks.add(treatmentTask);
        treatmentTask.setDoctor(this);
        return this;
    }

    public ExtendUser removeTreatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTasks.remove(treatmentTask);
        treatmentTask.setDoctor(null);
        return this;
    }

    public void setTreatmentTasks(Set<TreatmentTask> treatmentTasks) {
        this.treatmentTasks = treatmentTasks;
    }

    public Set<Procedure> getProcedures() {
        return procedures;
    }

    public ExtendUser procedures(Set<Procedure> procedures) {
        this.procedures = procedures;
        return this;
    }

    public ExtendUser addProcedure(Procedure procedure) {
        this.procedures.add(procedure);
        procedure.setDoctor(this);
        return this;
    }

    public ExtendUser removeProcedure(Procedure procedure) {
        this.procedures.remove(procedure);
        procedure.setDoctor(null);
        return this;
    }

    public void setProcedures(Set<Procedure> procedures) {
        this.procedures = procedures;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public ExtendUser treatments(Set<Treatment> treatments) {
        this.treatments = treatments;
        return this;
    }

    public ExtendUser addTreatment(Treatment treatment) {
        this.treatments.add(treatment);
        treatment.setDoctor(this);
        return this;
    }

    public ExtendUser removeTreatment(Treatment treatment) {
        this.treatments.remove(treatment);
        treatment.setDoctor(null);
        return this;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public Set<Calendar> getCalendars() {
        return calendars;
    }

    public ExtendUser calendars(Set<Calendar> calendars) {
        this.calendars = calendars;
        return this;
    }

    public ExtendUser addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
        calendar.setDoctor(this);
        return this;
    }

    public ExtendUser removeCalendar(Calendar calendar) {
        this.calendars.remove(calendar);
        calendar.setDoctor(null);
        return this;
    }

    public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtendUser extendUser = (ExtendUser) o;
        if (extendUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extendUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtendUser{" +
            "id=" + getId() +
            ", firstLogin='" + isFirstLogin() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", avatarContentType='" + getAvatarContentType() + "'" +
            "}";
    }
}
