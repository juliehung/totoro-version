package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiMonthDeclarationDetails;
import io.dentall.totoro.repository.NhiMonthDeclarationDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NhiMonthDeclarationDetails.
 */
@Service
@Transactional
public class NhiMonthDeclarationDetailsService {

    private final Logger log = LoggerFactory.getLogger(NhiMonthDeclarationDetailsService.class);

    private final NhiMonthDeclarationDetailsRepository nhiMonthDeclarationDetailsRepository;

    public NhiMonthDeclarationDetailsService(NhiMonthDeclarationDetailsRepository nhiMonthDeclarationDetailsRepository) {
        this.nhiMonthDeclarationDetailsRepository = nhiMonthDeclarationDetailsRepository;
    }

    /**
     * Save a nhiMonthDeclarationDetails.
     *
     * @param nhiMonthDeclarationDetails the entity to save
     * @return the persisted entity
     */
    public NhiMonthDeclarationDetails save(NhiMonthDeclarationDetails nhiMonthDeclarationDetails) {
        log.debug("Request to save NhiMonthDeclarationDetails : {}", nhiMonthDeclarationDetails);
        return nhiMonthDeclarationDetailsRepository.save(nhiMonthDeclarationDetails);
    }

    /**
     * Get all the nhiMonthDeclarationDetails.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiMonthDeclarationDetails> findAll() {
        log.debug("Request to get all NhiMonthDeclarationDetails");
        return nhiMonthDeclarationDetailsRepository.findAll();
    }


    /**
     * Get one nhiMonthDeclarationDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiMonthDeclarationDetails> findOne(Long id) {
        log.debug("Request to get NhiMonthDeclarationDetails : {}", id);
        return nhiMonthDeclarationDetailsRepository.findById(id);
    }

    /**
     * Delete the nhiMonthDeclarationDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiMonthDeclarationDetails : {}", id);
        nhiMonthDeclarationDetailsRepository.deleteById(id);
    }
}
