package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.Disposal;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ScenarioScope
@Data
public class DisposalTestInfoHolder {

    private Disposal disposal;

    private List<Disposal> disposalHistoryList = new ArrayList<>();

    public void addDisposal(Disposal disposal) {
        this.disposalHistoryList.add(disposal);
    }

}
