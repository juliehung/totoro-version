package io.dentall.totoro.service;

import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.domain.enumeration.TreatmentType;
import io.dentall.totoro.repository.TreatmentRepository;
import io.dentall.totoro.service.mapper.TreatmentMapper;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.service.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Treatment.
 */
@Service
@Transactional
public class TreatmentService {

    private final Logger log = LoggerFactory.getLogger(TreatmentService.class);

    private final TreatmentRepository treatmentRepository;

    private final RelationshipService relationshipService;

    public TreatmentService(TreatmentRepository treatmentRepository, RelationshipService relationshipService) {
        this.treatmentRepository = treatmentRepository;
        this.relationshipService = relationshipService;
    }

    /**
     * Save a treatment.
     *
     * @param treatment the entity to save
     * @return the persisted entity
     */
    public Treatment save(Treatment treatment) {
        log.debug("Request to save Treatment : {}", treatment);
        return treatmentRepository.save(treatment);
    }

    /**
     * Get all the treatments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Treatment> findAll(Pageable pageable) {
        log.debug("Request to get all Treatments");
        return treatmentRepository.findAll(pageable);
    }


    /**
     * Get one treatment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Treatment> findOne(Long id) {
        log.debug("Request to get Treatment : {}", id);
        return treatmentRepository.findById(id);
    }

    /**
     * Delete the treatment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Treatment : {}", id);

        treatmentRepository.findById(id).ifPresent(treatment -> {
            if (treatment.getType() == TreatmentType.GENERAL) {
                throw new ProblemUtil("A general treatment cannot delete", Status.BAD_REQUEST);
            }

            StreamUtil.asStream(treatment.getTreatmentPlans()).forEach(treatmentPlan -> treatmentPlan.setTreatment(null));
            relationshipService.deleteTreatmentPlans(treatment.getTreatmentPlans());

            treatmentRepository.deleteById(id);
        });
    }

    /**
     * Get one treatment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Treatment> findOneWithEagerRelationships(Long id) {
        log.debug("Request to get Treatment : {}", id);
        return treatmentRepository.findWithEagerRelationshipsById(id);
    }

    @Transactional(readOnly = true)
    public Set<Treatment> getTreatmentProjectionByPatientId(Long id) {
        return treatmentRepository.findByPatient_Id(id)
            .stream()
            .map(TreatmentMapper::treatmentTableToTreatment)
            .collect(Collectors.toSet());
    }
}
