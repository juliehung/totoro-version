package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.*;
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

    private List<LedgerReceipt> ledgerReceipts = new ArrayList<>();

    private List<LedgerReceiptPrintedRecord> ledgerReceiptPrintedRecords = new ArrayList<>();

}
