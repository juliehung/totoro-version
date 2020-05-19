package io.dentall.totoro.service;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.SmsView;
import io.dentall.totoro.repository.SmsViewRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing SmsView.
 */
@Service
@Transactional
public class SmsViewService {

    private final Logger log = LoggerFactory.getLogger(SmsViewService.class);

    private final SmsViewRepository smsViewRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SmsViewService(SmsViewRepository smsViewRepository) {
        this.smsViewRepository = smsViewRepository;
    }

    /**
     * Save a smsView.
     *
     * @param id the appointmentId
     * @return the persisted entity
     */
    public SmsView save(Long id) {
        log.debug("Request to save appointmentId[{}] of SmsView", id);
        if (entityManager.find(Appointment.class, id) == null) {
            throw ProblemUtil.notFoundException("appointment");
        }

        SmsView smsView = smsViewRepository.findById(id).orElse(new SmsView().id(id));

        return smsViewRepository.save(smsView.lastTime(Instant.now()));
    }


    /**
     * Get one smsView by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SmsView> findOne(Long id) {
        log.debug("Request to get SmsView : {}", id);
        return smsViewRepository.findById(id);
    }
}
