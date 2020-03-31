package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.ConfigurationMap;

import java.util.List;

public class ConfigurationVM {
    public List<ConfigurationMap> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ConfigurationMap> configurations) {
        this.configurations = configurations;
    }

    private List<ConfigurationMap> configurations;
}
