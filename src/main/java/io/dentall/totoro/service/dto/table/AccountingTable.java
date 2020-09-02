package io.dentall.totoro.service.dto.table;

import java.time.Instant;

public interface AccountingTable {
    Long getId();
    Double getRegistrationFee();
    Double getPartialBurden();
    Double getDeposit();
    Double getOwnExpense();
    Double getOther();
    String getPatientIdentity();
    String getDiscountReason();
    Double getDiscount();
    Double getWithdrawal();
    Instant getTransactionTime();
    String getStaff();
    Boolean getCopaymentExemption();

    // Relationship
    Long getHospital_Id();
}
