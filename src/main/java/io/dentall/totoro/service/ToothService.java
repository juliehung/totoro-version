package io.dentall.totoro.service;

import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.ToothRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Tooth.
 */
@Service
@Transactional
public class ToothService {

    private final Logger log = LoggerFactory.getLogger(ToothService.class);

    private final ToothRepository toothRepository;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    private final DisposalRepository disposalRepository;

    private final PatientRepository patientRepository;

    public ToothService(
        ToothRepository toothRepository,
        TreatmentProcedureRepository treatmentProcedureRepository,
        DisposalRepository disposalRepository,
        PatientRepository patientRepository
    ) {
        this.toothRepository = toothRepository;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.disposalRepository = disposalRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Save a tooth.
     *
     * @param tooth the entity to save
     * @return the persisted entity
     */
    public Tooth save(Tooth tooth) {
        log.debug("Request to save Tooth : {}", tooth);
        return toothRepository.save(tooth);
    }

    /**
     * Get all the teeth.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tooth> findAll(Pageable pageable) {
        log.debug("Request to get all Teeth");
        return toothRepository.findAll(pageable);
    }


    /**
     * Get one tooth by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Tooth> findOne(Long id) {
        log.debug("Request to get Tooth : {}", id);
        return toothRepository.findById(id);
    }

    /**
     * Delete the tooth by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tooth : {}", id);

        toothRepository.findById(id).ifPresent(tooth -> {
            if (tooth.getDisposal() != null) {
                return;
            }

            if (tooth.getPatient() != null) {
                Patient patient = tooth.getPatient();
                patient.getTeeth().remove(tooth);
            }

            if (tooth.getTreatmentProcedure() != null) {
                TreatmentProcedure treatmentProcedure = tooth.getTreatmentProcedure();
                treatmentProcedure.getTeeth().remove(tooth);
            }

            toothRepository.deleteById(id);
        });
    }

    /**
     * Update the tooth.
     *
     * @param updateTooth the update entity
     */
    public Tooth update(Tooth updateTooth) {
        log.debug("Request to update Tooth : {}", updateTooth);

        return toothRepository
            .findById(updateTooth.getId())
            .map(tooth -> {
                if (updateTooth.getPosition() != null) {
                    tooth.setPosition((updateTooth.getPosition()));
                }

                if (updateTooth.getSurface() != null) {
                    tooth.setSurface((updateTooth.getSurface()));
                }

                if (updateTooth.getStatus() != null) {
                    tooth.setStatus((updateTooth.getStatus()));
                }

                if (updateTooth.getTreatmentProcedure() != null && updateTooth.getTreatmentProcedure().getId() != null) {
                    treatmentProcedureRepository.findById(updateTooth.getTreatmentProcedure().getId()).ifPresent(tooth::setTreatmentProcedure);
                }

                if (updateTooth.getDisposal() != null && updateTooth.getDisposal().getId() != null) {
                    disposalRepository.findById(updateTooth.getDisposal().getId()).ifPresent(tooth::setDisposal);
                }

                if (updateTooth.getPatient() != null && updateTooth.getPatient().getId() != null) {
                    patientRepository.findById(updateTooth.getPatient().getId()).ifPresent(tooth::setPatient);
                }

                if (updateTooth.getMetadata() != null) {
                    tooth.metadata(updateTooth.getMetadata());
                }

                return tooth;
            })
            .get();
    }
}
