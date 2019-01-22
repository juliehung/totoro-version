package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.dentall.totoro.domain.enumeration.SettingType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
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
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private SettingType type;

    @NotNull
    @Column(name = "preferences", nullable = false, columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Map<String, Object> preferences = new LinkedHashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SettingType getType() {
        return type;
    }

    public Setting type(SettingType type) {
        this.type = type;
        return this;
    }

    public void setType(SettingType type) {
        this.type = type;
    }

    public Map<String, Object> getPreferences() {
        return preferences;
    }

    public Setting preferences(Map<String, Object> preferences) {
        this.preferences = preferences;
        return this;
    }

    @JsonAnySetter
    public void setPreferences(String key, Object value) {
        preferences.put(key, value);
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
            ", type='" + getType() + "'" +
            ", preferences='" + getPreferences() + "'" +
            "}";
    }
}
