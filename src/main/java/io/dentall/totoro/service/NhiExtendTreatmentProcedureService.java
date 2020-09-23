package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.mapper.NhiExtendTreatmentProcedureMapper;
import io.dentall.totoro.service.util.MedicalAreaUtil;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.Status;

import java.util.Optional;

/**
 * Service Implementation for managing NhiExtendTreatmentProcedure.
 */
@Service
@Transactional
public class NhiExtendTreatmentProcedureService {

    private final Logger log = LoggerFactory.getLogger(NhiExtendTreatmentProcedureService.class);

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    public NhiExtendTreatmentProcedureService(
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        TreatmentProcedureRepository treatmentProcedureRepository,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper
    ) {
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
    }

    /**
     * Save a nhiExtendTreatmentProcedure.
     *
     * @param nhiExtendTreatmentProcedure the entity to save
     * @return the persisted entity
     */
    public NhiExtendTreatmentProcedure save(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        log.debug("Request to save NhiExtendTreatmentProcedure : {}", nhiExtendTreatmentProcedure);

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
    public Page<NhiExtendTreatmentProcedure> findAll(Pageable pageable) {
        log.debug("Request to get all NhiExtendTreatmentProcedure");
        return nhiExtendTreatmentProcedureRepository.findAll(pageable);
    }


    /**
     * Get one nhiExtendTreatmentProcedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiExtendTreatmentProcedure> findOne(Long id) {
        log.debug("Request to get NhiExtendTreatmentProcedure : {}", id);
        return nhiExtendTreatmentProcedureRepository.findById(id);
    }

    /**
     * Delete the nhiExtendTreatmentProcedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiExtendTreatmentProcedure : {}", id);
        nhiExtendTreatmentProcedureRepository.deleteById(id);
    }

    /**
     * Update the nhiExtendTreatmentProcedure.
     *
     * @param updateNhiExtendTreatmentProcedure the update entity
     */
    public NhiExtendTreatmentProcedure update(NhiExtendTreatmentProcedure updateNhiExtendTreatmentProcedure) {
        log.debug("Request to update NhiExtendTreatmentProcedure : {}", updateNhiExtendTreatmentProcedure);

        return nhiExtendTreatmentProcedureRepository
            .findById(updateNhiExtendTreatmentProcedure.getId())
            .map(nhiExtendTreatmentProcedure -> {
                MedicalAreaUtil.update(nhiExtendTreatmentProcedure, updateNhiExtendTreatmentProcedure);

                return nhiExtendTreatmentProcedure;
            })
            .get();
    }

    public NhiExtendTreatmentProcedure findNhiExtendTreatmentProcedureById(Long id) {
        return nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(
            nhiExtendTreatmentProcedureRepository.findById(id, NhiExtendTreatmentProcedureTable.class)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to found resource")));
    }
}
