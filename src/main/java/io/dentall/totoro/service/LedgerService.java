package io.dentall.totoro.service;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.repository.LedgerRepository;
import io.dentall.totoro.repository.TreatmentPlanRepository;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupUpdateVM;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Ledger.
 */
@Service
@Transactional
public class LedgerService {

    private final Logger log = LoggerFactory.getLogger(LedgerService.class);

    private final LedgerRepository ledgerRepository;

    private final TreatmentPlanRepository treatmentPlanRepository;

    public LedgerService(LedgerRepository ledgerRepository, TreatmentPlanRepository treatmentPlanRepository) {
        this.ledgerRepository = ledgerRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
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
    public void delete(Long id) {
        log.debug("Request to delete Ledger : {}", id);
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

                if (updateLedger.getPrintTime() != null) {
                    ledger.setPrintTime(updateLedger.getPrintTime());
                }

                return ledger;
            })
            .get();
    }
}
