package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.repository.dao.MonthDisposalDAO;

import java.util.List;

public class MonthDisposalVM {

    @JsonProperty(value = "disposalId")
    private Long disposalId;

    @JsonProperty(value = "nhiExtendDisposalList")
    private List<MonthDisposalDAO> monthDisposalDTO;

    public MonthDisposalVM(Long disposalId, List<MonthDisposalDAO> monthDisposalDTO) {
        this.disposalId = disposalId;
        this.monthDisposalDTO = monthDisposalDTO;
    }
}
