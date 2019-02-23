package io.dentall.totoro.service;

import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TreatmentDrug.
 */
@Service
@Transactional
public class TreatmentDrugService {

    private final Logger log = LoggerFactory.getLogger(TreatmentDrugService.class);

    private final TreatmentDrugRepository treatmentDrugRepository;

    private final DrugRepository drugRepository;

    private final PrescriptionRepository prescriptionRepository;

    public TreatmentDrugService(
        TreatmentDrugRepository treatmentDrugRepository,
        DrugRepository drugRepository,
        PrescriptionRepository prescriptionRepository
    ) {
        this.treatmentDrugRepository = treatmentDrugRepository;
        this.drugRepository = drugRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Save a treatmentDrug.
     *
     * @param treatmentDrug the entity to save
     * @return the persisted entity
     */
    public TreatmentDrug save(TreatmentDrug treatmentDrug) {
        log.debug("Request to save TreatmentDrug : {}", treatmentDrug);
        return treatmentDrugRepository.save(treatmentDrug);
    }

    /**
     * Get all the treatmentDrugs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TreatmentDrug> findAll(Pageable pageable) {
        log.debug("Request to get all TreatmentDrugs");
        return treatmentDrugRepository.findAll(pageable);
    }


    /**
     * Get one treatmentDrug by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentDrug> findOne(Long id) {
        log.debug("Request to get TreatmentDrug : {}", id);
        return treatmentDrugRepository.findById(id);
    }

    /**
     * Delete the treatmentDrug by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentDrug : {}", id);
        treatmentDrugRepository.deleteById(id);
    }

    /**
     * Update the treatmentDrug.
     *
     * @param updateTreatmentDrug the update entity
     * @return the entity
     */
    public TreatmentDrug update(TreatmentDrug updateTreatmentDrug) {
        log.debug("Request to update TreatmentDrug : {}", updateTreatmentDrug);

        return treatmentDrugRepository
            .findById(updateTreatmentDrug.getId())
            .map(treatmentDrug -> {
                if (updateTreatmentDrug.getDay() != null) {
                    treatmentDrug.setDay((updateTreatmentDrug.getDay()));
                }

                if (updateTreatmentDrug.getDrug() != null && updateTreatmentDrug.getDrug().getId() != null) {
                    drugRepository.findById(updateTreatmentDrug.getDrug().getId()).ifPresent(treatmentDrug::setDrug);
                }

                if (updateTreatmentDrug.getPrescription() != null && updateTreatmentDrug.getPrescription().getId() != null) {
                    prescriptionRepository.findById(updateTreatmentDrug.getPrescription().getId()).ifPresent(treatmentDrug::setPrescription);
                }

                return treatmentDrug;
            })
            .get();
    }
}
