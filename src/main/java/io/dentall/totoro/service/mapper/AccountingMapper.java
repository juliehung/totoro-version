package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.domain.Hospital;
import io.dentall.totoro.service.dto.table.AccountingTable;

public class AccountingMapper {

    public static Accounting accountingTableToAccounting(AccountingTable accountingTable) {
        Accounting accounting = new Accounting();

        accounting.setId(accountingTable.getId());
        accounting.setRegistrationFee(accountingTable.getRegistrationFee());
        accounting.setPartialBurden(accountingTable.getPartialBurden());
        accounting.setDeposit(accountingTable.getDeposit());
        accounting.setOwnExpense(accountingTable.getOwnExpense());
        accounting.setOther(accountingTable.getOther());
        accounting.setPatientIdentity(accountingTable.getPatientIdentity());
        accounting.setDiscountReason(accountingTable.getDiscountReason());
        accounting.setDiscount(accountingTable.getDiscount());
        accounting.setWithdrawal(accountingTable.getWithdrawal());
        accounting.setTransactionTime(accountingTable.getTransactionTime());
        accounting.setStaff(accountingTable.getStaff());

        Hospital hospital = new Hospital();
        hospital.setId(accountingTable.getHospital_Id());
        accounting.setHospital(hospital);
        return accounting;
    }
}
