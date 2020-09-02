package io.dentall.totoro.service;

import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.repository.AccountingRepository;
import io.dentall.totoro.repository.HospitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing accounting.
 */
@Service
@Transactional
public class AccountingService {

    private final Logger log = LoggerFactory.getLogger(AccountingService.class);

    private final AccountingRepository accountingRepository;

    private final HospitalRepository hospitalRepository;

    public AccountingService(AccountingRepository accountingRepository, HospitalRepository hospitalRepository) {
        this.accountingRepository = accountingRepository;
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * Save a accounting.
     *
     * @param accounting the entity to save
     * @return the persisted entity
     */
    public Accounting save(Accounting accounting) {
        log.debug("Request to save Accounting : {}", accounting);

        return accountingRepository.save(accounting);
    }

    /**
     * Update the accounting.
     *
     * @param updateAccounting the update entity
     */
    public Accounting update(Accounting updateAccounting) {
        log.debug("Request to update Accounting : {}", updateAccounting);

        return accountingRepository
            .findById(updateAccounting.getId())
            .map(accounting -> {
                if (updateAccounting.getRegistrationFee() != null) {
                    accounting.setRegistrationFee((updateAccounting.getRegistrationFee()));
                }

                if (updateAccounting.getPartialBurden() != null) {
                    accounting.setPartialBurden((updateAccounting.getPartialBurden()));
                }

                if (updateAccounting.getDeposit() != null) {
                    accounting.setDeposit((updateAccounting.getDeposit()));
                }

                if (updateAccounting.getOwnExpense() != null) {
                    accounting.setOwnExpense((updateAccounting.getOwnExpense()));
                }

                if (updateAccounting.getOther() != null) {
                    accounting.setOther((updateAccounting.getOther()));
                }

                if (updateAccounting.getPatientIdentity() != null) {
                    accounting.setPatientIdentity((updateAccounting.getPatientIdentity()));
                }

                if (updateAccounting.getDiscountReason() != null) {
                    accounting.setDiscountReason((updateAccounting.getDiscountReason()));
                }

                if (updateAccounting.getDiscount() != null) {
                    accounting.setDiscount((updateAccounting.getDiscount()));
                }

                if (updateAccounting.getWithdrawal() != null) {
                    accounting.setWithdrawal(updateAccounting.getWithdrawal());
                }

                if (updateAccounting.getTransactionTime() != null) {
                    accounting.setTransactionTime(updateAccounting.getTransactionTime());
                }

                if (updateAccounting.getStaff() != null) {
                    accounting.setStaff(updateAccounting.getStaff());
                }

                if (updateAccounting.getHospital() != null && updateAccounting.getHospital().getId() != null) {
                    hospitalRepository.findById(updateAccounting.getHospital().getId()).ifPresent(accounting::setHospital);
                }

                if (updateAccounting.isCopaymentExemption() != null) {
                    accounting.setCopaymentExemption(updateAccounting.isCopaymentExemption());
                }

                return accounting;
            })
            .get();
    }
}
