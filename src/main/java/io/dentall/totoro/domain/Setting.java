package io.dentall.totoro.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.dentall.totoro.domain.jsonb.setting.Preferences;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Setting.
 */
@Entity
@Table(name = "setting")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "preferences", nullable = false, columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Preferences preferences;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public Setting preferences(Preferences preferences) {
        this.preferences = preferences;
        return this;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Setting setting = (Setting) o;
        if (setting.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), setting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Setting{" +
            "id=" + getId() +
            ", preferences='" + getPreferences() + "'" +
            "}";
    }
}
