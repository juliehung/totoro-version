package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendPatient;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.repository.NhiMedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing NhiMedicalRecord.
 */
@Service
@Transactional
public class NhiMedicalRecordService {

    private final Logger log = LoggerFactory.getLogger(NhiMedicalRecordService.class);

    private final NhiMedicalRecordRepository nhiMedicalRecordRepository;

    public NhiMedicalRecordService(NhiMedicalRecordRepository nhiMedicalRecordRepository) {
        this.nhiMedicalRecordRepository = nhiMedicalRecordRepository;
    }

    /**
     * Save a nhiMedicalRecord.
     *
     * @param nhiMedicalRecord the entity to save
     * @return the persisted entity
     */
    public NhiMedicalRecord save(NhiMedicalRecord nhiMedicalRecord) {
        log.debug("Request to save NhiMedicalRecord : {}", nhiMedicalRecord);
        return nhiMedicalRecordRepository.save(nhiMedicalRecord);
    }

    /**
     * Get all the nhiMedicalRecords.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiMedicalRecord> findAll() {
        log.debug("Request to get all NhiMedicalRecords");
        return nhiMedicalRecordRepository.findAll();
    }


    /**
     * Get one nhiMedicalRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiMedicalRecord> findOne(Long id) {
        log.debug("Request to get NhiMedicalRecord : {}", id);
        return nhiMedicalRecordRepository.findById(id);
    }

    /**
     * Delete the nhiMedicalRecord by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiMedicalRecord : {}", id);

        nhiMedicalRecordRepository.findById(id).ifPresent(nhiMedicalRecord -> {
            if (nhiMedicalRecord.getNhiExtendPatient() != null) {
                NhiExtendPatient nhiExtendPatient = nhiMedicalRecord.getNhiExtendPatient();
                nhiExtendPatient.getNhiMedicalRecords().remove(nhiMedicalRecord);
            }
        });
        nhiMedicalRecordRepository.deleteById(id);
    }

    /**
     * Update the nhiMedicalRecord.
     *
     * @param updateNhiMedicalRecord the update entity
     */
    public NhiMedicalRecord update(NhiMedicalRecord updateNhiMedicalRecord) {
        log.debug("Request to update NhiMedicalRecord : {}", updateNhiMedicalRecord);

        return nhiMedicalRecordRepository
            .findById(updateNhiMedicalRecord.getId())
            .map(nhiMedicalRecord -> {
                if (updateNhiMedicalRecord.getDate() != null) {
                    nhiMedicalRecord.setDate(updateNhiMedicalRecord.getDate());
                }

                if (updateNhiMedicalRecord.getNhiCategory() != null) {
                    nhiMedicalRecord.setNhiCategory(updateNhiMedicalRecord.getNhiCategory());
                }

                if (updateNhiMedicalRecord.getNhiCode() != null) {
                    nhiMedicalRecord.setNhiCode(updateNhiMedicalRecord.getNhiCode());
                }

                if (updateNhiMedicalRecord.getPart() != null) {
                    nhiMedicalRecord.setPart(updateNhiMedicalRecord.getPart());
                }

                if (updateNhiMedicalRecord.getUsage() != null) {
                    nhiMedicalRecord.setUsage(updateNhiMedicalRecord.getUsage());
                }

                if (updateNhiMedicalRecord.getTotal() != null) {
                    nhiMedicalRecord.setTotal(updateNhiMedicalRecord.getTotal());
                }

                if (updateNhiMedicalRecord.getNote() != null) {
                    nhiMedicalRecord.setNote(updateNhiMedicalRecord.getNote());
                }

                return nhiMedicalRecord;
            })
            .get();
    }
}
