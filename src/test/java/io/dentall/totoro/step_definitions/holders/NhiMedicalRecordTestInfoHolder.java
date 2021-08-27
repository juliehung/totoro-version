package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.NhiMedicalRecord;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ScenarioScope
@Data
public class NhiMedicalRecordTestInfoHolder {

    private List<NhiMedicalRecord> nhiMedicalRecordList = new ArrayList<>();

    public void addNhiMedicalRecord(NhiMedicalRecord nhiMedicalRecord) {
        this.nhiMedicalRecordList.add(nhiMedicalRecord);
    }
}
