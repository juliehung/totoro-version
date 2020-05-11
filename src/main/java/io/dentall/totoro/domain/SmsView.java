package io.dentall.totoro.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SmsView.
 */
@Entity
@Table(name = "sms_view")
public class SmsView extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "last_time")
    private Instant lastTime;


    public Long getId() {
        return id;
    }

    public SmsView id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Instant getLastTime() {
        return lastTime;
    }

    public SmsView lastTime(Instant lastTime) {
        this.lastTime = lastTime;
        return this;
    }

    public void setLastTime(Instant lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsView smsView = (SmsView) o;
        if (smsView.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smsView.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmsView{" +
            "id=" + getId() +
            ", lastTime='" + getLastTime() + "'" +
            "}";
    }
}
