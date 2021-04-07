package io.dentall.totoro.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.Disposal;

import java.util.Set;

public class HybridRuleCheckDisposal extends Disposal {

    public HybridRuleCheckDisposal(Disposal d) {
        this.setId(d.getId());
        this.setStatus(d.getStatus());
        this.setTotal(d.getTotal());
        this.setDateTime(d.getDateTime());
        this.setDateTimeEnd(d.getDateTimeEnd());
        this.setChiefComplaint(d.getChiefComplaint());
        this.setCreatedBy(d.getCreatedBy());
        this.setCreatedDate(d.getCreatedDate());
        this.setLastModifiedBy(d.getLastModifiedBy());
        this.setLastModifiedDate(d.getLastModifiedDate());
        this.setRevisitContent(d.getRevisitContent());
        this.setRevisitInterval(d.getRevisitInterval());
        this.setRevisitTreatmentTime(d.getRevisitTreatmentTime());
        this.setRevisitComment(d.getRevisitComment());
        this.setRevisitWillNotHappen(d.getRevisitWillNotHappen());

        this.setPrescription(d.getPrescription());
        this.setRegistration(d.getRegistration());
        this.setNhiExtendDisposals(d.getNhiExtendDisposals());
    }

    @JsonProperty(value = "treatmentProcedures")
    private Set<HybridRuleCheckTreatmentProcedure> hybridRuleCheckTreatmentProcedures = null;

    public Set<HybridRuleCheckTreatmentProcedure> getHybridRuleCheckTreatmentProcedures() {
        return hybridRuleCheckTreatmentProcedures;
    }

    public void setHybridRuleCheckTreatmentProcedures(Set<HybridRuleCheckTreatmentProcedure> hybridRuleCheckTreatmentProcedures) {
        this.hybridRuleCheckTreatmentProcedures = hybridRuleCheckTreatmentProcedures;
    }
}
