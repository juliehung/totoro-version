package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiAccumulatedMedicalRecord;
import io.dentall.totoro.repository.NhiAccumulatedMedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NhiAccumulatedMedicalRecord.
 */
@Service
@Transactional
public class NhiAccumulatedMedicalRecordService {

    private final Logger log = LoggerFactory.getLogger(NhiAccumulatedMedicalRecordService.class);

    private final NhiAccumulatedMedicalRecordRepository nhiAccumulatedMedicalRecordRepository;

    public NhiAccumulatedMedicalRecordService(NhiAccumulatedMedicalRecordRepository nhiAccumulatedMedicalRecordRepository) {
        this.nhiAccumulatedMedicalRecordRepository = nhiAccumulatedMedicalRecordRepository;
    }

    /**
     * Save a nhiAccumulatedMedicalRecord.
     *
     * @param nhiAccumulatedMedicalRecord the entity to save
     * @return the persisted entity
     */
    public NhiAccumulatedMedicalRecord save(NhiAccumulatedMedicalRecord nhiAccumulatedMedicalRecord) {
        log.debug("Request to save NhiAccumulatedMedicalRecord : {}", nhiAccumulatedMedicalRecord);
        return nhiAccumulatedMedicalRecordRepository.save(nhiAccumulatedMedicalRecord);
    }

    /**
     * Get all the nhiAccumulatedMedicalRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NhiAccumulatedMedicalRecord> findAll(Pageable pageable) {
        log.debug("Request to get all NhiAccumulatedMedicalRecords");
        return nhiAccumulatedMedicalRecordRepository.findAll(pageable);
    }


    /**
     * Get one nhiAccumulatedMedicalRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiAccumulatedMedicalRecord> findOne(Long id) {
        log.debug("Request to get NhiAccumulatedMedicalRecord : {}", id);
        return nhiAccumulatedMedicalRecordRepository.findById(id);
    }

    /**
     * Delete the nhiAccumulatedMedicalRecord by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiAccumulatedMedicalRecord : {}", id);
        nhiAccumulatedMedicalRecordRepository.deleteById(id);
    }
}
