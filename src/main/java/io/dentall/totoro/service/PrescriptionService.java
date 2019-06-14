package io.dentall.totoro.service;

import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.domain.enumeration.PrescriptionStatus;
import io.dentall.totoro.repository.PrescriptionRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.service.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Prescription.
 */
@Service
@Transactional
public class PrescriptionService {

    private final Logger log = LoggerFactory.getLogger(PrescriptionService.class);

    private final PrescriptionRepository prescriptionRepository;

    private final RelationshipService relationshipService;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, RelationshipService relationshipService) {
        this.prescriptionRepository = prescriptionRepository;
        this.relationshipService = relationshipService;
    }

    /**
     * Save a prescription.
     *
     * @param prescription the entity to save
     * @return the persisted entity
     */
    public Prescription save(Prescription prescription) {
        log.debug("Request to save Prescription : {}", prescription);

        Set<TreatmentDrug> treatmentDrugs = prescription.getTreatmentDrugs();
        prescription = prescriptionRepository.save(prescription.treatmentDrugs(null));
        relationshipService.addRelationshipWithTreatmentDrugs(prescription.treatmentDrugs(treatmentDrugs));

        return prescription;
    }

    /**
     * Get all the prescriptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Prescription> findAll(Pageable pageable) {
        log.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll(pageable);
    }



    /**
     *  get all the prescriptions where Disposal is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Prescription> findAllWhereDisposalIsNull() {
        log.debug("Request to get all prescriptions where Disposal is null");
        return StreamSupport
            .stream(prescriptionRepository.findAll().spliterator(), false)
            .filter(prescription -> prescription.getDisposal() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one prescription by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Prescription> findOne(Long id) {
        log.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findById(id);
    }

    /**
     * Delete the prescription by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Prescription : {}", id);

        prescriptionRepository.findById(id).ifPresent(prescription -> {
            if (prescription.getStatus() == PrescriptionStatus.PERMANENT) {
                throw new ProblemUtil("A permanent prescription cannot delete", Status.BAD_REQUEST);
            }

            StreamUtil.asStream(prescription.getTreatmentDrugs()).forEach(treatmentDrug -> treatmentDrug.setPrescription(null));
            relationshipService.deleteTreatmentDrugs(prescription.getTreatmentDrugs());

            prescriptionRepository.deleteById(id);
        });
    }

    /**
     * Update the prescription.
     *
     * @param updatePrescription the update entity
     * @return the entity
     */
    public Prescription update(Prescription updatePrescription) {
        log.debug("Request to update Prescription : {}", updatePrescription);

        return prescriptionRepository
            .findById(updatePrescription.getId())
            .map(prescription -> {
                if (updatePrescription.isClinicAdministration() != null) {
                    prescription.setClinicAdministration((updatePrescription.isClinicAdministration()));
                }

                if (updatePrescription.isAntiInflammatoryDrug() != null) {
                    prescription.setAntiInflammatoryDrug((updatePrescription.isAntiInflammatoryDrug()));
                }

                if (updatePrescription.isPain() != null) {
                    prescription.setPain((updatePrescription.isPain()));
                }

                if (updatePrescription.isTakenAll() != null) {
                    prescription.setTakenAll((updatePrescription.isTakenAll()));
                }

                if (updatePrescription.getStatus() != null) {
                    prescription.setStatus((updatePrescription.getStatus()));
                }

                if (updatePrescription.getMode() != null) {
                    prescription.setMode((updatePrescription.getMode()));
                }

                if (updatePrescription.getTreatmentDrugs() != null) {
                    Set<Long> updateIds = updatePrescription.getTreatmentDrugs().stream().map(TreatmentDrug::getId).collect(Collectors.toSet());
                    relationshipService.deleteTreatmentDrugs(
                        StreamUtil.asStream(prescription.getTreatmentDrugs())
                            .filter(treatmentDrug -> !updateIds.contains(treatmentDrug.getId()))
                            .map(treatmentDrug -> treatmentDrug.prescription(null))
                            .collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTreatmentDrugs(prescription.treatmentDrugs(updatePrescription.getTreatmentDrugs()));
                }

                return prescription;
            })
            .get();
    }
}
