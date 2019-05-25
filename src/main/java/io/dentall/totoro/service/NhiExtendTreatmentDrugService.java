package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.repository.NhiExtendTreatmentDrugRepository;
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
 * Service Implementation for managing NhiExtendTreatmentDrug.
 */
@Service
@Transactional
public class NhiExtendTreatmentDrugService {

    private final Logger log = LoggerFactory.getLogger(NhiExtendTreatmentDrugService.class);

    private final NhiExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository;

    private final TreatmentDrugRepository treatmentDrugRepository;

    public NhiExtendTreatmentDrugService(
        NhiExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository,
        TreatmentDrugRepository treatmentDrugRepository
    ) {
        this.nhiExtendTreatmentDrugRepository = nhiExtendTreatmentDrugRepository;
        this.treatmentDrugRepository = treatmentDrugRepository;
    }

    /**
     * Save a nhiExtendTreatmentDrug.
     *
     * @param nhiExtendTreatmentDrug the entity to save
     * @return the persisted entity
     */
    public NhiExtendTreatmentDrug save(NhiExtendTreatmentDrug nhiExtendTreatmentDrug) {
        log.debug("Request to save NhiExtendTreatmentDrug : {}", nhiExtendTreatmentDrug);

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
    public Page<NhiExtendTreatmentDrug> findAll(Pageable pageable) {
        log.debug("Request to get all NhiExtendTreatmentDrug");
        return nhiExtendTreatmentDrugRepository.findAll(pageable);
    }


    /**
     * Get one nhiExtendTreatmentDrug by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiExtendTreatmentDrug> findOne(Long id) {
        log.debug("Request to get NhiExtendTreatmentDrug : {}", id);
        return nhiExtendTreatmentDrugRepository.findById(id);
    }

    /**
     * Delete the nhiExtendTreatmentDrug by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiExtendTreatmentDrug : {}", id);
        nhiExtendTreatmentDrugRepository.deleteById(id);
    }

    /**
     * Update the nhiExtendTreatmentDrug.
     *
     * @param updateNhiExtendTreatmentDrug the update entity
     */
    public NhiExtendTreatmentDrug update(NhiExtendTreatmentDrug updateNhiExtendTreatmentDrug) {
        log.debug("Request to update NhiExtendTreatmentDrug : {}", updateNhiExtendTreatmentDrug);

        return nhiExtendTreatmentDrugRepository
            .findById(updateNhiExtendTreatmentDrug.getId())
            .map(nhiExtendTreatmentDrug -> {
                MedicalAreaUtil.update(nhiExtendTreatmentDrug, updateNhiExtendTreatmentDrug);

                return nhiExtendTreatmentDrug;
            })
            .get();
    }
}
