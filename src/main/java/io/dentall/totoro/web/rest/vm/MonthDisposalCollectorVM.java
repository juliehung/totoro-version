package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.service.dto.MonthDisposalDTO;

import java.util.List;

public class MonthDisposalCollectorVM {

    @JsonProperty(value = "disposalId")
    private Long disposalId;

    @JsonProperty(value = "nhiExtendDisposalList")
    private List<MonthDisposalVM> monthDisposalVM;

    public MonthDisposalCollectorVM(Long disposalId, List<MonthDisposalVM> monthDisposalVM) {
        this.disposalId = disposalId;
        this.monthDisposalVM = monthDisposalVM;
    }
}
