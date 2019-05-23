package io.dentall.totoro.service;

import io.dentall.totoro.domain.Procedure;
import io.dentall.totoro.repository.ProcedureRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.Optional;

/**
 * Service Implementation for managing Procedure.
 */
@Service
@Transactional
public class ProcedureService {

    private final Logger log = LoggerFactory.getLogger(ProcedureService.class);

    private final ProcedureRepository procedureRepository;

    public ProcedureService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    /**
     * Save a procedure.
     *
     * @param procedure the entity to save
     * @return the persisted entity
     */
    public Procedure save(Procedure procedure) {
        log.debug("Request to save Procedure : {}", procedure);
        return procedureRepository.save(procedure);
    }

    /**
     * Get all the procedures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Procedure> findAll(Pageable pageable) {
        log.debug("Request to get all Procedures");
        return procedureRepository.findAll(pageable);
    }


    /**
     * Get one procedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Procedure> findOne(Long id) {
        log.debug("Request to get Procedure : {}", id);
        return procedureRepository.findById(id);
    }

    /**
     * Delete the procedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Procedure : {}", id);

        if (procedureRepository.findAnyTreatmentProcedureByProcedureId(id) == null) {
            procedureRepository.deleteById(id);
        } else {
            throw new ProblemUtil("A procedure already has treatmentProcedure", Status.BAD_REQUEST);
        }
    }
}
