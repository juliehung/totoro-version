package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public interface SameTreatmentVM {
    @JsonProperty(value = "id")
    Long getTreatmentProcedures_NhiExtendTreatmentProcedure_TreatmentProcedure_Id();

    @JsonProperty(value = "a18")
    String getNhiExtendDisposals_a18();

    @JsonProperty(value = "a23")
    String getNhiExtendDisposals_a23();

    @JsonProperty(value = "a74")
    String getTreatmentProcedures_NhiExtendTreatmentProcedure_a74();

    @JsonProperty(value = "completedDate")
    Instant getTreatmentProcedures_completedDate();

    @JsonProperty(value = "code")
    String getTreatmentProcedures_NhiProcedure_code();

    @JsonProperty(value = "name")
    String getTreatmentProcedures_NhiProcedure_name();
}
