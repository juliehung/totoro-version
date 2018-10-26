package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A ExtendUser.
 */
@Entity
@Table(name = "extend_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExtendUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "first_login")
    private Boolean firstLogin = true;

    @Column(name = "gmail")
    private String gmail;

    @Column(name = "calendar_id")
    private String calendarId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
            "}";
    }
}
