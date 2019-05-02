package io.dentall.totoro.service;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.NHIExtendDisposal;
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.repository.NHIExtendDisposalRepository;
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
 * Service Implementation for managing NHIExtendDisposal.
 */
@Service
@Transactional
public class NHIExtendDisposalService {

    private final Logger log = LoggerFactory.getLogger(NHIExtendDisposalService.class);

    private final NHIExtendDisposalRepository nhiExtendDisposalRepository;

    private final DisposalRepository disposalRepository;

    public NHIExtendDisposalService(NHIExtendDisposalRepository nhiExtendDisposalRepository, DisposalRepository disposalRepository) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.disposalRepository = disposalRepository;
    }

    /**
     * Save a nhiExtendDisposal.
     *
     * @param nhiExtendDisposal the entity to save
     * @return the persisted entity
     */
    public NHIExtendDisposal save(NHIExtendDisposal nhiExtendDisposal) {
        log.debug("Request to save NHIExtendDisposal : {}", nhiExtendDisposal);

        Disposal disposal = nhiExtendDisposal.getDisposal();
        if (disposal == null || disposal.getId() == null) {
            throw disposalNotFoundException();
        }

        disposal = disposalRepository.findById(disposal.getId()).orElseThrow(this::disposalNotFoundException);
        if (disposal.getNhiExtendDisposal() != null) {
            throw new ProblemUtil("A disposal already has nhiExtendDisposal", Status.BAD_REQUEST);
        }

        return nhiExtendDisposalRepository.save(nhiExtendDisposal.disposal(disposal));
    }

    /**
     * Get all the nhiExtendDisposals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NHIExtendDisposal> findAll(Pageable pageable) {
        log.debug("Request to get all NHIExtendDisposals");
        return nhiExtendDisposalRepository.findAll(pageable);
    }


    /**
     * Get one nhiExtendDisposal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NHIExtendDisposal> findOne(Long id) {
        log.debug("Request to get NHIExtendDisposal : {}", id);
        return nhiExtendDisposalRepository.findById(id);
    }

    /**
     * Delete the nhiExtendDisposal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NHIExtendDisposal : {}", id);
        nhiExtendDisposalRepository.deleteById(id);
    }

    /**
     * Update the nhiExtendDisposal.
     *
     * @param updateNHIExtendDisposal the update entity
     */
    public NHIExtendDisposal update(NHIExtendDisposal updateNHIExtendDisposal) {
        log.debug("Request to update NHIExtendDisposal : {}", updateNHIExtendDisposal);

        return nhiExtendDisposalRepository
            .findById(updateNHIExtendDisposal.getId())
            .map(nhiExtendDisposal -> {
                if (updateNHIExtendDisposal.getA11() != null) {
                    nhiExtendDisposal.setA11((updateNHIExtendDisposal.getA11()));
                }

                if (updateNHIExtendDisposal.getA12() != null) {
                    nhiExtendDisposal.setA12((updateNHIExtendDisposal.getA12()));
                }

                if (updateNHIExtendDisposal.getA13() != null) {
                    nhiExtendDisposal.setA13((updateNHIExtendDisposal.getA13()));
                }

                if (updateNHIExtendDisposal.getA14() != null) {
                    nhiExtendDisposal.setA14((updateNHIExtendDisposal.getA14()));
                }

                if (updateNHIExtendDisposal.getA15() != null) {
                    nhiExtendDisposal.setA15((updateNHIExtendDisposal.getA15()));
                }

                if (updateNHIExtendDisposal.getA16() != null) {
                    nhiExtendDisposal.setA16((updateNHIExtendDisposal.getA16()));
                }

                if (updateNHIExtendDisposal.getA17() != null) {
                    nhiExtendDisposal.setA17((updateNHIExtendDisposal.getA17()));
                }

                if (updateNHIExtendDisposal.getA18() != null) {
                    nhiExtendDisposal.setA18((updateNHIExtendDisposal.getA18()));
                }

                if (updateNHIExtendDisposal.getA19() != null) {
                    nhiExtendDisposal.setA19((updateNHIExtendDisposal.getA19()));
                }

                if (updateNHIExtendDisposal.getA22() != null) {
                    nhiExtendDisposal.setA22((updateNHIExtendDisposal.getA22()));
                }

                if (updateNHIExtendDisposal.getA23() != null) {
                    nhiExtendDisposal.setA23((updateNHIExtendDisposal.getA23()));
                }

                if (updateNHIExtendDisposal.getA25() != null) {
                    nhiExtendDisposal.setA25((updateNHIExtendDisposal.getA25()));
                }

                if (updateNHIExtendDisposal.getA26() != null) {
                    nhiExtendDisposal.setA26((updateNHIExtendDisposal.getA26()));
                }

                if (updateNHIExtendDisposal.getA27() != null) {
                    nhiExtendDisposal.setA27((updateNHIExtendDisposal.getA27()));
                }

                if (updateNHIExtendDisposal.getA31() != null) {
                    nhiExtendDisposal.setA31((updateNHIExtendDisposal.getA31()));
                }

                if (updateNHIExtendDisposal.getA32() != null) {
                    nhiExtendDisposal.setA32((updateNHIExtendDisposal.getA32()));
                }

                if (updateNHIExtendDisposal.getA41() != null) {
                    nhiExtendDisposal.setA41((updateNHIExtendDisposal.getA41()));
                }

                if (updateNHIExtendDisposal.getA42() != null) {
                    nhiExtendDisposal.setA42((updateNHIExtendDisposal.getA42()));
                }

                if (updateNHIExtendDisposal.getA43() != null) {
                    nhiExtendDisposal.setA43((updateNHIExtendDisposal.getA43()));
                }

                if (updateNHIExtendDisposal.getA44() != null) {
                    nhiExtendDisposal.setA44((updateNHIExtendDisposal.getA44()));
                }

                if (updateNHIExtendDisposal.getA54() != null) {
                    nhiExtendDisposal.setA54((updateNHIExtendDisposal.getA54()));
                }

                return nhiExtendDisposal;
            })
            .get();
    }

    private ProblemUtil disposalNotFoundException() {
        return new ProblemUtil("A disposal could not found", Status.BAD_REQUEST);
    }
}
