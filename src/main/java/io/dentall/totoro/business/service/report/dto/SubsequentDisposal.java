package io.dentall.totoro.business.service.report.dto;

import java.time.LocalDate;
import java.util.List;

public interface SubsequentDisposal {

    LocalDate getDisposalDate();

    Long getPatientId();

    void setSubsequentNhiList(List<NhiVo> subsequentNhiList);

    void setSubsequentOwnExpenseList(List<OwnExpenseVo> subsequentOwnExpenseList);

    void setSubsequentDrugList(List<DrugVo> subsequentDrugList);

}
