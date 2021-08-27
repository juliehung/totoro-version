package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@Component
@ScenarioScope
@Data
public class NhiRuleCheckTestInfoHolder {

    private String nhiCode;

    private NhiRuleCheckVM nhiRuleCheckVM;

    private ResultActions resultActions;

    private List<NhiRuleCheckTxSnapshot> nhiRuleCheckTxSnapshotList;

}
