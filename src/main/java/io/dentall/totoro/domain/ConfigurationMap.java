package io.dentall.totoro.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * A ConfigurationMap.
 */
@Entity
@Table(name = "configuration_map")
public class ConfigurationMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "config_key")
    private String configKey;

    @Column(name = "config_value")
    private String configValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public ConfigurationMap configKey(String configKey) {
        this.configKey = configKey;
        return this;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public ConfigurationMap configValue(String configValue) {
        this.configValue = configValue;
        return this;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigurationMap)) {
            return false;
        }
        return id != null && id.equals(((ConfigurationMap) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConfigurationMap{" +
            "id=" + getId() +
            ", configKey='" + getConfigKey() + "'" +
            ", configValue='" + getConfigValue() + "'" +
            "}";
    }
}
