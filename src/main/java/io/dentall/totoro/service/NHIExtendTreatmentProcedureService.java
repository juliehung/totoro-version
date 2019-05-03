package io.dentall.totoro.service;

import io.dentall.totoro.domain.NHIExtendTreatmentProcedure;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.NHIExtendTreatmentProcedureRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
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
 * Service Implementation for managing NHIExtendTreatmentProcedure.
 */
@Service
@Transactional
public class NHIExtendTreatmentProcedureService {

    private final Logger log = LoggerFactory.getLogger(NHIExtendTreatmentProcedureService.class);

    private final NHIExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    public NHIExtendTreatmentProcedureService(NHIExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository, TreatmentProcedureRepository treatmentProcedureRepository) {
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
    }

    /**
     * Save a nhiExtendTreatmentProcedure.
     *
     * @param nhiExtendTreatmentProcedure the entity to save
     * @return the persisted entity
     */
    public NHIExtendTreatmentProcedure save(NHIExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        log.debug("Request to save NHIExtendTreatmentProcedure : {}", nhiExtendTreatmentProcedure);

        TreatmentProcedure treatmentProcedure = nhiExtendTreatmentProcedure.getTreatmentProcedure();
        if (treatmentProcedure == null || treatmentProcedure.getId() == null) {
            throw ProblemUtil.notFoundException("treatmentProcedure");
        }

        treatmentProcedure = treatmentProcedureRepository.findById(treatmentProcedure.getId()).orElseThrow(() -> ProblemUtil.notFoundException("treatmentProcedure"));
        if (treatmentProcedure.getNhiExtendTreatmentProcedure() != null) {
            throw new ProblemUtil("A treatmentProcedure already has nhiExtendTreatmentProcedure", Status.BAD_REQUEST);
        }

        return nhiExtendTreatmentProcedureRepository.save(nhiExtendTreatmentProcedure.treatmentProcedure(treatmentProcedure));
    }

    /**
     * Get all the nhiExtendTreatmentProcedures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NHIExtendTreatmentProcedure> findAll(Pageable pageable) {
        log.debug("Request to get all NHIExtendTreatmentProcedures");
        return nhiExtendTreatmentProcedureRepository.findAll(pageable);
    }


    /**
     * Get one nhiExtendTreatmentProcedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NHIExtendTreatmentProcedure> findOne(Long id) {
        log.debug("Request to get NHIExtendTreatmentProcedure : {}", id);
        return nhiExtendTreatmentProcedureRepository.findById(id);
    }

    /**
     * Delete the nhiExtendTreatmentProcedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NHIExtendTreatmentProcedure : {}", id);
        nhiExtendTreatmentProcedureRepository.deleteById(id);
    }

    /**
     * Update the nhiExtendTreatmentProcedure.
     *
     * @param updateNHIExtendTreatmentProcedure the update entity
     */
    public NHIExtendTreatmentProcedure update(NHIExtendTreatmentProcedure updateNHIExtendTreatmentProcedure) {
        log.debug("Request to update NHIExtendTreatmentProcedure : {}", updateNHIExtendTreatmentProcedure);

        return nhiExtendTreatmentProcedureRepository
            .findById(updateNHIExtendTreatmentProcedure.getId())
            .map(nhiExtendTreatmentProcedure -> {
                MedicalAreaUtil.update(nhiExtendTreatmentProcedure, updateNHIExtendTreatmentProcedure);

                return nhiExtendTreatmentProcedure;
            })
            .get();
    }
}
