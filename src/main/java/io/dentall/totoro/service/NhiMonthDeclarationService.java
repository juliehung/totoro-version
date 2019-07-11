package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiMonthDeclaration;
import io.dentall.totoro.repository.NhiMonthDeclarationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NhiMonthDeclaration.
 */
@Service
@Transactional
public class NhiMonthDeclarationService {

    private final Logger log = LoggerFactory.getLogger(NhiMonthDeclarationService.class);

    private final NhiMonthDeclarationRepository nhiMonthDeclarationRepository;

    public NhiMonthDeclarationService(NhiMonthDeclarationRepository nhiMonthDeclarationRepository) {
        this.nhiMonthDeclarationRepository = nhiMonthDeclarationRepository;
    }

    /**
     * Save a nhiMonthDeclaration.
     *
     * @param nhiMonthDeclaration the entity to save
     * @return the persisted entity
     */
    public NhiMonthDeclaration save(NhiMonthDeclaration nhiMonthDeclaration) {
        log.debug("Request to save NhiMonthDeclaration : {}", nhiMonthDeclaration);
        return nhiMonthDeclarationRepository.save(nhiMonthDeclaration);
    }

    /**
     * Get all the nhiMonthDeclarations.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiMonthDeclaration> findAll() {
        log.debug("Request to get all NhiMonthDeclarations");
        return nhiMonthDeclarationRepository.findAll();
    }


    /**
     * Get one nhiMonthDeclaration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiMonthDeclaration> findOne(Long id) {
        log.debug("Request to get NhiMonthDeclaration : {}", id);
        return nhiMonthDeclarationRepository.findById(id);
    }

    /**
     * Delete the nhiMonthDeclaration by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiMonthDeclaration : {}", id);
        nhiMonthDeclarationRepository.deleteById(id);
    }

    public Optional<NhiMonthDeclaration> findByYM(String ym) {
        log.debug("Request to get NhiMonthDeclaration by year-month : {}", ym);
        return nhiMonthDeclarationRepository.findByYearMonth(ym);
    }
}
