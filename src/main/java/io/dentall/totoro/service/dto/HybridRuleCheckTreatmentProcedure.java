package io.dentall.totoro.service.dto;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckResultDTO;
import io.dentall.totoro.domain.TreatmentProcedure;

import java.util.ArrayList;
import java.util.List;

public class HybridRuleCheckTreatmentProcedure extends TreatmentProcedure {
    public List<NhiRuleCheckResultDTO> getCheckHistory() {
        return checkHistory;
    }

    public void setCheckHistory(List<NhiRuleCheckResultDTO> checkHistory) {
        this.checkHistory = checkHistory;
    }

    private List<NhiRuleCheckResultDTO> checkHistory = new ArrayList<>();
}
