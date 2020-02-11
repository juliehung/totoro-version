package io.dentall.totoro.business.dto;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonalNhiExtendTreatmentProcedureMap {
    private Map<String, PersonalNhiExtendTreatmentProcedure> personalNhiExtendTreatmentProcedures = new HashMap<>();

    public PersonalNhiExtendTreatmentProcedureMap nhiExtendTreatmentProcedure(Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        nhiExtendTreatmentProcedures.stream().forEach(nhiExtendTreatmentProcedure -> {
            String key = nhiExtendTreatmentProcedure.getA73();
            NhiExtendDisposal nhiExtendDisposal = nhiExtendTreatmentProcedure.getTreatmentProcedure()
                .getDisposal()
                .getNhiExtendDisposals().iterator().next();
            LocalDate declarationDate = nhiExtendDisposal.getReplenishmentDate() == null
                    ? nhiExtendDisposal.getDate()
                    : nhiExtendDisposal.getReplenishmentDate();

            if (personalNhiExtendTreatmentProcedures.containsKey(key)) {
                this.personalNhiExtendTreatmentProcedures.get(key).pushDeclarationDate(declarationDate);
            } else {
                this.personalNhiExtendTreatmentProcedures.put(key,
                    new PersonalNhiExtendTreatmentProcedure().code(key)
                        .declarationDate(declarationDate)
                );
            }
        });
        return this;
    }

    public boolean containPersonalNhiExtendTreatmentProcedure(String key) {
        return this.personalNhiExtendTreatmentProcedures.containsKey(key);
    }

    public List<String> getPersonalTreatmentProcedureCodeList() {
        return this.personalNhiExtendTreatmentProcedures.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public Map<String, PersonalNhiExtendTreatmentProcedure> getPersonalNhiExtendTreatmentProcedures() {
        return this.personalNhiExtendTreatmentProcedures;
    }

    public PersonalNhiExtendTreatmentProcedure getPersonalNhiExtendTreatmentProcedure(String key) {
        return this.personalNhiExtendTreatmentProcedures.get(key);
    }
}
