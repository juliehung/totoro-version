package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.repository.NhiProcedureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NhiProcedure.
 */
@Service
@Transactional
public class NhiProcedureService {

    private final Logger log = LoggerFactory.getLogger(NhiProcedureService.class);

    private final NhiProcedureRepository nhiProcedureRepository;

    public NhiProcedureService(NhiProcedureRepository nhiProcedureRepository) {
        this.nhiProcedureRepository = nhiProcedureRepository;
    }

    /**
     * Save a nhiProcedure.
     *
     * @param nhiProcedure the entity to save
     * @return the persisted entity
     */
    public NhiProcedure save(NhiProcedure nhiProcedure) {
        log.debug("Request to save NhiProcedure : {}", nhiProcedure);
        return nhiProcedureRepository.save(nhiProcedure);
    }

    /**
     * Get all the nhiProcedures.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiProcedure> findAll() {
        log.debug("Request to get all NhiProcedures");
        return nhiProcedureRepository.findAll();
    }


    /**
     * Get one nhiProcedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiProcedure> findOne(Long id) {
        log.debug("Request to get NhiProcedure : {}", id);
        return nhiProcedureRepository.findById(id);
    }

    /**
     * Delete the nhiProcedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiProcedure : {}", id);
        nhiProcedureRepository.deleteById(id);
    }

    /**
     * Update the nhiProcedure.
     *
     * @param updateNhiProcedure the update entity
     */
    public NhiProcedure update(NhiProcedure updateNhiProcedure) {
        log.debug("Request to update NhiProcedure : {}", updateNhiProcedure);

        return nhiProcedureRepository
            .findById(updateNhiProcedure.getId())
            .map(nhiProcedure -> {
                if (updateNhiProcedure.getName() != null) {
                    nhiProcedure.setName(updateNhiProcedure.getName());
                }

                if (updateNhiProcedure.getPoint() != null) {
                    nhiProcedure.setPoint(updateNhiProcedure.getPoint());
                }

                if (updateNhiProcedure.getEnglishName() != null) {
                    nhiProcedure.setEnglishName(updateNhiProcedure.getEnglishName());
                }

                if (updateNhiProcedure.getDefaultIcd10CmId() != null) {
                    nhiProcedure.setDefaultIcd10CmId(updateNhiProcedure.getDefaultIcd10CmId());
                }

                if (updateNhiProcedure.getDescription() != null) {
                    nhiProcedure.setDescription(updateNhiProcedure.getDescription());
                }

                if (updateNhiProcedure.getExclude() != null) {
                    nhiProcedure.setExclude(updateNhiProcedure.getExclude());
                }

                return nhiProcedure;
            })
            .get();
    }
}
