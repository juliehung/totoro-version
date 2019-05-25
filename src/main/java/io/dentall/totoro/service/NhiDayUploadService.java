package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiDayUpload;
import io.dentall.totoro.domain.NhiDayUploadDetails;
import io.dentall.totoro.repository.NhiDayUploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing NhiDayUpload.
 */
@Service
@Transactional
public class NhiDayUploadService {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadService.class);

    private final NhiDayUploadRepository nhiDayUploadRepository;

    private final RelationshipService relationshipService;

    public NhiDayUploadService(NhiDayUploadRepository nhiDayUploadRepository, RelationshipService relationshipService) {
        this.nhiDayUploadRepository = nhiDayUploadRepository;
        this.relationshipService = relationshipService;
    }

    /**
     * Save a nhiDayUpload.
     *
     * @param nhiDayUpload the entity to save
     * @return the persisted entity
     */
    public NhiDayUpload save(NhiDayUpload nhiDayUpload) {
        log.debug("Request to save NhiDayUpload : {}", nhiDayUpload);

        Set<NhiDayUploadDetails> nhiDayUploadDetails = nhiDayUpload.getNhiDayUploadDetails();
        nhiDayUpload = nhiDayUploadRepository.save(nhiDayUpload.nhiDayUploadDetails(null));
        relationshipService.addRelationshipWithNhiDayUploadDetails(nhiDayUpload, nhiDayUploadDetails);

        return nhiDayUpload;
    }

    /**
     * Get all the nhiDayUploads.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiDayUpload> findAll() {
        log.debug("Request to get all NhiDayUploads");
        return nhiDayUploadRepository.findAll();
    }


    /**
     * Get one nhiDayUpload by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiDayUpload> findOne(Long id) {
        log.debug("Request to get NhiDayUpload : {}", id);
        return nhiDayUploadRepository.findById(id);
    }

    /**
     * Delete the nhiDayUpload by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiDayUpload : {}", id);
        nhiDayUploadRepository.deleteById(id);
    }

    /**
     * Update the nhiDayUpload.
     *
     * @param updateNhiDayUpload the update entity
     * @return the entity
     */
    public NhiDayUpload update(NhiDayUpload updateNhiDayUpload) {
        log.debug("Request to update NhiDayUpload : {}", updateNhiDayUpload);

        return nhiDayUploadRepository
            .findById(updateNhiDayUpload.getId())
            .map(nhiDayUpload -> {
                if (updateNhiDayUpload.getDate() != null) {
                    nhiDayUpload.setDate(updateNhiDayUpload.getDate());
                }

                relationshipService.addRelationshipWithNhiDayUploadDetails(nhiDayUpload, updateNhiDayUpload.getNhiDayUploadDetails());

                return nhiDayUpload;
            })
            .get();
    }
}
