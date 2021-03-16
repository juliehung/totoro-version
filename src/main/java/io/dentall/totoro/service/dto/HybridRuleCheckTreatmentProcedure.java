package io.dentall.totoro.service.dto;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckResultDTO;
import io.dentall.totoro.domain.*;

import java.util.ArrayList;
import java.util.List;

public class HybridRuleCheckTreatmentProcedure extends TreatmentProcedure {

    public HybridRuleCheckTreatmentProcedure(TreatmentProcedure tp) {
        this.setId(tp.getId());
        this.setStatus(tp.getStatus());
        this.setQuantity(tp.getQuantity());
        this.setTotal(tp.getTotal());
        this.setNote(tp.getNote());
        this.setCompletedDate(tp.getCompletedDate());
        this.setPrice(tp.getPrice());
        this.setNhiCategory(tp.getNhiCategory());
        this.setNhiDescription(tp.getNhiDescription());
        this.setNhiIcd10Cm(tp.getNhiIcd10Cm());
        this.setCreatedDate(tp.getCreatedDate());
        this.setCreatedBy(tp.getCreatedBy());
        this.setLastModifiedDate(tp.getLastModifiedDate());
        this.setLastModifiedBy(tp.getLastModifiedBy());

        this.setNhiProcedure(tp.getNhiProcedure());
        this.setProcedure(tp.getProcedure());
        this.setDoctor(tp.getDoctor());
        this.setNhiExtendTreatmentProcedure(tp.getNhiExtendTreatmentProcedure());
        this.setDisposal(tp.getDisposal());
<<<<<<< HEAD
=======
        this.setTeeth(tp.getTeeth());
>>>>>>> milestone-1.20
    }

    public List<NhiRuleCheckResultDTO> getCheckHistory() {
        return checkHistory;
    }

    public void setCheckHistory(List<NhiRuleCheckResultDTO> checkHistory) {
        this.checkHistory = checkHistory;
    }

    private List<NhiRuleCheckResultDTO> checkHistory = new ArrayList<>();
}
