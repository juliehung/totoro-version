package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendPatient;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.NhiExtendPatientRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NhiExtendPatient.
 */
@Service
@Transactional
public class NhiExtendPatientService {

    private final Logger log = LoggerFactory.getLogger(NhiExtendPatientService.class);

    private final NhiExtendPatientRepository nhiExtendPatientRepository;

    private final PatientRepository patientRepository;

    private final NhiMedicalRecordService nhiMedicalRecordService;

    public NhiExtendPatientService(
        NhiExtendPatientRepository nhiExtendPatientRepository,
        PatientRepository patientRepository,
        NhiMedicalRecordService nhiMedicalRecordService
    ) {
        this.nhiExtendPatientRepository = nhiExtendPatientRepository;
        this.patientRepository = patientRepository;
        this.nhiMedicalRecordService = nhiMedicalRecordService;
    }

    /**
     * Save a nhiExtendPatient.
     *
     * @param nhiExtendPatient the entity to save
     * @return the persisted entity
     */
    public NhiExtendPatient save(NhiExtendPatient nhiExtendPatient) {
        log.debug("Request to save NhiExtendPatient : {}", nhiExtendPatient);

        Patient patient = nhiExtendPatient.getPatient();
        if (patient == null || patient.getId() == null) {
            throw ProblemUtil.notFoundException("patient");
        }

        patient = patientRepository.findById(patient.getId()).orElseThrow(() -> ProblemUtil.notFoundException("patient"));
        if (patient.getNhiExtendPatient() != null) {
            throw new ProblemUtil("A patient already has nhiExtendPatient", Status.BAD_REQUEST);
        }

        return nhiExtendPatientRepository.save(nhiExtendPatient.patient(patient));
    }

    /**
     * Get all the nhiExtendPatients.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiExtendPatient> findAll() {
        log.debug("Request to get all NhiExtendPatients");
        return nhiExtendPatientRepository.findAll();
    }


    /**
     * Get one nhiExtendPatient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiExtendPatient> findOne(Long id) {
        log.debug("Request to get NhiExtendPatient : {}", id);
        return nhiExtendPatientRepository.findById(id);
    }

    /**
     * Delete the nhiExtendPatient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiExtendPatient : {}", id);
        nhiExtendPatientRepository.deleteById(id);
    }

    /**
     * Update the nhiExtendPatient.
     *
     * @param updateNhiExtendPatient the update entity
     */
    public NhiExtendPatient update(NhiExtendPatient updateNhiExtendPatient) {
        log.debug("Request to update NhiExtendPatient : {}", updateNhiExtendPatient);

        return nhiExtendPatientRepository
            .findById(updateNhiExtendPatient.getId())
            .map(nhiExtendPatient -> {
                if (updateNhiExtendPatient.getCardNumber() != null) {
                    nhiExtendPatient.setCardNumber(updateNhiExtendPatient.getCardNumber());
                }

                if (updateNhiExtendPatient.getCardAnnotation() != null) {
                    nhiExtendPatient.setCardAnnotation(updateNhiExtendPatient.getCardAnnotation());
                }

                if (updateNhiExtendPatient.getCardValidDate() != null) {
                    nhiExtendPatient.setCardValidDate(updateNhiExtendPatient.getCardValidDate());
                }

                if (updateNhiExtendPatient.getCardIssueDate() != null) {
                    nhiExtendPatient.setCardIssueDate(updateNhiExtendPatient.getCardIssueDate());
                }

                if (updateNhiExtendPatient.getNhiIdentity() != null) {
                    nhiExtendPatient.setNhiIdentity(updateNhiExtendPatient.getNhiIdentity());
                }

                if (updateNhiExtendPatient.getAvailableTimes() != null) {
                    nhiExtendPatient.setAvailableTimes(updateNhiExtendPatient.getAvailableTimes());
                }

                if (updateNhiExtendPatient.getScaling() != null) {
                    nhiExtendPatient.setScaling(updateNhiExtendPatient.getScaling());
                }

                if (updateNhiExtendPatient.getFluoride() != null) {
                    nhiExtendPatient.setFluoride(updateNhiExtendPatient.getFluoride());
                }

                if (updateNhiExtendPatient.getPerio() != null) {
                    nhiExtendPatient.setPerio(updateNhiExtendPatient.getPerio());
                }

                if (updateNhiExtendPatient.getLifetime() != null) {
                    nhiExtendPatient.setLifetime(updateNhiExtendPatient.getLifetime());
                }

                if (updateNhiExtendPatient.getNhiMedicalRecords() != null) {
                    nhiMedicalRecordService.updateNhiMedicalRecordWithoutDuplicated(
                        nhiExtendPatient.getId(),
                        new ArrayList<>(
                            updateNhiExtendPatient.getNhiMedicalRecords()
                        )
                    );
                }

                return nhiExtendPatient;
            })
            .get();
    }
}
