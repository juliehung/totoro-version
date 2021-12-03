package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public interface SameTreatmentVM {
    @JsonProperty(value = "id")
    Long getId();

    @JsonProperty(value = "a18")
    String getA18();

    @JsonProperty(value = "a23")
    String getA23();

    @JsonProperty(value = "a74")
    String getA74();

    @JsonProperty(value = "completedDate")
    Instant getCompletedDate();

    @JsonProperty(value = "code")
    String getCode();

    @JsonProperty(value = "name")
    String getName();
}
