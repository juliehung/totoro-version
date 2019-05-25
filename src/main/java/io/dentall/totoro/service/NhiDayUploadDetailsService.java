package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiDayUploadDetails;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.repository.NhiDayUploadDetailsRepository;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing NhiDayUploadDetails.
 */
@Service
@Transactional
public class NhiDayUploadDetailsService {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadDetailsService.class);

    private final NhiDayUploadDetailsRepository nhiDayUploadDetailsRepository;

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    public NhiDayUploadDetailsService(NhiDayUploadDetailsRepository nhiDayUploadDetailsRepository, NhiExtendDisposalRepository nhiExtendDisposalRepository) {
        this.nhiDayUploadDetailsRepository = nhiDayUploadDetailsRepository;
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
    }

    /**
     * Save a nhiDayUploadDetails.
     *
     * @param nhiDayUploadDetails the entity to save
     * @return the persisted entity
     */
    public NhiDayUploadDetails save(NhiDayUploadDetails nhiDayUploadDetails) {
        log.debug("Request to save NhiDayUploadDetails : {}", nhiDayUploadDetails);

        setNhiExtendDisposals(nhiDayUploadDetails, nhiDayUploadDetails.getNhiExtendDisposals());

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
        return nhiDayUploadDetailsRepository.findById(id);
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

                setNhiExtendDisposals(nhiDayUploadDetails, updateNhiDayUploadDetails.getNhiExtendDisposals());

                return nhiDayUploadDetails;
            })
            .get();
    }

    private void setNhiExtendDisposals(NhiDayUploadDetails nhiDayUploadDetails, Set<NhiExtendDisposal> nhiExtendDisposals) {
        if (nhiExtendDisposals != null) {
            nhiExtendDisposals = nhiExtendDisposals
                .stream()
                .map(nhiExtendDisposal -> nhiExtendDisposalRepository.findById(nhiExtendDisposal.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

            nhiDayUploadDetails.setNhiExtendDisposals(nhiExtendDisposals);
        }
    }
}
