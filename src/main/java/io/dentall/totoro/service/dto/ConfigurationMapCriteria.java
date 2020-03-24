package io.dentall.totoro.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link io.dentall.totoro.domain.ConfigurationMap} entity. This class is used
 * in {@link io.dentall.totoro.web.rest.ConfigurationMapResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /configuration-maps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConfigurationMapCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter configKey;

    private StringFilter configValue;

    public ConfigurationMapCriteria() {
    }

    public ConfigurationMapCriteria(ConfigurationMapCriteria other) {
        this.id = other.id;
        this.configKey = other.configKey;
        this.configValue = other.configValue;
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConfigKey() {
        return configKey;
    }

    public void setConfigKey(StringFilter configKey) {
        this.configKey = configKey;
    }

    public StringFilter getConfigValue() {
        return configValue;
    }

    public void setConfigValue(StringFilter configValue) {
        this.configValue = configValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConfigurationMapCriteria that = (ConfigurationMapCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(configKey, that.configKey) &&
            Objects.equals(configValue, that.configValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        configKey,
        configValue
        );
    }

    @Override
    public String toString() {
        return "ConfigurationMapCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (configKey != null ? "configKey=" + configKey + ", " : "") +
                (configValue != null ? "configValue=" + configValue + ", " : "") +
            "}";
    }

}
