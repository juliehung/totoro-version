package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiDayUploadDetails;
import io.dentall.totoro.repository.NhiDayUploadDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NhiDayUploadDetails.
 */
@Service
@Transactional
public class NhiDayUploadDetailsService {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadDetailsService.class);

    private final NhiDayUploadDetailsRepository nhiDayUploadDetailsRepository;

    private final RelationshipService relationshipService;

    public NhiDayUploadDetailsService(NhiDayUploadDetailsRepository nhiDayUploadDetailsRepository, RelationshipService relationshipService) {
        this.nhiDayUploadDetailsRepository = nhiDayUploadDetailsRepository;
        this.relationshipService = relationshipService;
    }

    /**
     * Save a nhiDayUploadDetails.
     *
     * @param nhiDayUploadDetails the entity to save
     * @return the persisted entity
     */
    public NhiDayUploadDetails save(NhiDayUploadDetails nhiDayUploadDetails) {
        log.debug("Request to save NhiDayUploadDetails : {}", nhiDayUploadDetails);

        relationshipService.addRelationshipWithNhiExtendDisposals(nhiDayUploadDetails, nhiDayUploadDetails.getNhiExtendDisposals());

        return nhiDayUploadDetailsRepository.save(nhiDayUploadDetails);
    }

    /**
     * Get all the nhiDayUploadDetails.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiDayUploadDetails> findAll() {
        log.debug("Request to get all NhiDayUploadDetails");
        return nhiDayUploadDetailsRepository.findAll();
    }


    /**
     * Get one nhiDayUploadDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiDayUploadDetails> findOne(Long id) {
        log.debug("Request to get NhiDayUploadDetails : {}", id);
        Optional<NhiDayUploadDetails> optionalNhiDayUploadDetails = nhiDayUploadDetailsRepository.findById(id);
        // TODO: if some day it need upload details as history and display related nhi ext dis then uncomment below line.
        // optionalNhiDayUploadDetails.ifPresent(nhiDayUploadDetails -> nhiDayUploadDetails.getNhiExtendDisposals().size());
        return optionalNhiDayUploadDetails;
    }

    /**
     * Delete the nhiDayUploadDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiDayUploadDetails : {}", id);
        nhiDayUploadDetailsRepository.deleteById(id);
    }

    /**
     * Update the nhiDayUploadDetails.
     *
     * @param updateNhiDayUploadDetails the update entity
     * @return the entity
     */
    public NhiDayUploadDetails update(NhiDayUploadDetails updateNhiDayUploadDetails) {
        log.debug("Request to update NhiDayUploadDetails : {}", updateNhiDayUploadDetails);

        return nhiDayUploadDetailsRepository
            .findById(updateNhiDayUploadDetails.getId())
            .map(nhiDayUploadDetails -> {
                if (updateNhiDayUploadDetails.getType() != null) {
                    nhiDayUploadDetails.setType(updateNhiDayUploadDetails.getType());
                }

                relationshipService.addRelationshipWithNhiExtendDisposals(nhiDayUploadDetails, updateNhiDayUploadDetails.getNhiExtendDisposals());

                return nhiDayUploadDetails;
            })
            .get();
    }
}
