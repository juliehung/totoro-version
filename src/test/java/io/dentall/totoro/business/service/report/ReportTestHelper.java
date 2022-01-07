package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.dto.DrugVo;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.OwnExpenseVo;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ReportTestHelper {

    private ReportTestHelper() {
    }

    public static List<NhiVo> getNhiSubsequentList(List<NhiVo> nhiVoList, int gapMonths) {
        return nhiVoList.stream().map(vo -> {
                NhiVo newVo1 = new NhiVo();
                newVo1.setPatientId(vo.getPatientId());
                newVo1.setDisposalDate(vo.getDisposalDate().plusMonths(gapMonths));

                NhiVo newVo2 = new NhiVo();
                newVo2.setPatientId(vo.getPatientId());
                newVo2.setDisposalDate(vo.getDisposalDate().plusMonths(gapMonths).plusDays(1));

                return asList(newVo1, newVo2);
            })
            .flatMap(Collection::stream)
            .collect(toList());
    }

    public static List<OwnExpenseVo> getOwnExpenseSubsequentList(List<NhiVo> nhiVoList, int gapMonths) {
        return nhiVoList.stream().map(vo -> {
                OwnExpenseVo newVo1 = new OwnExpenseVo();
                newVo1.setPatientId(vo.getPatientId());
                newVo1.setDisposalDate(vo.getDisposalDate().plusMonths(gapMonths));

                OwnExpenseVo newVo2 = new OwnExpenseVo();
                newVo2.setPatientId(vo.getPatientId());
                newVo2.setDisposalDate(vo.getDisposalDate().plusMonths(gapMonths).plusDays(1));

                return asList(newVo1, newVo2);
            })
            .flatMap(Collection::stream)
            .collect(toList());
    }

    public static List<DrugVo> getDrugSubsequentList(List<NhiVo> nhiVoList, int gapMonths) {
        return nhiVoList.stream().map(vo -> {
                DrugVo newVo1 = new DrugVo();
                newVo1.setPatientId(vo.getPatientId());
                newVo1.setDisposalDate(vo.getDisposalDate().plusMonths(gapMonths));

                DrugVo newVo2 = new DrugVo();
                newVo2.setPatientId(vo.getPatientId());
                newVo2.setDisposalDate(vo.getDisposalDate().plusMonths(gapMonths).plusDays(1));

                return asList(newVo1, newVo2);
            })
            .flatMap(Collection::stream)
            .collect(toList());
    }
}
