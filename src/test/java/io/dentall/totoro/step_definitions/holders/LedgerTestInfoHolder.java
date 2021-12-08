package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.web.rest.vm.LedgerReceiptPrintedRecordVM;
import io.dentall.totoro.web.rest.vm.LedgerReceiptVM;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ScenarioScope
@Data
public class LedgerTestInfoHolder {

    private LedgerGroup ledgerGroup;

    private List<Ledger> ledgers = new ArrayList<>();

    private List<LedgerReceiptVM> ledgerReceipts = new ArrayList<>();

    private List<LedgerReceiptPrintedRecordVM> ledgerReceiptPrintedRecords = new ArrayList<>();

}
