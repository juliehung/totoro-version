package io.dentall.totoro.service;

import io.dentall.totoro.domain.NHIExtendTreatmentDrug;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.repository.NHIExtendTreatmentDrugRepository;
import io.dentall.totoro.repository.TreatmentDrugRepository;
import io.dentall.totoro.service.util.MedicalAreaUtil;
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
 * Service Implementation for managing NHIExtendTreatmentDrug.
 */
@Service
@Transactional
public class NHIExtendTreatmentDrugService {

    private final Logger log = LoggerFactory.getLogger(NHIExtendTreatmentDrugService.class);

    private final NHIExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository;

    private final TreatmentDrugRepository treatmentDrugRepository;

    public NHIExtendTreatmentDrugService(NHIExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository, TreatmentDrugRepository treatmentDrugRepository) {
        this.nhiExtendTreatmentDrugRepository = nhiExtendTreatmentDrugRepository;
        this.treatmentDrugRepository = treatmentDrugRepository;
    }

    /**
     * Save a nhiExtendTreatmentDrug.
     *
     * @param nhiExtendTreatmentDrug the entity to save
     * @return the persisted entity
     */
    public NHIExtendTreatmentDrug save(NHIExtendTreatmentDrug nhiExtendTreatmentDrug) {
        log.debug("Request to save NHIExtendTreatmentDrug : {}", nhiExtendTreatmentDrug);

        TreatmentDrug treatmentDrug = nhiExtendTreatmentDrug.getTreatmentDrug();
        if (treatmentDrug == null || treatmentDrug.getId() == null) {
            throw ProblemUtil.notFoundException("treatmentDrug");
        }

        treatmentDrug = treatmentDrugRepository.findById(treatmentDrug.getId()).orElseThrow(() -> ProblemUtil.notFoundException("treatmentDrug"));
        if (treatmentDrug.getNhiExtendTreatmentDrug() != null) {
            throw new ProblemUtil("A treatmentDrug already has nhiExtendTreatmentDrug", Status.BAD_REQUEST);
        }

        return nhiExtendTreatmentDrugRepository.save(nhiExtendTreatmentDrug.treatmentDrug(treatmentDrug));
    }

    /**
     * Get all the nhiExtendTreatmentDrugs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NHIExtendTreatmentDrug> findAll(Pageable pageable) {
        log.debug("Request to get all NHIExtendTreatmentDrugs");
        return nhiExtendTreatmentDrugRepository.findAll(pageable);
    }


    /**
     * Get one nhiExtendTreatmentDrug by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NHIExtendTreatmentDrug> findOne(Long id) {
        log.debug("Request to get NHIExtendTreatmentDrug : {}", id);
        return nhiExtendTreatmentDrugRepository.findById(id);
    }

    /**
     * Delete the nhiExtendTreatmentDrug by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NHIExtendTreatmentDrug : {}", id);
        nhiExtendTreatmentDrugRepository.deleteById(id);
    }

    /**
     * Update the nhiExtendTreatmentDrug.
     *
     * @param updateNHIExtendTreatmentDrug the update entity
     */
    public NHIExtendTreatmentDrug update(NHIExtendTreatmentDrug updateNHIExtendTreatmentDrug) {
        log.debug("Request to update NHIExtendTreatmentDrug : {}", updateNHIExtendTreatmentDrug);

        return nhiExtendTreatmentDrugRepository
            .findById(updateNHIExtendTreatmentDrug.getId())
            .map(nhiExtendTreatmentDrug -> {
                MedicalAreaUtil.update(nhiExtendTreatmentDrug, updateNHIExtendTreatmentDrug);

                return nhiExtendTreatmentDrug;
            })
            .get();
    }
}
