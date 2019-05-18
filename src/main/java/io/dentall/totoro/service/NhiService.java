package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.repository.NhiProcedureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class NhiService {

    private final NhiProcedureRepository nhiProcedureRepository;

    public NhiService(NhiProcedureRepository nhiProcedureRepository) {
        this.nhiProcedureRepository = nhiProcedureRepository;
    }

    public void checkExclude(List<NhiProcedure> nhiProcedures, Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        Map<String, String> excludes = nhiProcedures
            .stream()
            .filter(nhiProcedure -> nhiProcedure.getExclude() != null)
            .collect(Collectors.toMap(NhiProcedure::getCode, NhiProcedure::getExclude));
        nhiExtendTreatmentProcedures.forEach(nhiExtendTreatmentProcedure -> {
            String exclude = excludes.get(nhiExtendTreatmentProcedure.getA73());
            if (exclude != null) {
                Set<String> otherProcedureCodes = nhiExtendTreatmentProcedures
                    .stream()
                    .filter(procedure -> procedure != nhiExtendTreatmentProcedure)
                    .map(NhiExtendTreatmentProcedure::getA73)
                    .collect(Collectors.toSet());
                String[] excludeCodes = exclude.split(",");
                Arrays.stream(excludeCodes).forEach(code -> {
                    if (code.contains("~")) {
                        String tail = "";
                        if (code.contains("C")) {
                            tail = "C";
                        } else if (code.contains("B")) {
                            tail = "B";
                        }

                        String[] range = code.replace(tail,"").split("~");
                        int low = Integer.valueOf(range[0]);
                        int high = Integer.valueOf(range[1]);
                        for (int i = low; i <= high; i++) {
                            if (otherProcedureCodes.contains(i + tail)) {
                                nhiExtendTreatmentProcedure.setCheck("代碼 " + nhiExtendTreatmentProcedure.getA73() + " 不得與代碼 " + i + tail + " 同時申報");
                            }
                        }
                    } else {
                        if (otherProcedureCodes.contains(code)) {
                            nhiExtendTreatmentProcedure.setCheck("代碼 " + nhiExtendTreatmentProcedure.getA73() + " 不得與代碼 " + code + " 同時申報");
                        }
                    }
                });
            }
        });
    }
}
