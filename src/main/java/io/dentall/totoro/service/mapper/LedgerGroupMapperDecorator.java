package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.LedgerGroup;
import io.dentall.totoro.domain.LedgerReceipt;
import io.dentall.totoro.web.rest.vm.LedgerReceiptCreateVM;

public abstract class LedgerGroupMapperDecorator implements LedgerGroupMapper {

    private final LedgerGroupMapper delegate;

    public LedgerGroupMapperDecorator(
        LedgerGroupMapper delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    public LedgerReceipt convertLedgerReceiptFromCreateVMToDomain(LedgerReceiptCreateVM ledgerReceiptCreateVM) {
        LedgerReceipt ledgerReceipt =
            delegate.convertLedgerReceiptFromCreateVMToDomain(ledgerReceiptCreateVM);
        LedgerGroup ledgerGroup = new LedgerGroup();
        ledgerGroup.setId(ledgerReceiptCreateVM.getGid());
        ledgerReceipt.setLedgerGroup(ledgerGroup);

        return ledgerReceipt;
    }

}
