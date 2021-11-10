package io.dentall.totoro.service;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.LedgerReceipt;
import io.dentall.totoro.repository.LedgerReceiptRepository;
import io.dentall.totoro.repository.LedgerRepository;
import io.dentall.totoro.repository.TreatmentPlanRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupUpdateVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ledger.
 */
@Service
@Transactional
public class LedgerService {

    private final Logger log = LoggerFactory.getLogger(LedgerService.class);

    private final LedgerRepository ledgerRepository;

    private final TreatmentPlanRepository treatmentPlanRepository;

    private final LedgerReceiptRepository ledgerReceiptRepository;

    public LedgerService(
        LedgerRepository ledgerRepository,
        TreatmentPlanRepository treatmentPlanRepository,
        LedgerReceiptRepository ledgerReceiptRepository
    ) {
        this.ledgerRepository = ledgerRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.ledgerReceiptRepository = ledgerReceiptRepository;
    }

    /**
     * Save a ledger.
     *
     * @param ledger the entity to save
     * @return the persisted entity
     */
    public Ledger save(Ledger ledger) {
        log.debug("Request to save Ledger : {}", ledger);
        return ledgerRepository.save(ledger);
    }

    /**
     * Get all the ledgers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Ledger> findAll(Pageable pageable) {
        log.debug("Request to get all Ledgers");
        return ledgerRepository.findAll(pageable);
    }


    /**
     * Get one ledger by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Ledger> findOne(Long id) {
        log.debug("Request to get Ledger : {}", id);
        return ledgerRepository.findById(id);
    }

    /**
     * Delete the ledger by id.
     *
     * @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Ledger : {}", id);
        Ledger ledger = ledgerRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Not found ledger by id", "LEDGER", "notfound"));
        for (LedgerReceipt ledgerReceipt : ledger.getLedgerReceipts()) {
            List<Ledger> ledgerList = ledgerReceipt.getLedgers().stream().filter(d -> !d.getId().equals(id)).collect(Collectors.toList());
            ledgerReceipt.setLedgers(ledgerList);
            if (ledgerList.size() == 0) {
                ledgerReceiptRepository.delete(ledgerReceipt);
            }
        }
        ledgerRepository.deleteById(id);
    }

    /**
     * Update the ledger.
     *
     * @param updateLedger the update entity
     * @return the entity
     */
    public Ledger update(LedgerUnwrapGroupUpdateVM updateLedger) {
        log.debug("Request to update Ledger : {}", updateLedger);

        return ledgerRepository
            .findById(updateLedger.getId())
            .map(ledger -> {

                if (updateLedger.getCharge() != null) {
                    ledger.setCharge(updateLedger.getCharge());
                }

                if (updateLedger.getNote() != null) {
                    ledger.setNote(updateLedger.getNote());
                }

                if (updateLedger.getDoctor() != null) {
                    ledger.setDoctor(updateLedger.getDoctor());
                }

                if (updateLedger.getDate() != null) {
                    ledger.setDate(updateLedger.getDate());
                }

                if (updateLedger.getIncludeStampTax() != null) {
                    ledger.setIncludeStampTax(updateLedger.getIncludeStampTax());
                }

                return ledger;
            })
            .get();
    }


}
