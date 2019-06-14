package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

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

    private final NhiExtendTreatmentDrugService nhiExtendTreatmentDrugService;

    public TreatmentDrugService(
        TreatmentDrugRepository treatmentDrugRepository,
        DrugRepository drugRepository,
        PrescriptionRepository prescriptionRepository,
        NhiExtendTreatmentDrugService nhiExtendTreatmentDrugService
    ) {
        this.treatmentDrugRepository = treatmentDrugRepository;
        this.drugRepository = drugRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.nhiExtendTreatmentDrugService = nhiExtendTreatmentDrugService;
    }

    /**
     * Save a treatmentDrug.
     *
     * @param treatmentDrug the entity to save
     * @return the persisted entity
     */
    public TreatmentDrug save(TreatmentDrug treatmentDrug) {
        log.debug("Request to save TreatmentDrug : {}", treatmentDrug);

        NhiExtendTreatmentDrug nhiExtendTreatmentDrug = treatmentDrug.getNhiExtendTreatmentDrug();
        treatmentDrugRepository.save(treatmentDrug.nhiExtendTreatmentDrug(null));

        if (nhiExtendTreatmentDrug != null) {
            treatmentDrug.setNhiExtendTreatmentDrug(getNhiExtendTreatmentDrug(nhiExtendTreatmentDrug.treatmentDrug(treatmentDrug)));
        }

        return treatmentDrug;
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

        treatmentDrugRepository.findById(id).ifPresent(treatmentDrug -> {
            if (treatmentDrug.getNhiExtendTreatmentDrug() != null) {
                throw new ProblemUtil("A treatmentDrug which has nhiExtendTreatmentDrug cannot delete", Status.BAD_REQUEST);
            }

            if (treatmentDrug.getPrescription() != null) {
                throw new ProblemUtil("A treatmentDrug which has prescription cannot delete", Status.BAD_REQUEST);
            }

            treatmentDrugRepository.deleteById(id);
        });
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

                if (updateTreatmentDrug.getFrequency() != null) {
                    treatmentDrug.setFrequency((updateTreatmentDrug.getFrequency()));
                }

                if (updateTreatmentDrug.getWay() != null) {
                    treatmentDrug.setWay(updateTreatmentDrug.getWay());
                }

                if (updateTreatmentDrug.getQuantity() != null) {
                    treatmentDrug.setQuantity(updateTreatmentDrug.getQuantity());
                }

                if (updateTreatmentDrug.getDrug() != null && updateTreatmentDrug.getDrug().getId() != null) {
                    drugRepository.findById(updateTreatmentDrug.getDrug().getId()).ifPresent(treatmentDrug::setDrug);
                }

                if (updateTreatmentDrug.getPrescription() != null && updateTreatmentDrug.getPrescription().getId() != null) {
                    prescriptionRepository.findById(updateTreatmentDrug.getPrescription().getId()).ifPresent(treatmentDrug::setPrescription);
                }

                if (updateTreatmentDrug.getNhiExtendTreatmentDrug() != null) {
                    NhiExtendTreatmentDrug nhiExtendTreatmentDrug = updateTreatmentDrug.getNhiExtendTreatmentDrug();
                    treatmentDrug.setNhiExtendTreatmentDrug(getNhiExtendTreatmentDrug(nhiExtendTreatmentDrug.treatmentDrug(treatmentDrug)));
                }

                return treatmentDrug;
            })
            .get();
    }

    private NhiExtendTreatmentDrug getNhiExtendTreatmentDrug(NhiExtendTreatmentDrug nhiExtendTreatmentDrug) {
        return nhiExtendTreatmentDrug.getId() == null ? nhiExtendTreatmentDrugService.save(nhiExtendTreatmentDrug) : nhiExtendTreatmentDrugService.update(nhiExtendTreatmentDrug);
    }
}
