package io.dentall.totoro.service.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface AccountingDTO {

    @JsonProperty("id")
    Long getRegistration_Accounting_Id();

    @JsonProperty("registrationFee")
    Double getRegistration_Accounting_RegistrationFee();

    @JsonProperty("partialBurden")
    Double getRegistration_Accounting_PartialBurden();

    @JsonProperty("deposit")
    Double getRegistration_Accounting_Deposit();

    @JsonProperty("ownExpense")
    Double getRegistration_Accounting_OwnExpense();

    @JsonProperty("other")
    Double getRegistration_Accounting_Other();

    @JsonProperty("patientIdentity")
    String getRegistration_Accounting_PatientIdentity();

    @JsonProperty("discountReason")
    String getRegistration_Accounting_DiscountReason();

    @JsonProperty("discount")
    Double getRegistration_Accounting_Discount();

    @JsonProperty("withdrawal")
    Double getRegistration_Accounting_Withdrawal();

    @JsonProperty("transactionTime")
    Instant getRegistration_Accounting_TransactionTime();

    @JsonProperty("staff")
    String getRegistration_Accounting_Staff();

    @JsonProperty("copaymentExemption")
    Boolean getRegistration_Accounting_CopaymentExemption();

}
