package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.repository.NhiProcedureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
            // https://stackoverflow.com/a/51494637
            .collect(Collectors.toMap(NhiProcedure::getCode, NhiProcedure::getExclude, (exclude1, exclude2) -> exclude1));
        nhiExtendTreatmentProcedures.forEach(nhiExtendTreatmentProcedure -> {
            String exclude = excludes.get(nhiExtendTreatmentProcedure.getA73());
            if (exclude != null) {
                StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck() == null ? "" : nhiExtendTreatmentProcedure.getCheck());
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
                                check.append("代碼 ").append(nhiExtendTreatmentProcedure.getA73()).append(" 不得與代碼 ").append(i).append(tail).append(" 同時申報\n");
                            }
                        }
                    } else {
                        if (otherProcedureCodes.contains(code)) {
                            check.append("代碼 ").append(nhiExtendTreatmentProcedure.getA73()).append(" 不得與代碼 ").append(code).append(" 同時申報\n");
                        }
                    }
                });

                nhiExtendTreatmentProcedure.setCheck(check.toString());
            }
        });
    }

    public void checkFdi(List<NhiProcedure> nhiProcedures, Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        Map<String, String> fdis = nhiProcedures
            .stream()
            .filter(nhiProcedure -> nhiProcedure.getFdi() != null)
            .collect(Collectors.toMap(NhiProcedure::getCode, NhiProcedure::getFdi, (fdi1, fdi2) -> fdi1));
        nhiExtendTreatmentProcedures.forEach(nhiExtendTreatmentProcedure -> {
            String fdi = fdis.get(nhiExtendTreatmentProcedure.getA73());
            if (fdi != null) {
                StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck() == null ? "" : nhiExtendTreatmentProcedure.getCheck());
                String A74 = nhiExtendTreatmentProcedure.getA74();
                if (A74 == null || A74.isEmpty()) {
                    check.append("代碼 ").append(nhiExtendTreatmentProcedure.getA73()).append(" 需填入牙位\n");
                } else {
                    String[] fdiPositions = fdi.split(",");
                    Set<String> checkPositions = new HashSet<>();
                    Arrays.stream(fdiPositions).forEach(fdiPosition -> {
                        if (fdiPosition.contains("~")) {
                            String[] range = fdiPosition.split("~");
                            int low = Integer.valueOf(range[0]);
                            int high = Integer.valueOf(range[1]);
                            for (int i = low; i <= high; i++) {
                                checkPositions.add(String.valueOf(i));
                            }
                        } else {
                            checkPositions.add(fdiPosition);
                        }
                    });

                    // https://stackoverflow.com/a/4788718
                    String[] positions = A74.split("(?<=\\G.{2})");
                    Arrays.stream(positions).forEach(position -> {
                        if (!checkPositions.contains(position)) {
                            check.append("代碼 ").append(nhiExtendTreatmentProcedure.getA73()).append(" 需填入正確牙位\n");
                        }
                    });
                }

                nhiExtendTreatmentProcedure.setCheck(check.toString());
            }
        });
    }
}
